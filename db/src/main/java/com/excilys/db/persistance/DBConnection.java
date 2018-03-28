package com.excilys.db.persistance;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import com.excilys.db.exception.ConnectionFailedException;
import com.excilys.db.exception.DisconnectionFailedExeption;
import com.zaxxer.hikari.HikariDataSource;


//TODO:réutiliser connection.properties
public final class DBConnection {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DBConnection.class);

    private static String propertyUrl = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=UTF-8&characterSetResults=UTF-8&useSSL=FALSE&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=CET";;
    private static String propertyNomUtilisateur = "admincdb";
    private static String propertyMotDePasse = "qwerty1234";
    public static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";



    private static Connection conn = null;
    private static DBConnection instance;
    private static HikariDataSource ds = new HikariDataSource();

    /**
     *
     */
    private DBConnection() {
        ds.setJdbcUrl(propertyUrl);
        ds.setUsername(propertyNomUtilisateur);
        ds.setPassword(propertyMotDePasse);
        ds.setDriverClassName(MYSQL_DRIVER);
    }

    /**
     *
     * @return l'instance
     */
    public static DBConnection getInstance() {
        if (null == instance) {
            instance = new DBConnection();
        }
        return instance;

    }

    /**
     *
     * @param url de la base
     * @param name d'utilisateur
     * @param password associé
     * @return l'instance
     */
    public static DBConnection getInstance(String url, String name, String password) {
        try {
            Class.forName(MYSQL_DRIVER);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (null == instance) {
            instance = new DBConnection();
        }
        propertyUrl = url;
        propertyNomUtilisateur = name;
        propertyMotDePasse = password;
        return instance;
    }

    /**
     *
     */
    public void connect() {
        if (conn == null) {
            try {
                conn = ds.getConnection();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
                throw new ConnectionFailedException();
            }
        }
    }

    /**
     *
     */
    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
                throw new DisconnectionFailedExeption();
            }
        }
        conn = null;
    }

    public static Connection getConn() {
        return conn;
    }




}
