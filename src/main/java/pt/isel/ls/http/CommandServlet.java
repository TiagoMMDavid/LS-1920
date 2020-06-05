package pt.isel.ls.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.model.Router;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.Headers;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.commands.common.PostResult;
import pt.isel.ls.model.commands.common.exceptions.CommandException;
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
import java.util.Map;

import static pt.isel.ls.model.commands.sql.TransactionManager.CONNECTION_REFUSED_ERROR;
import static pt.isel.ls.model.commands.sql.TransactionManager.DUPLICATE_COLUMN_ERROR;

public class CommandServlet extends HttpServlet {
    private final Router router;
    private final TransactionManager trans;
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
        Parameters params = getParametersGet(req, resp);
        // In this phase, we only require the accept header
        Headers headers = getAccept(req, resp);

        CommandResult result = null;

        // Status code from response remains as 200 when setStatus() wasn't yet called
        // Before this line, that method is only called whenever we catch an error while parsing
        // the method, the path, the parameters and the headers
        if (resp.getStatus() == 200) {
            CommandRequest request = new CommandRequest(path, params, trans, router);
            CommandHandler handler = router.findRoute(method, path);
            result = executeCommand(resp, request, handler);
        }

        writeView(resp, headers, result);
        log.info("outgoing response to {}: method={}, uri={}, status={}, Content-Type={}",
                req.getRemoteAddr(),
                req.getMethod(),
                req.getRequestURI(),
                resp.getStatus(),
                resp.getHeader("Content-Type"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("incoming request from {}: method={}, uri={}, query-string={}, accept={}",
                req.getRemoteAddr(),
                req.getMethod(),
                req.getRequestURI(),
                req.getQueryString(),
                req.getHeader("Accept"));

        // We're in the "doPost" method, so we already know it's a POST method
        Method method = Method.POST;

        Path path = getPath(req, resp);
        Parameters params = getParametersPost(req, resp);

        PostResult result = null;

        // Status code from response remains as 200 when setStatus() wasn't yet called
        // Before this line, that method is only called whenever we catch an error while parsing
        // the method, the path, the parameters and the headers
        CommandRequest request = new CommandRequest(path, params, trans, router);
        if (resp.getStatus() == 200) {
            CommandHandler handler = router.findRoute(method, path);

            // We assume every POST command returns a result that implements PostCommandResult
            result = (PostResult) executeCommand(resp, request, handler);
        }

        if (result != null) {
            resp.setStatus(301); // Permanently Moved / Redirect
            resp.setHeader("location", result.getCreatedId());
        } else {
            Headers headers = getAccept(req, resp);
            CommandResult resultView = null;

            // We only want to present the same page when the error is 400 (Bad Request)
            if (resp.getStatus() == 400) {
                CommandHandler handler = router.findRoute(Method.GET, path);
                resultView = executeCommand(resp, request, handler);
            }
            writeView(resp, headers, resultView);
        }

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
            } catch (InvalidIdException e) {
                resp.setStatus(404); // Not Found
                log.error(e.getMessage());
            } catch (SQLException e) {
                log.error("SQL ERROR STATE: {}", e.getSQLState());
                switch (e.getSQLState()) {
                    case DUPLICATE_COLUMN_ERROR:
                        resp.setStatus(400); // Bad Request
                        break;
                    case CONNECTION_REFUSED_ERROR:
                        resp.setStatus(500); // Internal Server Error
                        break;
                    default:
                        resp.setStatus(500); // Internal Server Error
                }
                log.error(e.getMessage());
            } catch (CommandException e) {
                resp.setStatus(500); // Internal Server Error
                log.error(e.getMessage());
            }
        } else {
            resp.setStatus(404);
        }
        return result;
    }


    private void writeView(HttpServletResponse resp, Headers headers, CommandResult result) throws IOException {
        String viewFormat = headers != null ? headers.getFirst("accept") : null;
        View view = View.findView(result != null ? result : new HttpResponseResult(resp.getStatus()), viewFormat);
        if (!view.foundRoute()) {
            resp.setStatus(406); // Not Acceptable
            view = View.findView(new HttpResponseResult(406), viewFormat);
        }

        String display = view.getDisplay();

        Charset utf8 = StandardCharsets.UTF_8;
        resp.setContentType(String.format("%s; charset=%s", view.getViewFormat(), utf8.name()));
        byte[] respBodyBytes = display.getBytes(utf8);

        resp.setContentLength(respBodyBytes.length);
        OutputStream os = resp.getOutputStream();
        os.write(respBodyBytes);
        os.flush();
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

    private Parameters getParametersGet(HttpServletRequest req, HttpServletResponse resp) {
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

    private Parameters getParametersPost(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String[]> parameterMap = req.getParameterMap();
        if (parameterMap != null) {
            try {
                return new Parameters(parsePostParameters(parameterMap));
            } catch (IllegalArgumentException e) {
                log.error("Invalid parameters specified");
                resp.setStatus(500); // Internal Server Error
            }
        }
        return null;
    }

    private String parsePostParameters(Map<String, String[]> parameterMap) {
        StringBuilder builder = new StringBuilder();
        int entrySize = parameterMap.entrySet().size();
        int entryIdx = 0;
        for (Map.Entry<String, String[]> mapEntry : parameterMap.entrySet()) {
            int valueIdx = 0;
            int valueLength = mapEntry.getValue().length;
            for (String value: mapEntry.getValue()) {
                builder.append(mapEntry.getKey());
                builder.append('=');
                builder.append(value);
                if (++valueIdx < valueLength) {
                    builder.append('&');
                }
            }
            if (++entryIdx < entrySize) {
                builder.append('&');
            }
        }
        return builder.toString();
    }
}
