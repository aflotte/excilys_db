package com.excilys.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.db.model.Company;

@Component
public class RowMapperCompany implements RowMapper<Company>{

    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        Company result = new Company();
        result.setId(rs.getInt("company.id"));
        result.setName(rs.getString("company.name"));
        return result;
    }

}
