/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import org.jboss.logging.Logger;

/**
 *
 * @author nikolaj
 */
@Named
@ManagedBean
@RequestScoped
public class LoginBean implements Serializable {

//    private String username;
//    private String password;
//    private boolean admin;
    private User user;

    public User getUser() {
        return user;
    }

    public static Statement s = null;
    public static ResultSet rs = null;
    public static String str = null;

    public String loginCheck() {
        Connection conn = null;
        String toReturn = "";
        try {
            conn = ConnectionToDB.getConnection();
            s = conn.createStatement();
            s.executeQuery("SELECT * FROM person WHERE navn = " + "'" + user.getEmailAddress() + "'" + " AND stilling =" + "'" + user.getPassword() + "'");
            rs = s.getResultSet();
            if (rs.next()) {
                System.out.println("rs next");
                toReturn = "welcome";
            } else {
                toReturn = "error";
            }

        } catch (SQLException e) {
            System.out.println("Sql Exception :" + e.getMessage());
        }
        return toReturn;
    }
}
