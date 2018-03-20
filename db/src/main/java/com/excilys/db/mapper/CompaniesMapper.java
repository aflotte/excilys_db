package com.excilys.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.db.model.Companies;

public class CompaniesMapper {
	static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompaniesMapper.class);
	public static Companies computerResultToCompanies(ResultSet rs) {
		Companies result = new Companies(); 
		try {
			result.setId(rs.getInt("company.id"));
			result.setName(rs.getString("company.name"));
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}
		return result;
	}
}
