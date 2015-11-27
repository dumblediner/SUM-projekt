package Service;
 
import Model.ConnectionToDB;
import Model.Shift;
import Model.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultScheduleEvent;
 
@Named
@ApplicationScoped
public class Service {
   
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Shift> substituteList = new ArrayList<>();
    private Statement s;
    private ResultSet rs;
    private String str;
   
   
    public Service() {
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
   /*
    Denne metode skal opdateres, variabler passer ikke med sql sætninger
    */
   public static void createShift(Date startDate, Date endDate, Time startTime, Time endTime, String zone){
     // Shift newShift = new Shift(date,startTime,endTime, zone);
      String getId = "SELECT id FROM shifts WHERE id=(SELECT max(id) FROM shifts)";
     
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
         String sql = "INSERT INTO shifts VALUES ('"+id+"','"+startDate+"','"+endDate+"','"+startTime+"','"+endTime+"','"+zone+"')";
         s.execute(sql);   
      }
      catch (SQLException e){
          System.out.println(e.getMessage());
      }
    }
   
    public void deleteShift(Shift shift){
       try{
           Connection conn = ConnectionToDB.getConnection();
        Statement s = conn.createStatement();
        s.execute("DELETE FROM usershifts WHERE shifts_shiftid=" + shift.getId());
        s.execute("DELETE FROM shifts WHERE shiftid=" + shift.getId());
       } catch(SQLException e){
           System.out.println(e.getMessage());
       }
    }
   
    public void ConfirmShift(Shift shift, User user){
        Connection conn = null;
         try{
            conn = ConnectionToDB.getConnection();
            s = conn.createStatement();
          shift.setSubstitute(user);
          s.execute("DELETE FROM usershifts WHERE shifts_shiftid=" + shift.getId());
          s.execute("UPDATE shifts SET user_mobilephone=" + user.getMobilePhone() + "WHERE shiftid=" + shift.getId() + ";");
          user.getAssignedShifts().add(shift);
           
         } catch(SQLException e){
            System.out.println("SQL Exception" + e.getMessage());
        }
    }
    
   
    public void RequestShift(Shift shift, User user){
        Connection conn = null;
        
        try{
            conn = ConnectionToDB.getConnection();
            s = conn.createStatement();
      if(shift.getSubstitute() == null)
      {
         
          if(user.getExpertises().containsKey(shift.getExpertise()))
          {
              if(user.getExpertises().get(shift.getExpertise()) > 0)
              {
                  s.execute("INSERT INTO usershifts VALUES('" + user.getMobilePhone() + "','" + shift.getId() + "')");
                  shift.getSubstitutes().add(user);
              }
          } 
      }
        } catch(SQLException e){
            System.out.println("SQL Exception" + e.getMessage());
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
            int id = rs.getInt("id");
            Date startDate = rs.getDate("StartDate");
            Date endDate = rs.getDate("SlutDate");
            Time startTime = rs.getTime("startTime");
            Time endTime = rs.getTime("endTime");
            String zone = rs.getString("expertise");
            Shift s1 = new Shift(startDate,endDate,startTime,endTime,zone,id);
            list.add(s1);
        }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        
        return list;
    }

    public static ArrayList<DefaultScheduleEvent> loadShifts(){
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
            System.out.println(list);
            System.out.println("asdf");
        }
        catch (Exception e){
           System.out.println(e.getMessage());
        }
        
        return list;
    }
}