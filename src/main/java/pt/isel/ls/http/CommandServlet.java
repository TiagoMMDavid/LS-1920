package pt.isel.ls.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.model.Router;
import pt.isel.ls.model.commands.common.Headers;
import pt.isel.ls.model.commands.common.exceptions.CommandException;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.commands.common.exceptions.InvalidIdException;
import pt.isel.ls.model.commands.results.HttpResponseResult;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.view.View;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class CommandServlet extends HttpServlet {
    private Router router;
    private TransactionManager trans;
    private static final Logger log = LoggerFactory.getLogger(CommandServlet.class);

    public CommandServlet(Router router, TransactionManager trans) {
        this.router = router;
        this.trans = trans;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("incoming request from {}: method={}, uri={}, query-string={}, accept={}",
                req.getRemoteAddr(),
                req.getMethod(),
                req.getRequestURI(),
                req.getQueryString(),
                req.getHeader("Accept"));

        // We're in the "doGet" method, so we already know it's a GET method
        Method method = Method.GET;

        Path path = getPath(req, resp);
        Parameters params = getParameters(req, resp);
        // In this phase, we only require the accept header
        Headers headers = getAccept(req, resp);

        CommandResult result = null;

        // Status code from response remains as 0 when setStatus() wasn't yet called
        // Before this line, that method is only called whenever we catch an error while parsing
        // the method, the path, the parameters and the headers
        if (resp.getStatus() == 0) {
            CommandRequest request = new CommandRequest(path, params, trans, router);
            CommandHandler handler = router.findRoute(method, path);
            result = executeCommand(resp, request, handler);
        }

        String viewFormat = headers != null ? headers.getFirst("accept") : null;
        View view = View.findView(result != null ? result : new HttpResponseResult(resp.getStatus()));
        String display = view.getDisplay(viewFormat);

        Charset utf8 = StandardCharsets.UTF_8;
        // TODO: NOT THE VIEWFORMAT PASSED IN HEADERS, BUT THE ONE USED IN THE RESPONSE (TEXT PLAIN OR HTML)
        resp.setContentType(String.format(viewFormat + "; charset=%s", utf8.name()));
        byte[] respBodyBytes = display.getBytes(utf8);

        resp.setContentLength(respBodyBytes.length);
        OutputStream os = resp.getOutputStream();
        os.write(respBodyBytes);
        os.flush();
        log.info("outgoing response to {}: method={}, uri={}, status={}, Content-Type={}",
                req.getRemoteAddr(),
                req.getMethod(),
                req.getRequestURI(),
                resp.getStatus(),
                resp.getHeader("Content-Type"));
    }

    private CommandResult executeCommand(HttpServletResponse resp, CommandRequest request, CommandHandler handler) {
        CommandResult result = null;
        if (handler != null) {
            try {
                result = handler.execute(request);
                resp.setStatus(200); // OK
            } catch (InvalidIdException e) {
                resp.setStatus(404); // Not Found
                log.error(e.getMessage());
            } catch (SQLException | CommandException e) {
                resp.setStatus(500); // Internal Server Error
                log.error(e.getMessage());
            }
        } else {
            resp.setStatus(404);
        }
        return result;
    }

    private Headers getAccept(HttpServletRequest req, HttpServletResponse resp) {
        String accept = req.getHeader("Accept");
        if (accept != null) {
            try {
                accept = "accept:" + accept;
                return new Headers(accept);
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage());
                resp.setStatus(500);
            }
        }
        return null;
    }

    private Path getPath(HttpServletRequest req, HttpServletResponse resp) {
        try {
            return new Path(req.getRequestURI());
        } catch (IllegalArgumentException e) {
            log.error("Invalid path specified");
            resp.setStatus(500); // Internal Server Error
        }
        return null;
    }

    private Parameters getParameters(HttpServletRequest req, HttpServletResponse resp) {
        String parameters = req.getQueryString();
        if (parameters != null) {
            try {
                return new Parameters(URLDecoder.decode(parameters, StandardCharsets.UTF_8));
            } catch (IllegalArgumentException e) {
                log.error("Invalid parameters specified");
                resp.setStatus(500); // Internal Server Error
            }
        }
        return null;
    }
}
