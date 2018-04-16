package com.excilys.db.model;

import java.time.LocalDate;
import java.util.Objects;

public class Computer {
    private Integer id;
    private String name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private Company company;

    /**
     *
     */
    public Computer() {
        company = new Company();
    }


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getIntroduced() {
        return introduced;
    }
    public void setIntroduced(LocalDate date) {
        this.introduced = date;
    }
    public LocalDate getDiscontinued() {
        return discontinued;
    }
    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }
    public Company getCompany() {
        return company;
    }
    public void setCompany(Company companie) {
        this.company = companie;
    }

    @Override
    public String toString() {
        StringBuilder sB = new StringBuilder(200);
        sB.append(" | name=").append(this.getName());
        sB.append(" | introduced=").append(this.getIntroduced());
        sB.append(" | discontinued=").append(this.getDiscontinued());
        String compIdtoPrint;
        if (this.getCompany() == null) {
            compIdtoPrint = "null";
        } else {
            compIdtoPrint = this.getCompany().getName();
        }
        sB.append(" | companyId=").append(compIdtoPrint);
        return sB.toString();

    }
    
    
    private boolean equalsDate(Object obj) {
        if (this.getIntroduced()!=null) {
            if (!this.getIntroduced().equals(((Computer) obj).getIntroduced())) {
                return false;
            }
        }else {
            if (((Computer) obj).getIntroduced()!=null){
                return false;
            }
        }
        if (this.getDiscontinued()!=null) {
            if (!this.getDiscontinued().equals(((Computer) obj).getDiscontinued())) {
                return false;
            }
        }else {
            if (((Computer) obj).getDiscontinued()!=null){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (!this.getName().equals(((Computer) obj).getName())) {
            return false;
        }
        if (!this.equalsDate(obj)) {
            return false;
        }
        if ( this.getCompany() != null) {
        return this.getCompany().equals(((Computer) obj).getCompany());
        }else {
            return ((Computer) obj).getCompany()==null;
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(name) + Objects.hash(introduced) + Objects.hash(discontinued) + Objects.hash(company);
    }



}