package com.excilys.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.model.Computer;

public class ComputerMapper {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerMapper.class);

    /**
     *
     * @param resultSet le ResultSet a interpreter
     * @return l'ordinateur
     * @throws SQLException en cas d'erreur de connection
     * @throws CompaniesInexistantException si la compagnie n'existe pas
     */
    public static Computer resultToComputer(ResultSet resultSet) throws SQLException {
        Computer toReturn = new Computer();
        toReturn.setName(resultSet.getString(1));
        if (resultSet.getDate(2) != null) {
            toReturn.setIntroduced(resultSet.getDate(2).toLocalDate());
        } else {
            toReturn.setIntroduced(null);
        }
        if (resultSet.getDate(3) != null) {
            toReturn.setIntroduced(resultSet.getDate(3).toLocalDate());
        } else {
            toReturn.setIntroduced(null);
        }
        toReturn.setCompany(CompaniesMapper.computerResultToCompanies(resultSet));
        toReturn.setId(resultSet.getInt(5));
        return toReturn;
    }

    public static ComputerDTO computerToSTO(Computer computer) {
        ComputerDTO toReturn = new ComputerDTO();
        toReturn.setId(computer.getId());
        toReturn.setCompany(computer.getCompany().getName());
        toReturn.setName(computer.getName());
        toReturn.setDiscontinued(computer.getDiscontinued());
        toReturn.setIntroduced(computer.getIntroduced());
        return toReturn;
    }
}



