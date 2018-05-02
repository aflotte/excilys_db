package com.excilys.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.model.Company;
import com.excilys.db.model.Computer;
import com.excilys.db.service.CompagnyIdable;

@Component
public class ComputerMapper {

    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerMapper.class);

    
    /**
     *
     */
    private ComputerMapper() {

    }

    /**
     *
     * @param resultSet le ResultSet a interpreter
     * @return l'ordinateur
     * @throws SQLException en cas d'erreur de connection
     */
    public static Computer resultToComputer(ResultSet resultSet) throws SQLException {
        Computer toReturn = new Computer(resultSet.getString(1));
        if (resultSet.getDate(2) != null) {
            toReturn.setIntroduced(resultSet.getDate(2).toLocalDate());
        } else {
            toReturn.setIntroduced(null);
        }
        if (resultSet.getDate(3) != null) {
            toReturn.setDiscontinued(resultSet.getDate(3).toLocalDate());
        } else {
            toReturn.setDiscontinued(null);
        }
        toReturn.setCompany(CompaniesMapper.computerResultToCompanies(resultSet));
        toReturn.setId(resultSet.getInt(5));
        return toReturn;
    }

    /**
     *
     * @param computer l'ordinateur
     * @return l'ordinateur DTO
     */
    public static ComputerDTO computerToDTO(Computer computer) {
        ComputerDTO toReturn = new ComputerDTO();
        toReturn.setId(computer.getId());
        if (computer.getCompany() != null) {
            toReturn.setCompany(computer.getCompany().getName());
        } else {
            toReturn.setCompany("");
        }
        toReturn.setName(computer.getName());
        if (computer.getDiscontinued() == null) {
            toReturn.setDiscontinued("");
        } else {
            toReturn.setDiscontinued(computer.getDiscontinued().toString());
        }
        if (computer.getIntroduced() == null) {
            toReturn.setIntroduced("");
        } else {
            toReturn.setIntroduced(computer.getIntroduced().toString());
        }

        return toReturn;
    }

    /**
     *
     * @param computer la liste des ordinateurs
     * @return la liste des ordinateurs DTO
     */
    public static List<ComputerDTO> computerListToComputerDTO(List<Computer> computer) {
        List<ComputerDTO> toReturn = new ArrayList<>();
        for (int i = 0; i < computer.size(); i++) {
            toReturn.add(computerToDTO(computer.get(i)));
        }
        return toReturn;
    }

    /**
     *
     * @param computer l'ordinateur DTO
     * @return l'ordinateur
     */
    public Computer computerDTOToComputer(ComputerDTO computer, CompagnyIdable service) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.FRANCE);
        Computer toReturn = new Computer(computer.getName());
        toReturn.setId(computer.getId());
        Company company = new Company();
        company.setName(computer.getCompany());
        Integer id = service.getCompagnyId(computer.getCompany());
        company.setId(id);
        toReturn.setCompany(company);
        if (computer.getDiscontinued() != null) {

            try {
                toReturn.setDiscontinued(LocalDate.parse(computer.getDiscontinued(), formatter));
            } catch (Exception e) {
                toReturn.setDiscontinued(null);
            }
        } else {
            toReturn.setDiscontinued(null);
        }
        if (computer.getIntroduced() != null) {
            try {
                toReturn.setIntroduced(LocalDate.parse(computer.getIntroduced(), formatter));
            } catch (Exception e) {
                toReturn.setIntroduced(null);
            }
        } else {
            toReturn.setIntroduced(null);
        }
        return toReturn;
    }

    /**
     *
     * @param computer la liste des ordinateurs DTO
     * @return la liste des ordinateurs
     */
    public List<Computer> computerDTOListToComputer(List<ComputerDTO> computer, CompagnyIdable service) {
        List<Computer> toReturn = new ArrayList<>();
        for (int i = 0; i < computer.size(); i++) {
            toReturn.add(computerDTOToComputer(computer.get(i),service));
        }
        return toReturn;
    }
}
