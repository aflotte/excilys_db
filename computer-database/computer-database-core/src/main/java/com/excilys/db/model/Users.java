package com.excilys.db.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Users {    
    @Id
    private String username;

    private String password;
    private Boolean enable;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserRoles> userRoles;

    public Users(String username, String password, Boolean enabled) {
        this.username = username;
        this.password = password;
        this.enable = enabled;
    }

    public Users() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enable;
    }

    public void setEnabled(Boolean enabled) {
        this.enable = enabled;
    }

    public List<UserRoles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoles> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public String toString() {
        return "Users [username=" + username + ", password=" + password + ", enabled=" + enable + ", userRoles="
                + userRoles + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((enable == null) ? 0 : enable.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Users other = (Users) obj;
        if (enable == null) {
            if (other.enable != null)
                return false;
        } else if (!enable.equals(other.enable))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

}