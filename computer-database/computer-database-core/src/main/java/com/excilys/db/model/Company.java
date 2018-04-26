package com.excilys.db.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Company.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     */
    public Company() {
        super();
        name = "";
        id = null;
    }

    /**
     *
     * @param id de la compagnie a creer
     */
    public Company(Integer id) {
        this.id = id;
        name = "";
    }

    @Override
    public String toString() {
        StringBuilder sB = new StringBuilder(200);
        sB.append(" | id=").append(this.getId());
        sB.append(" | name=").append(this.getName());
        return sB.toString();

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
        if (!(this.getName().equals(((Company) obj).getName()))) {
            return false;
        }
        if (this.getId() == null) {
            if (((Company) obj).getId() != null) {
                return false;
            }
        } else {
            if ((!this.getId().equals(((Company) obj).getId()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name) + Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}