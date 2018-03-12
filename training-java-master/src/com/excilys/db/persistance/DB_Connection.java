package com.excilys.db.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
	
	    
	    Connection conn = null;
	    private static DB_Connection instance;
	    
	    private DB_Connection() {
	    	try {
				Class.forName("com.mysql.jcdb.Driver").newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    public DB_Connection getInstance() {
			if (null == instance) {
				instance = new DB_Connection();
			}
	    	return instance;
	    	
	    }
	    
	    
	    
	    
	    public void Connection() {

	    try {
			Connection conn = DriverManager.getConnection(PROPERTY_URL,PROPERTY_NOM_UTILISATEUR,PROPERTY_MOT_DE_PASSE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
	    
}
