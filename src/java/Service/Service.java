package Service;
 
import Model.ConnectionToDB;
import Model.Shift;
import Model.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultScheduleEvent;
 
@Named
@ApplicationScoped
public class Service {
   
    private final ArrayList<User> users;
    private final ArrayList<Shift> substituteList;
   
   
    public Service() {
        users = new ArrayList<>();
        User u1 = new User();
        User u2 = new User();
       
        u1.setUsername("abc");
        u1.setPassword("123");
        u1.setAdmin(true);
       
        u2.setUsername("def");
        u2.setPassword("456");
        u2.setAdmin(false);
       
        users.add(u1);
        users.add(u2);
    }
   
    private User getUser(User user) {
        User u = null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(user)) {
                u = users.get(i);
            }
           
        }
        return u;
       
    }
   
    public boolean loginOK(User user) {
        User u = getUser(user);
        if (u != null) {
            return user.equals(u);
        } else {
            return false;
        }
    }
   
    public boolean isAdmin(User user) {
        User u = getUser(user);
        if (u != null) {
            return u.isAdmin();
        } else {
            return false;
        }
    }
   
    public ArrayList< User> getUsers() {
        return users;
    }
   
    public static void createShift(LocalDate date, LocalTime startTime, LocalTime endTime, String zone){
     // Shift newShift = new Shift(date,startTime,endTime, zone);
      String getId = "Select id from shifts where id=(select max(id) from shifts)";
     
      try{
          int id = 0;
         Connection conn = ConnectionToDB.getConnection();
        Statement s = conn.createStatement();
         s.executeQuery(getId);
        ResultSet rs = s.getResultSet();
        while(rs.next()){
          id = rs.getInt("id");
          id++;
        }
         String sql = "INSERT INTO shifts VALUES ('"+id+"','"+date+"','"+startTime+"','"+endTime+"','"+zone+"')";
         s.execute(sql);
             
      
      }
      catch (Exception e){
          System.out.println(e.getMessage());
      }
    
    }
    
    public ArrayList<Shift> getShiftsFromDate(Date date){
        ArrayList<Shift> list = new ArrayList<Shift>();
        String sqlString = "select * from shift where date ='"+date+"'";
        try{
        Connection conn = ConnectionToDB.getConnection();
        Statement s = conn.createStatement();
        s.executeQuery(sqlString);
        ResultSet rs = s.getResultSet();
        while(rs.next()){
            Shift s1 = new Shift(rs.getInt("id"),rs.getDate(2),rs.getTime(3),rs.getTime(4));
            list.add(s1);
        }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        
        return list;
    }
   
    public void deleteShift(Shift shift){
        //TODOO
    }
   
    public void AcceptShiftSwap(){
        //TODOO - Admin skal acceptere bytning af vagter.
    }
   
    public void RequestShift(Shift shift, User user){
      if(shift.getSubstitute() == null)
      {
         
          if(shift.getExpertise().equals("red"))
          {
              if(shift.getLevel() <= user.getExpertises().get(0))
              {
                 
              }
          }
         
          shift.getSubstitutes().add(user);
      }
    }
    
    public ArrayList<DefaultScheduleEvent> loadShifts(){
        ArrayList<DefaultScheduleEvent> list = new ArrayList<DefaultScheduleEvent>();
        try{
        
        Connection conn = ConnectionToDB.getConnection();
        Statement s = conn.createStatement();
        String sql = "select * from shift";
        s.executeQuery(sql);
        ResultSet rs = s.getResultSet();
        while(rs.next()){
            int id = rs.getInt("id");
            Date startDate = rs.getDate("StartDate");
            Date endDate = rs.getDate("SlutDate");
            Time startTime = rs.getTime("startTime");
            Time endTime = rs.getTime("endTime");
            String zone = rs.getString("expertise");
            Shift s1 = new Shift(startDate,endDate,startTime,endTime,zone,id);
            
            DefaultScheduleEvent e = new DefaultScheduleEvent("vagt",startDate,endDate,s1);
            list.add(e);
        }
        }
        catch (Exception e){
           System.out.println(e.getMessage());
        }
        
        return list;
    }
            
 
}

