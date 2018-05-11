package com.github.microkibaco.bearlive.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlManager {

    private static final String DB_PRO = "jdbc:mysql://";
    private static final String CHARSET = "?useUnicode=true&charactsetEncoding=utf-8";
    private static final String USER = "af4d43dc";
    private static final String PASSWORD = "b96c8390";
    private static final String PORT = "30443";
    private static final String HOST = "192.168.1.234";
    private static final String DB_NAME = "def567db";
    private static final String URL = DB_PRO + HOST + ":" + PORT + "/" + DB_NAME + CHARSET;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
