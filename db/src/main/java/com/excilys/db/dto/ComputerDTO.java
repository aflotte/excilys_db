package com.excilys.db.dto;

import java.time.LocalDate;

public class ComputerDTO {

    private Integer id;
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    private String company;
    private String introduced;
    private String discontinued;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String compagnie) {
        company = compagnie;
    }
    public String getIntroduced() {
        return introduced;
    }
    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }
    public String getDiscontinued() {
        return discontinued;
    }
    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }
}
