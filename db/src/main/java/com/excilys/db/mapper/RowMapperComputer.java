package com.excilys.db.mapper;
import com.excilys.db.model.Company;
import com.excilys.db.model.Computer;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class RowMapperComputer implements RowMapper<Computer>{

    @Override
    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Computer result = new Computer();
        result.setId(rs.getInt("computer.id"));
        result.setName(rs.getString("computer.name"));
        if (rs.getDate("computer.introduced")!=null) {
            result.setIntroduced(rs.getDate("computer.introduced").toLocalDate());
        }else {
            result.setIntroduced(null);
        }
        if (rs.getDate("computer.discontinued")!=null) {
            result.setDiscontinued(rs.getDate("computer.discontinued").toLocalDate());
        }else {
            result.setDiscontinued(null);
        }
        Company company = new Company();
        company.setId(rs.getInt("company.id"));
        company.setName(rs.getString("company.name"));
        result.setCompany(company);
        return result;
    }

}
