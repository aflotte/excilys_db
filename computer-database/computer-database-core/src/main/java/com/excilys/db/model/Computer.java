package com.excilys.db.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "computer")
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Size(min=2, max=60)
    @Pattern(regexp="^[\\wÀ-ÿ]+[\\wÀ-ÿ_\\-'\\+\\*. ]+$")
    private String name;
    @Pattern(regexp="(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))")
    private LocalDate introduced;
    @Pattern(regexp="(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))")
    private LocalDate discontinued;
    @ManyToOne(optional = true)
    @JoinColumn(name = "company_id")
    private Company company;

    public Computer() {
        
    }
    
    
    
    /**
     *
     */
    public Computer(String name) {
        this.name = name;
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