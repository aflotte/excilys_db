package com.excilys.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.db.exception.CompaniesIdIncorrect;
import com.excilys.db.exception.CompaniesInexistant;
import com.excilys.db.exception.IncoherentDates;
import com.excilys.db.model.Computer;

public class ComputerMapper {
	static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerMapper.class);
	public static Computer resultToComputer(ResultSet resultSet) throws SQLException, CompaniesInexistant {
		Computer toReturn = new Computer();
		toReturn.setName(resultSet.getString(1));
		if ( resultSet.getDate(2) != null) {
			toReturn.setIntroduced(resultSet.getDate(2).toLocalDate());
		}else {
			toReturn.setIntroduced(null);
		}
		if ( resultSet.getDate(3) != null) {
			toReturn.setIntroduced(resultSet.getDate(3).toLocalDate());
		}else {
			toReturn.setIntroduced(null);
		}
		try {
			toReturn.setCompanyId(CompaniesMapper.computerResultToCompanies(resultSet));
		} catch (IncoherentDates | CompaniesIdIncorrect e) {
			logger.warn(e.getMessage());
		}
		toReturn.setId(resultSet.getInt(5));
		
		return toReturn;
	}
}
