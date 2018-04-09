package com.excilys.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.db.dto.CompanyDTO;
import com.excilys.db.model.Company;

public class CompaniesMapper {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompaniesMapper.class);

    private CompaniesMapper() {

    }

    /**
     *
     * @param rs le ResultSet a interpreter
     * @return la compagnie
     */
    public static Company computerResultToCompanies(ResultSet rs) {
        Company result = new Company();
        try {
            result.setId(rs.getInt("company.id"));
            result.setName(rs.getString("company.name"));
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return result;
    }

    public static CompanyDTO companyToDTO(Company company) {
        CompanyDTO toReturn = new CompanyDTO();
        toReturn.setId(company.getId());
        toReturn.setName(company.getName());
        return toReturn;
    }
}


