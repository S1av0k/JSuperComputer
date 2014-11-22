import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

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
public class CommandManager {

    private static final Logger LOGGER = Logger.getLogger(CommandManager.class);
    private static final String PROPERTY_SERVER_IP = "server.ip";
    private static final String PROPERTY_SERVER_PORT = "server.port";

    private Socket socket = null;


    // Установка соединения с сервером
    public void connect() {
        WaitingFormatter formatter = new WaitingFormatter();
        formatter.startWaitingOutput();

        ClientProperties properties = MainClient.getInstance().getProperties();
        String serverIP = properties.getProperty(PROPERTY_SERVER_IP, "127.0.0.1");
        int serverPort = Integer.parseInt(properties.getProperty(PROPERTY_SERVER_PORT, 2014));

        try {
            socket = new Socket(serverIP, serverPort);
            socket.setKeepAlive(true);
        } catch (IOException e) {
            LOGGER.warn("Перехвачено исключение: " + e.getMessage());
        } finally {
            formatter.stopWaitingOutput();
        }
    }

    // Разрыв соединения с сервером
    public void disconnect() throws IOException {
        if (socket != null) {
            if (socket.isConnected()) {
                socket.close();
            } else {
                socket = null;
            }
        }
    }

    // Конец работы клиента
    public void exit() {
        try {
            disconnect();
        } catch (Exception e) {
            LOGGER.warn("Перехвачено исключение: " + e.getMessage());
        }
    }
}
