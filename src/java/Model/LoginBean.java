/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author nikolaj
 */
@SessionScoped
@ManagedBean
public class LoginBean implements Serializable {

    private String emailaddress;
    private String password;
    private User user = new User();

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static PreparedStatement s = null;
    public static ResultSet rs = null;
    public static String str = null;

    public String loginCheck() {
        Connection conn = null;
        String toReturn = "";
        try {
            conn = ConnectionToDB.getConnection();
            s = conn.prepareStatement("SELECT * FROM users WHERE emailaddress = ? AND pw =?");
            s.setString(1, user.getEmailAddress());
            s.setString(2, user.getPassword());
//            s.executeQuery("SELECT * FROM users WHERE emailaddress = " + "'" + user.getEmailAddress() + "'" + " AND pw =" + "'" + user.getPassword() + "'");
            rs = s.executeQuery();
            if (rs.next()) {
                HashMap<String, Integer> temp = new HashMap();
                String name = rs.getString("name");
                String middlename = rs.getString("middlename");
                String surname = rs.getString("surname");
                String email = rs.getString("emailaddress");
                String mobilephone = rs.getString("mobilephone");
                String homephone = rs.getString("homephone");
                String homeaddress = rs.getString("homeaddress");
                boolean adminboolean = rs.getBoolean("adminboolean");
                String pw = rs.getString("pw");
                temp.put("orange", rs.getInt("orangeexpertises_Orange"));
                temp.put("blue", rs.getInt("blueexpertises_Blue"));
                temp.put("green", rs.getInt("greenexpertises_Green"));
                temp.put("red", rs.getInt("redexpertises_Red"));

                user.setName(name);
                user.setMiddlename(middlename);
                user.setSurname(surname);
                user.setEmailAddress(email);
                user.setMobilePhone(mobilephone);
                user.setHomePhone(homephone);
                user.setHomeAddress(homeaddress);
                user.setAdmin(adminboolean);
                user.setPassword(pw);

                if (user.isAdmin() == true) {
                    toReturn = "adminWelcome";
                } else {
                    toReturn = "welcome";
                }
            } else {
                toReturn = "error";
            }

        } catch (SQLException e) {
            System.out.println("Sql Exception  :" + e.getMessage());
        }
        return toReturn;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }
}

//    public void opretVikar() {
//        Connection conn = null;
//        String toReturn = "";
//        try {
//            conn = ConnectionToDB.getConnection();
//            s = conn.prepareStatement("SELECT * FROM users WHERE emailaddress = ? AND pw =?");
//            s.setString(1, user.getEmailAddress());
//            s.setString(2, user.getPassword());
////            s.executeQuery("SELECT * FROM users WHERE emailaddress = " + "'" + user.getEmailAddress() + "'" + " AND pw =" + "'" + user.getPassword() + "'");
//            rs = s.executeQuery();
//            if (rs.next()) {
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Sql Exception :" + e.getMessage());
//        }
//        return toReturn;
//    }
