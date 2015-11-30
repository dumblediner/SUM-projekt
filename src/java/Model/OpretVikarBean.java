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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author nikolaj
 */
@SessionScoped
@ManagedBean
public class OpretVikarBean implements Serializable {

    private boolean admin;
    private boolean skip;
    private User user = new User();

    private ArrayList<Integer> allowedZoneLevels = new ArrayList<Integer>() {
        {
            this.add(0);
            this.add(1);
            this.add(2);
        }
    };

    private Map<String, Integer> expertises = new HashMap<String, Integer>() {
        {
            put("red", 0);
            put("blue", 0);
            put("green", 0);
            put("orange", 0);
            user.setExpertises(this);
        }
    };
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

    public void add(String key, Integer value) {
        expertises.put(key, value);
    }

    public Integer getObject(String key) {
        return expertises.get(key);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int isAdmin() {
        if (admin) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
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

    public Map<String, Integer> getExpertises() {
        return expertises;
    }

    public void setExpertises(Map<String, Integer> expertises) {
        this.expertises = expertises;
    }

    public String opretVikar() throws SQLException {
        Connection conn = null;
        String toReturn = null;
        try {
            conn = ConnectionToDB.getConnection();
            s = conn.prepareStatement(
                    "INSERT INTO users values(?, ? ,? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

            s.setString(1, user.getExpertises().get("orange") + "");
            s.setString(2, user.getExpertises().get("blue") + "");
            s.setString(3, user.getExpertises().get("green") + "");
            s.setString(4, user.getExpertises().get("red") + "");
            s.setString(5, user.getName());
            s.setString(6, user.getMiddlename());
            s.setString(7, user.getSurname());
            s.setString(8, user.getMobilePhone());
            s.setString(9, user.getHomePhone());
            s.setString(10, user.getEmailAddress());
            s.setString(11, user.getHomeAddress());
            s.setString(12, Integer.toBinaryString(isAdmin()));
            s.setString(13, user.getPassword());
            System.out.println(s + "bane?");
            s.executeQuery();

        } catch (SQLException e) {
            System.out.println("Sql Exception :" + e.getMessage());
        }
        return toReturn;
    }

    //Hent alle Users
    public ArrayList<User> getAllSubstitute() throws SQLException {
        ArrayList<User> arr = new ArrayList<User>();

        try {
            Connection conn = ConnectionToDB.getConnection();
            Statement sm = conn.createStatement();
            ResultSet rs = null;
            System.out.println("HEJ");
            sm.executeQuery("SELECT name,middlename,surname,mobilephone,homephone,emailaddress,homeaddress,adminboolean,orangeexpertises_Orange,blueexpertises_Blue,greenexpertises_Green,redexpertises_Red FROM users");
            rs = sm.getResultSet();

            while (rs.next()) {
                HashMap<String, Integer> temp = new HashMap();
                User user = new User();
                user.setName(rs.getString("name"));
                user.setMiddlename(rs.getString("middlename"));
                user.setSurname(rs.getString("surname"));
                user.setMobilePhone(rs.getString("mobilephone"));
                user.setHomePhone(rs.getString("homephone"));
                user.setEmailAddress(rs.getString("emailaddress"));
                user.setAdmin(rs.getBoolean("adminboolean"));
                temp.put("orange", rs.getInt("orangeexpertises_Orange"));
                temp.put("blue", rs.getInt("blueexpertises_Blue"));
                temp.put("green", rs.getInt("greenexpertises_Green"));
                temp.put("red", rs.getInt("redexpertises_Red"));
                System.out.println("GRISEFAR");
                user.setExpertises(temp);
                arr.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OpretVikarBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public ArrayList<Integer> getAllowedZoneLevels() {
        return allowedZoneLevels;
    }

    public void setAllowedZoneLevels(ArrayList<Integer> allowedZoneLevels) {
        this.allowedZoneLevels = allowedZoneLevels;
    }

}
