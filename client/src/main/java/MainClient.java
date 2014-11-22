import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
public class MainClient {

    public static MainClient instance;
    private CommandManager manager;
    private ClientProperties properties;


    public MainClient() {
        instance = this;
        manager = new CommandManager();
        properties = new ClientProperties();
    }

    public static MainClient getInstance() {
        if (instance == null)
            instance = new MainClient();

        return instance;
    }

    public ClientProperties getProperties() {
        return properties;
    }

    private void showMenu() {
        System.out.println("1: Connect to server");
        System.out.println("2: Disconnect from server");
        System.out.println("3: Exit program");
    }

    private boolean handle(char key) throws IOException {
        if (key == '1') {
            manager.connect();

        } else if (key == '2'){
            manager.disconnect();

        } else if (key == '3'){
            manager.exit();
            return true;

        } else {
            System.out.println("Unknown command. Retry.");
        }

        return false;
    }

    private boolean readInputAndHandleCommands(){
        boolean exit = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char key;

        showMenu();

        try {
            key = (char) br.read();
            exit = handle(key);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return exit;
    }

    public static void main(String[] args){
        MainClient mainClient = new MainClient();
        boolean exit = false;

        while(!exit){
            exit = mainClient.readInputAndHandleCommands();
        }
    }
}
