/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
 
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
 
/**
 *
 * @author Mikkel
 */

public class Shift {
    private Date startDate;
    private Date endDate;
    private String startTime;
    private String endTime;
    private String expertise; //Typen skal muligvis ændres
    private ArrayList<User> unassignedList;
    private User substitute;
    private int id;
 
    public Shift(Date startDate,Date endDate, String startTime, String endTime, String zone){
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.expertise = zone;
        this.unassignedList = new ArrayList<>();
       
    }
 
    public int getId() {
        return id;
    }
 
    public Date getStartDate() {
        return startDate;
    }
 
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
 
    public Date getEndDate() {
        return endDate;
    }
 
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public Date getstartDate() {
        return startDate;
    }
 
    public void setstartDate(Date date) {
        this.startDate = date;
    }
 
   
    public String getStartTime() {
        return startTime;
    }
 
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
 
    public String getEndTime() {
        return endTime;
    }
 
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
 
    public String getExpertise() {
        return expertise;
    }
 
    public void setExpertise(String zone) {
        this.expertise = zone;
    }
 
    public User getSubstitute() {
        return substitute;
    }
 
    public void setSubstitute(User substitute) {
        this.substitute = substitute;
    }
   
    public ArrayList<User> getSubstitutes(){
        try{
        Connection conn = ConnectionToDB.getConnection();
        Statement s = conn.createStatement();
        s.executeQuery("SELECT user_mobilephone FROM usershifts WHERE id=" + this.getId());
        ResultSet rs = s.getResultSet();
        while(rs.next()){
           String mobile = rs.getString("user_mobilephone");
            s.executeQuery("SELECT * FROM user WHERE mobilephone=" + mobile);
            while(rs.next()){
                boolean admin = rs.getBoolean("adminboolean");
                String name = rs.getString("name");
                String middlename = rs.getString("middlename");
                String lastname = rs.getString("lastname");
                String mobilephone = rs.getString("mobilephone");
                String homephone = rs.getString("homephone");
                String emailaddress = rs.getString("emailaddress");
                String homeaddress = rs.getString("homeaddress");
                String pw = rs.getString("pw");
               
                User user = new User(admin, name, middlename, lastname, mobilephone, homephone, emailaddress, homeaddress, pw);
                unassignedList.add(user);
            }
        }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return unassignedList;
    }
   
}