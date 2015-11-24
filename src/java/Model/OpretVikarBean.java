/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static Model.LoginBean.rs;
import static Model.LoginBean.s;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author nikolaj
 */
@SessionScoped
@ManagedBean
public class OpretVikarBean implements Serializable {

    private boolean admin;
    private User user = new User();
    private Map<String, Integer> expertises = new HashMap();
    private String color;

    private String name;
    private String middlename;
    private String surname;
    private String password;
    private String mobilePhone;
    private String homePhone;
    private String emailAddress;
    private String homeAddress;
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setExpertises(Map<String, Integer> expertises) {
        this.expertises = expertises;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Map<String, Integer> getExpertises() {
        return expertises;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public void opretVikar() throws SQLException {
        Connection conn = null;
        try {
            conn = ConnectionToDB.getConnection();
            s = conn.prepareStatement(
                    "INSERT INTO users values(?, ? ,? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            s.setString(1, user.getExpertises().get(color) + "");
            s.setString(2, user.getExpertises().get(color) + "");
            s.setString(3, user.getExpertises().get(color) + "");
            s.setString(4, user.getExpertises().get(color) + "");
            s.setString(5, user.getName());
            s.setString(6, user.getMiddlename());
            s.setString(7, user.getSurname());
            s.setString(8, user.getMobilePhone());
            s.setString(9, user.getHomePhone());
            s.setString(10, user.getEmailAddress());
            s.setString(11, user.getHomeAddress());
            s.setBoolean(12, isAdmin());
            s.setString(13, user.getPassword());
            s.executeQuery();

        } catch (SQLException e) {
            System.out.println("Sql Exception :" + e.getMessage());
        }
    }
}
