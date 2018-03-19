package com.excilys.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.db.model.Companies;

public class CompaniesMapper {
	public static Companies computerResultToCompanies(ResultSet rs) {
		Companies result = new Companies(); 
		try {
			result.setId(rs.getInt("company.id"));
			result.setName(rs.getString("company.name"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
