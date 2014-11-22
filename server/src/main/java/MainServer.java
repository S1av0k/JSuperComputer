import javax.servlet.http.HttpServlet;

/**
 * <p>
 * Title: Проект Java Super Computer
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * </p>
 * <p>
 * Copyright: Copyright MEPHI (c) 2014
 * </p>
 *
 * @author vyacheslav.beketnov &lt;beketnov.vm@gmail.ru&gt;
 * @version 1.0
 */
public class MainServer extends HttpServlet {
    private ServerNIO serverNIO;

    public MainServer() {
        serverNIO = new ServerNIO();
    }

    public void start() {

    }

    public static void main(String[] args) {
        MainServer mainServer = new MainServer();
        mainServer.start();
    }
}
