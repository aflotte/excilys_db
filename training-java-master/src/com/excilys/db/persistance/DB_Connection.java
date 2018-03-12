package com.excilys.db.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Driver;

//singleton ??
//TODO : faire un singleton si nécessaire
public final class DB_Connection {
	
	 	private static final String FICHIER_PROPERTIES       = "/com/excilys/db/dao/dao.properties";
	    private static final String PROPERTY_URL             = "jdbc:mysql://localhost:3306/computer-database-db";
	    private static final String PROPERTY_DRIVER          = "driver";
	    private static final String PROPERTY_NOM_UTILISATEUR = "admincdb";
	    private static final String PROPERTY_MOT_DE_PASSE    = "qwerty1234";
	    
	    private String              url;
	    private String              username;
	    private String              password;
	
	    
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
	    
	    
	    
	    
	    public void Connection() {

	    try {
			conn = DriverManager.getConnection(PROPERTY_URL,PROPERTY_NOM_UTILISATEUR,PROPERTY_MOT_DE_PASSE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
	    
	    public void Disconnect() {
	    	if ( conn != null) {
	    		System.out.println("Fermeture de la connection");
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

		public void setConn(Connection conn) {
			this.conn = conn;
		}
	    

	    
}
