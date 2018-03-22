package com.excilys.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.model.Company;
import com.excilys.db.model.Computer;
import com.excilys.db.service.CompaniesService;
import com.excilys.db.test.CompaniesServiceTest;

public class ComputerMapper {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerMapper.class);

    /**
     *
     * @param resultSet le ResultSet a interpreter
     * @return l'ordinateur
     * @throws SQLException en cas d'erreur de connection
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

    public static ComputerDTO computerToDTO(Computer computer) {
        ComputerDTO toReturn = new ComputerDTO();
        toReturn.setId(computer.getId());
        toReturn.setCompany(computer.getCompany().getName());
        toReturn.setName(computer.getName());
        if (computer.getDiscontinued()==null) {
            toReturn.setDiscontinued("");
        }else {
            toReturn.setDiscontinued(computer.getDiscontinued().toString());
        }
        if (computer.getDiscontinued()==null) {
            toReturn.setIntroduced("");
        }else {
            toReturn.setIntroduced(computer.getIntroduced().toString());
        }

        return toReturn;
    }

    public static List<ComputerDTO> computerListToComputerDTO(List<Computer> computer) {
        List<ComputerDTO> toReturn = new ArrayList<ComputerDTO>();
        for (int i = 0; i < computer.size(); i++  ) {
            toReturn.add(computerToDTO(computer.get(i)));
        }
        return toReturn;
    }

    public static Computer computerDTOToComputer(ComputerDTO computer) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale(Locale.FRANCE);
        Computer toReturn = new Computer();
        toReturn.setId(computer.getId());
        Company company = new Company();
        company.setName(computer.getCompany());
        int id = CompaniesService.INSTANCE.getCompagnyId(computer.getCompany());
        company.setId(id);

        toReturn.setCompany(company);
        toReturn.setName(computer.getName());
        toReturn.setDiscontinued(LocalDate.parse(computer.getDiscontinued(),formatter));
        toReturn.setIntroduced(LocalDate.parse(computer.getIntroduced(),formatter));


        return toReturn;
    }

    public static List<Computer> computerDTOListToComputer(List<ComputerDTO> computer) {
        List<Computer> toReturn = new ArrayList<Computer>();
        for (int i = 0; i < computer.size(); i++  ) {
            toReturn.add(computerDTOToComputer(computer.get(i)));
        }
        return toReturn;
    }
}



