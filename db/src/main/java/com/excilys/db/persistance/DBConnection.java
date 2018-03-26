package com.excilys.db.persistance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



public final class DBConnection {
	static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DBConnection.class);

	   private static String propertyUrl = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=UTF-8&characterSetResults=UTF-8&useSSL=FALSE&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";;
	    private static String propertyNomUtilisateur="admincdb";
	private static String propertyMotDePasse = "qwerty1234";
	public static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";



	private static Connection conn = null;
	private static DBConnection instance;

	private DBConnection() {

	}

	public static DBConnection getInstance() {
		if (null == instance) {
			instance = new DBConnection();
		}
		return instance;

	}

	public static DBConnection getInstance(String url,String name,String password) {
	    try {
            Class.forName(MYSQL_DRIVER);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		if (null == instance) {
			instance = new DBConnection();
		}
		propertyUrl =url;
		propertyNomUtilisateur = name;
		propertyMotDePasse = password;
		return instance;

	}
	
	
	public int executeQuery(String query) throws ClassNotFoundException, SQLException {
		
			    return conn.createStatement().executeUpdate(query);
		
			  }
	
	public void connect() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(propertyUrl,propertyNomUtilisateur,propertyMotDePasse);
			} catch (SQLException e) {
				logger.warn(e.getMessage());
			}
		}
	}

	public void disconnect() {
		if ( conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.warn(e.getMessage());
			}
		}
		conn = null;
	}

	public static Connection getConn() {
		return conn;
	}




}
