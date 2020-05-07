package pt.isel.ls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.model.Router;
import pt.isel.ls.model.commands.common.CommandHandler;
import pt.isel.ls.model.commands.common.CommandRequest;
import pt.isel.ls.model.commands.common.CommandResult;
import pt.isel.ls.model.commands.common.Method;
import pt.isel.ls.model.commands.common.Parameters;
import pt.isel.ls.model.commands.sql.TransactionManager;
import pt.isel.ls.model.paths.Path;
import pt.isel.ls.view.View;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
        log.info("incoming request: method={}, uri={}, accept={}",
                req.getMethod(),
                req.getRequestURI(),
                req.getHeader("Accept"));

        Method method = Method.valueOf(req.getMethod());
        Path path = new Path(req.getRequestURI());
        CommandRequest request = new CommandRequest(path, null, trans, router);

        CommandHandler handler = router.findRoute(method, path);

        CommandResult result;
        String display;

        try {
            result = handler.execute(request);
            View view = View.getInstance(result);
            display = view.getDisplay("text/html");
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n");
            return;
        }



        Charset utf8 = StandardCharsets.UTF_8;
        resp.setContentType(String.format("text/html; charset=%s", utf8.name()));
        String respBody = display;

        byte[] respBodyBytes = respBody.getBytes(utf8);
        resp.setStatus(200);
        resp.setContentLength(respBodyBytes.length);
        OutputStream os = resp.getOutputStream();
        os.write(respBodyBytes);
        os.flush();
        log.info("outgoing response: method={}, uri={}, status={}, Content-Type={}",
                req.getMethod(),
                req.getRequestURI(),
                resp.getStatus(),
                resp.getHeader("Content-Type"));
    }


}
