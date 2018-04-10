package com.excilys.db.persistance;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.excilys.db.exception.ConnectionFailedException;
import com.zaxxer.hikari.HikariDataSource;


//TODO:réutiliser connection.properties
public final class DBConnection {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DBConnection.class);
    private static DBConnection instance;
    private static HikariDataSource ds = new HikariDataSource();

    /**
     *
     */
    private DBConnection() {
        ResourceBundle bundle = ResourceBundle.getBundle("connect");
        ds.setJdbcUrl(bundle.getString("database"));
        ds.setUsername(bundle.getString("dbuser"));
        ds.setPassword(bundle.getString("dbpassword"));
        ds.setDriverClassName(bundle.getString("dbdriver"));
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

    public static Connection getConn() {
        DBConnection.getInstance();
        Connection conn;
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            throw new ConnectionFailedException();
        }
        return conn;
    }
}
