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
import java.time.LocalTime;
import java.util.ArrayList;
 
/**
 *
 * @author Mikkel
 */
public class Shift {
    private Date startDate;
    private Date endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String expertise; //Typen skal muligvis ændres
    private ArrayList<User> unassignedList;
    private User substitute;
    private int id;
   
    public Shift(Date Startdate, Date Enddate, LocalTime startTime, LocalTime endTime, String expertise){
        this.startDate = Startdate;
        this.endDate = Enddate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.expertise = expertise;
        this.unassignedList = new ArrayList<>();
        
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
 
    public Date getStartDate() {
        return startDate;
    }
 
    public void setStartDate(Date date) {
        this.startDate = date;
    }
    
       public Date getEndDate() {
        return endDate;
    }
 
    public void setEndDate(Date date) {
        this.endDate = date;
    }
 
    public LocalTime getStartTime() {
        return startTime;
    }
 
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
 
    public LocalTime getEndTime() {
        return endTime;
    }
 
    public void setEndTime(LocalTime endTime) {
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
