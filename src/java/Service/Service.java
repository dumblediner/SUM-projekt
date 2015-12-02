package Service;
 
import Model.ConnectionToDB;
import static Model.LoginBean.s;
import Model.Shift;
import Model.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
 
@Named
@ApplicationScoped
public class Service {
   
    private final ArrayList<User> users;
    private final ArrayList<Shift> substituteList;
    private static Statement s;
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
    
    public static String createUser(boolean admin, String name, String middlename, String surname, String mobilePhone, String homePhone, String emailAddress, String homeAddress, String password, String red, int redKey, String orange, int orangeKey, String blue, int blueKey, String green, int greenKey, HashMap<String, Integer> expertises) throws SQLException {
        Connection conn = null;
        String toReturn = null;
        expertises.put(blue, blueKey);
        expertises.put(red, redKey);
        expertises.put(orange, orangeKey);
        expertises.put(green, greenKey);
        
        
        
        try {
            conn = ConnectionToDB.getConnection();
          
            PreparedStatement s1 = conn.prepareStatement(
                    "INSERT INTO users values(?, ? ,? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            s1.setString(1, expertises.get("blue") + "");
            s1.setString(2, expertises.get("green") + "");
            s1.setString(3, expertises.get("orange") + "");
            s1.setString(4, expertises.get("red") + "");
            s1.setString(5, name);
            s1.setString(6, middlename);
            s1.setString(7, surname);
            s1.setString(8, mobilePhone);
            s1.setString(9, homePhone);
            s1.setString(10, emailAddress);
            s1.setString(11, homeAddress);
            s1.setString(12, Integer.toString(isAdmin(admin)));
            s1.setString(13, password);
            System.out.println(s + "bane?");
            s1.executeQuery();

        } catch (SQLException e) {
            System.out.println("Sql Exception :" + e.getMessage());
        }
        return toReturn;
    }
   
   public static Shift createShift(Date startDate, Date endDate, String startTime, String endTime, String zone){
     // OBS Varchar i DB Date i koden. check it out
      String getId = "SELECT shiftid FROM shifts WHERE shiftid=(SELECT max(shiftid) FROM shifts)";
     
      try{
          int id = 0;
         Connection conn = ConnectionToDB.getConnection();
        Statement s = conn.createStatement();
        
         s.executeQuery(getId);
        ResultSet rs = s.getResultSet();
        
        while(rs.next()){
          id = rs.getInt("shiftid");
         
          id++;
        }
         String sql = "INSERT INTO shifts VALUES ('"+id+"','"+startDate+"','" +endDate+"','"+startTime+"','"+endTime+"','"+zone+"',"+null+")";
          System.out.println(sql);
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
    
    public void ConfirmSwap(Shift shift1, Shift shift2)
    {
        Shift temp1 = shift1;
        Shift temp2 = shift2;
        
        shift1.setSubstitute(temp2.getSubstitute());
        shift2.setSubstitute(temp1.getSubstitute());
        
         Connection conn = null;
         try{
            conn = ConnectionToDB.getConnection();
            s = conn.createStatement();
          s.execute("UPDATE SHIFTS SET user_mobilephone=" + shift1.getSubstitute().getMobilePhone());
         s.execute("UPDATE SHIFTS SET user_mobilephone=" + shift2.getSubstitute().getMobilePhone());
  
           
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
    
    public static int isAdmin(boolean admin){
        
        if(admin == true){
            return 1;
        }
        else{
            return 0;
        }
    }
    
    public static ArrayList<DefaultScheduleEvent> loadShifts(){
        ArrayList<DefaultScheduleEvent> list = new ArrayList<DefaultScheduleEvent>();
       
        try{
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dff = new SimpleDateFormat("hh");
        Connection conn = ConnectionToDB.getConnection();
        Statement s = conn.createStatement();
           
        String sql = "select * from shifts";
        s.execute(sql);
           
        ResultSet rs = s.getResultSet();
        while(rs.next()){
            int id = rs.getInt("shiftid");
            String startddate = rs.getString(2);
            System.out.println("DAG 1" + startddate);
            java.util.Date startDate = df.parse(startddate);
            String slutddate = rs.getString(3);
            System.out.println("DAG 2"+ slutddate);
            java.util.Date endDate = df.parse(slutddate);
            String startTime = rs.getString(4);
            String endTime = rs.getString(5);
            String zone = rs.getString("zone");
            int startTTime = Integer.parseInt(startTime);
            int endTTime = Integer.parseInt(endTime);
            startDate.setHours(startTTime);
            endDate.setHours(endTTime);
           
           
//            Calendar t = Calendar.getInstance();
//            //startDate.setHours(Integer.parseInt(startTime));
//            //endDate.setHours(Integer.parseInt(endTime));
//            System.out.println("PIIIIIIIIIIIIIK: " + startDate);
//            
//            t.set(t.get(Calendar.YEAR), t.get(Calendar.MONTH), t.get(Calendar.DATE), 0, 0, 0);
//            t.set(Calendar.DAY_OF_YEAR, startDate.getYear());
//            t.set(Calendar.DAY_OF_MONTH, startDate.getMonth());
//            t.set(Calendar.DATE, startDate.getDay());
//          
//            t.set(Calendar.HOUR,startTime.getHours());
//          
//            Calendar tt = Calendar.getInstance();
//            tt.set(tt.get(Calendar.YEAR), tt.get(Calendar.MONTH), tt.get(Calendar.DATE), 0, 0, 0);
//            tt.set(Calendar.DAY_OF_YEAR, endDate.getYear());
//            tt.set(Calendar.DAY_OF_MONTH, endDate.getMonth());
//            tt.set(Calendar.DATE, endDate.getDay());
//            tt.set(Calendar.HOUR,endTime.getHours());
            System.out.println("STARTDATE: "+startDate + "ENDDATE: " + endDate);
            Shift s1 = new Shift(startDate,endDate,startTime,endTime,zone);
            DefaultScheduleEvent e = new DefaultScheduleEvent("vagt",startDate,endDate,s1);
            //System.out.println("S1s starttid"+ s1.getStartDate()+"S1 endtime"+ s1.getEndDate()+"T0 " + t.getTime()+"TTTT"+tt.getTime());
            list.add(e);
        }
           
           
        }
        catch (Exception e){
           System.out.println(e.getMessage());
        }
       
        return list;
    }
}
