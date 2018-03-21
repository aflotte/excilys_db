package com.excilys.db.dto;

import java.time.LocalDate;

public class ComputerDTO {

    private Integer id;
    private String Compagnie;
    private LocalDate introduced;
    private LocalDate discontinued;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCompagnie() {
        return Compagnie;
    }
    public void setCompagnie(String compagnie) {
        Compagnie = compagnie;
    }
    public LocalDate getIntroduced() {
        return introduced;
    }
    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }
    public LocalDate getDiscontinued() {
        return discontinued;
    }
    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }
}
