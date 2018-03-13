package com.excilys.db.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.db.mapper.Companies;
import com.excilys.db.persistance.DB_Connection;

public class CompaniesDAO{
	
	
	public List<Companies> listCompanies() {
		DB_Connection.getInstance().Connection();
		Connection conn = DB_Connection.getConn();
		ResultSet resultSet = null;
		List<Companies> listResult = new ArrayList<Companies>();
		String querry = "SELECT name FROM company";
		try {
			PreparedStatement prep1 = conn.prepareStatement(querry);
			resultSet = prep1.executeQuery();
			while (resultSet.next()) {
				Companies toAdd = new Companies();
				toAdd.setName(resultSet.getString(1));
				listResult.add(toAdd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listResult;
	}

}
