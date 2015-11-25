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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
 
/**
 *
 * @author Mikkel
 */
public class Shift {
    private Date date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String expertise; //Typen skal muligvis ændres
    private ArrayList<User> unassignedList;
    private User substitute;
    private int id;
   
    public Shift(Date date, LocalTime startTime, LocalTime endTime, String zone, int id){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.expertise = zone;
        this.unassignedList = new ArrayList<>();
        this.id = id;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
 
    public Date getDate() {
        return date;
    }
 
    public void setDate(Date date) {
        this.date = date;
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
