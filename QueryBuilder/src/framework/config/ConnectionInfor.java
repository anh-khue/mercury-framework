package framework.config;

import java.io.Serializable;

public class ConnectionInfor implements Serializable {
    private static String dbms;
    private static String driverClass;
    private static String url;
    private static String username;
    private static String password;

    ConnectionInfor(String dbms, String driverClass, String url, String username, String password) {
        ConnectionInfor.dbms = dbms;
        ConnectionInfor.driverClass = driverClass;
        ConnectionInfor.url = url;
        ConnectionInfor.username = username;
        ConnectionInfor.password = password;
    }

    public String getDbms() {
        return dbms;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
