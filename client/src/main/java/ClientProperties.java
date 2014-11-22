import java.util.ArrayList;
import java.util.Collections;

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
public class ClientProperties extends ApplicationProperties{

    /**
     * Ключ списока пользователей под которыми производилось подключение.
     */
    private static final String DATA_CONNECTION_USERS = "data.connection.users";
    /**
     * Имя файла настроек приложения.
     */
    private static String DEFAULT_PROPERTIES_FILE_NAME = "client.properties";

    /**
     * Возвращает список имён последних пользователей.
     *
     * @return Список имён пользователей.
     */
    public ArrayList<String> getUserNames() {
        String users = applicationProperties.getProperty(DATA_CONNECTION_USERS, "");
        ArrayList<String> strs = new ArrayList<>();
        if (users.length() != 0) {
            String[] data = users.split(";");
            Collections.addAll(strs, data);
        }
        return strs;
    }

    /**
     * Возвращает имя последнего авторизованного пользователя.
     *
     * @return Имя пользователя.
     */
    public String getUserName() {
        ArrayList<String> userNames = getUserNames();
        if (userNames.isEmpty()) {
            return null;
        }
        return userNames.get(0);
    }

    /**
     * Сохраняет имя пользователя.
     *
     * @param userName
     *            - Имя пользователя.
     */
    public void setUserName(String userName) {

        if ((userName == null) || (userName.length() == 0)) {
            return;
        }

        ArrayList<String> users = new ArrayList<>();
        users.add(userName);
        for (String user : getUserNames()) {
            if (!users.contains(user)) {
                users.add(user);
            }
            // Храним не более 5 пользователей
            if (users.size() == 5) {
                break;
            }
        }
        String regStr = "";
        for (String user : users) {
            regStr += user + ";";
        }

        applicationProperties.setProperty(DATA_CONNECTION_USERS, regStr);

        // Сохранение настроек
        storeApplicationProperties();
    }

    @Override
    protected String getPropertiesFileName() {
        return DEFAULT_PROPERTIES_FILE_NAME;
    }
}