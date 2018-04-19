package com.excilys.db.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ComputerDTO {

    private Integer id;
    @NotNull
    @Size(min=2, max=60)
    @Pattern(regexp="^[\\wÀ-ÿ]+[\\wÀ-ÿ_\\-'\\+\\*. ]+$")
    private String name;
    private String company;
    @Pattern(regexp="(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))")
    private String introduced;
    @Pattern(regexp="(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))")
    private String discontinued;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
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
