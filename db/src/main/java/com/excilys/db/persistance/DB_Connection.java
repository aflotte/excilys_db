package com.excilys.db.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class DB_Connection {

	private static final String PROPERTY_URL             = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=UTF-8&characterSetResults=UTF-8";
	private static final String PROPERTY_NOM_UTILISATEUR = "admincdb";
	public static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String PROPERTY_MOT_DE_PASSE    = "qwerty1234";



	private static Connection conn = null;
	private static DB_Connection instance;

	private DB_Connection() {


	}

	public static DB_Connection getInstance() {
		if (null == instance) {
			instance = new DB_Connection();
		}
		return instance;

	}

	public void connect() {
		if (conn == null) {

			try {
				conn = DriverManager.getConnection(PROPERTY_URL,PROPERTY_NOM_UTILISATEUR,PROPERTY_MOT_DE_PASSE);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void disconnect() {
		if ( conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		conn = null;
	}

	public static Connection getConn() {
		return conn;
	}




}
