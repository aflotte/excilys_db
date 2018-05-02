package com.excilys.db.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private int userRoleId;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "username")
    private User user;
    
    private String role;
    
    public UserRoles() {
        this.role = "ROLE_USER";
    }
    
    public UserRoles(User user, String role) {
        this.user = user;
        this.role = role;
    }
    
    public int getUserRoleId() {
        return userRoleId;
    }
    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        return "UserRoles [userRoleId=" + userRoleId  + ", role=" + role + ", user=" + user
                + "]";
    }

}