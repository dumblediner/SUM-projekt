package Service;
 
import Model.Shift;
import Model.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
 
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
   
    public void createShift(LocalDate date, double startTime, double endTime, String zone){
       //TODOO - mulig return statement
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
 
}