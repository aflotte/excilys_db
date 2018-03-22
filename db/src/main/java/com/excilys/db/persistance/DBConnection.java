package com.excilys.db.persistance;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public final class DBConnection {
	static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DBConnection.class);

	private static String PROPERTY_URL;
	private static String PROPERTY_NOM_UTILISATEUR;
	private static String PROPERTY_MOT_DE_PASSE;



	private static Connection conn = null;
	private static DBConnection instance;

	private DBConnection() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("connection.properties"));
		} catch (FileNotFoundException e1) {
			logger.error("Le fichier de propriété de la connection semble introuvable");
		}

		try {
			PROPERTY_URL =reader.readLine();
			PROPERTY_NOM_UTILISATEUR = reader.readLine();
			PROPERTY_MOT_DE_PASSE = reader.readLine();
		} catch (IOException e) {
			logger.error("Le fichier de propriété de la connection semble corrompu");
		}
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
		PROPERTY_URL =url;
		PROPERTY_NOM_UTILISATEUR = name;
		PROPERTY_MOT_DE_PASSE = password;
		return instance;

	}
	
	
	public int executeQuery(String query) throws ClassNotFoundException, SQLException {
		
			    return conn.createStatement().executeUpdate(query);
		
			  }
	
	public void connect() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(PROPERTY_URL,PROPERTY_NOM_UTILISATEUR,PROPERTY_MOT_DE_PASSE);
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