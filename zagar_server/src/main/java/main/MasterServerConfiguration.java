package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Ольга on 29.11.2016.
 */
public class MasterServerConfiguration {
    public static String ACCOUNT_SERVER_PORT;
    public static String CLENT_CONNECTION_PORT;
    public static String REPLICATOR;
    public static String MATCH_MAKER;
    public static String[] SERVICES;

    static {
        FileInputStream fin;
        Properties property = new Properties();
        try {
            fin = new FileInputStream("src/main/resources/config.properties");
            property.load(fin);
            ACCOUNT_SERVER_PORT =property.getProperty("accountServerPort");
            CLENT_CONNECTION_PORT = property.getProperty("clientConnectionPort");
            REPLICATOR = property.getProperty("replicator");
            MATCH_MAKER =  property.getProperty("matchMaker");
            SERVICES = property.get("services").toString().split(",");
            if (ACCOUNT_SERVER_PORT==null || CLENT_CONNECTION_PORT==null || REPLICATOR==null || MATCH_MAKER==null || SERVICES == null)
                throw new InvalidConfigException();
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        } catch (InvalidConfigException e){
            e.printStackTrace();
        }

    }
}
