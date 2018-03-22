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

	   private static String propertyUrl;
	    private static String propertyNomUtilisateur;
	private static String propertyMotDePasse;



	private static Connection conn = null;
	private static DBConnection instance;

	private DBConnection() {
	    Properties prop = new Properties();
	    InputStream input = null;

        try {
            input = new FileInputStream("./src/main/ressources/properties/connection.properties");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            prop.load(input);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        propertyUrl = prop.getProperty("database");
        propertyNomUtilisateur = prop.getProperty("dbuser");
propertyMotDePasse = prop.getProperty("dbpassword");
	}

	public static DBConnection getInstance() {
		if (null == instance) {
			instance = new DBConnection();
		}
		return instance;

	}

	public static DBConnection getInstance(String url,String name,String password) {
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
