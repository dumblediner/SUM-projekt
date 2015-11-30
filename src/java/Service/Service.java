package Service;
 
import Model.ConnectionToDB;
import Model.Shift;
import Model.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
 
@Named
@ApplicationScoped
public class Service {
   
    private final ArrayList<User> users;
    private final ArrayList<Shift> substituteList;
    private Statement s;
    private ResultSet rs;
    private String str;
   
   
    public Service() {
//        users = new ArrayList<>();
//        User u1 = new User();
//        User u2 = new User();
//       
//        u1.setUsername("abc");
//        u1.setPassword("123");
//        u1.setAdmin(true);
//       
//        u2.setUsername("def");
//        u2.setPassword("456");
//        u2.setAdmin(false);
//       
//        users.add(u1);
//        users.add(u2);
    
   
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
   
   public static Shift createShift(Date startDate, Date endDate, LocalTime startTime, LocalTime endTime, String zone){
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
         String sql = "INSERT INTO shifts VALUES ('"+id+"','"+startDate+"','" +endDate+"','"+startTime+"','"+endTime+"','"+zone+"')";
         s.execute(sql);   
      }
      catch (SQLException e){
          System.out.println(e.getMessage());
      }
   Shift shift = new Shift(startDate, endDate, startTime, endTime, zone);
   return shift;
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
    
   
 
}
