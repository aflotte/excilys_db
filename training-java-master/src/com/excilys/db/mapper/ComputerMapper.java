package com.excilys.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.db.moddel.Computer;

public class ComputerMapper {
	public static Computer resultToComputer(ResultSet resultSet) throws SQLException {
		Computer toReturn = new Computer();
		toReturn.setName(resultSet.getString(1));
		toReturn.setIntroduced(resultSet.getDate(2));
		toReturn.setIntroduced(resultSet.getDate(3));
		toReturn.setCompanyId(resultSet.getInt(4));
		toReturn.setId(resultSet.getInt(5));
		return toReturn;
	}
}
