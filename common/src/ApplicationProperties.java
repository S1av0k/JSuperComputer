import java.io.*;
import java.util.*;

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
public abstract class ApplicationProperties {

    /**
     * Имя файла настроек приложения.
     */
    protected String applicationPropertiesFileName = null;
    protected Properties applicationProperties = null;

    /**
     * Загружает свойства приложения из файла.
     */
    public void loadApplicationProperties() {

        if (applicationProperties != null) {
            throw new IllegalStateException("Этот метод достаточно вызывать один раз. Для избежания коллизий.");
        }
        // По умолчанию создаём свойства
        applicationProperties = new java.util.Properties();

        File file = new File(getFileName(null));

        // Если файл существует, то выгружаем настройки из него
        if (file.exists()) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // Попытка выгрузки настроек
            if (fileInputStream != null) {
                try {
                    applicationProperties.load(fileInputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Сохраняет свойства приложения в файл.
     */
    public synchronized void storeApplicationProperties() {

        File file = new File(getFileName(null));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fileOutputStream != null) {
            try {
                applicationProperties.store(fileOutputStream, "Настройки приложения");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Сортирует и сохраняет свойства приложения в файл.
     */
    public synchronized void sortAndStoreApplicationProperties() {
        ArrayList<String> keys = new ArrayList<String>();
        keys.addAll(getPropertiesKeys(null));
        Collections.sort(keys);
        // Новый файл, в который произойдёт сохранение настроек
        File tmpFile = new File(getFileName(null) + ".tmp");
        if (!tmpFile.exists()) {
            try {
                tmpFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(tmpFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        if (fileOutputStream != null) {
            for (String key : keys) {
                String str = key + "=" + getProperty(key, "") + "\n";
                String newstr = convertToJavaUnicode(str);
                try {
                    fileOutputStream.write(newstr.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // Заменяем старый файл на новый
        File file = new File(getFileName(null));
        file.delete();
        tmpFile.renameTo(file);
        tmpFile = new File(getFileName(null) + ".tmp");
        tmpFile.delete();
    }

    /**
     * Метод преобразует строку формата Unicode, в представление Unicode в кодировке ASCII.
     *
     * @param unicode
     *            - Строка в формате Unicode.
     * @return Строка представляющаю Unicode в кодировке ASCII.
     */
    private static String convertToJavaUnicode(String unicode) {
        String output = "";
        char[] ca = unicode.toCharArray();
        for (char a : ca) {
            if ((int) a > 255) {
                String str = Integer.toHexString((int) a);
                for (int j = str.length(); j < 4; j++) {
                    str = "0" + str;
                }
                output += "\\u" + str;
            } else if (a == '\\') {
                output += "\\\\";
            } else {
                output += a;
            }
        }
        return output;
    }

    /**
     * Возвращает значение по заданному ключу, если не находит данного ключа, то возвращает значение по умолчанию.
     *
     * @param key
     *            - Ключ.
     * @param defaultValue
     *            - Значение по умолчанию.
     * @return Значение по ключу.
     */
    public synchronized String getProperty(String key, String defaultValue) {
        String value = applicationProperties.getProperty(key, defaultValue);

        return value;
    }

    /**
     * Возвращает значение по заданному ключу, если не находит данного ключа, то возвращает значение по умолчанию.
     *
     * @param key
     *            - Ключ.
     * @param defaultValue
     *            - Значение по умолчанию.
     * @return Значение по ключу.
     */
    public synchronized String getProperty(String key, int defaultValue) {
        String value = applicationProperties.getProperty(key, Integer.toString(defaultValue));

        return value;
    }

    private synchronized String getFileName(String fileName) {
        if (applicationPropertiesFileName == null) {
            if (fileName == null) {
                applicationPropertiesFileName = getPropertiesFileName();
            }
            else
                applicationPropertiesFileName = fileName;
        }

        return applicationPropertiesFileName;
    }

    protected abstract String getPropertiesFileName();

    /**
     * Устанавливает значение по заданному ключу.
     *
     * @param key
     *            - Ключ.
     * @param value
     *            - Значение.
     */
    public synchronized void setProperty(String key, String value) {
        applicationProperties.setProperty(key, value);
    }

    /**
     * Возвращает множество ключей, начинающихся с заданного префикса. Если в качестве префикса передать параметр
     * <code>null</code>, то метод вернёт множество всех найденных ключей.
     *
     * @param prefix
     *            - Префикс.
     * @return Множество найденных ключей.
     */
    public synchronized Set<String> getPropertiesKeys(String prefix) {
        HashSet<String> keys = new HashSet<>();
        for (Object object : applicationProperties.keySet()) {
            String key = object.toString();
            if (prefix == null) {
                keys.add(key);
            } else if (key.startsWith(prefix)) {
                keys.add(key);
            }
        }
        return keys;
    }
}