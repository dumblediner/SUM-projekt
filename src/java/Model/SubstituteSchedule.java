package Model;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
 
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import Service.Service;
 
@ManagedBean
@ViewScoped
public class SubstituteSchedule implements Serializable {
    
    private Date startDate;
    
    private Date slutDate;
    
    private String chosenZone;

    private ArrayList<String> colors;

    private ScheduleModel eventModel;
     
    private ScheduleModel lazyEventModel;
 
    private ScheduleEvent event = new DefaultScheduleEvent();
    
    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        eventModel.addEvent(new DefaultScheduleEvent("Test", previousDay8Pm(8), previousDay11Pm(11)));
        eventModel.addEvent(new DefaultScheduleEvent("Tester", previousDay8Pm(9), previousDay11Pm(12)));
        eventModel.addEvent(new DefaultScheduleEvent("Testerer", previousDay8Pm(10), previousDay11Pm(13)));
        /*Date start = new Date();
        Calendar cs = (Calendar) today().clone();
        cs.set(2015, 11, 25, 11, 00, 00);
        Calendar ce = (Calendar) today().clone();
        ce.set(2015, 11, 25, 12, 00, 00);
        eventModel.addEvent(new DefaultScheduleEvent("Tester",cs.getTime(),ce.getTime())); */
        
    // for hver vagt i Databasen eventModel.addEvent(new Default ScheduleEvent("IOvermorgen", theDayAfter3Pm(), fourDaysLater3pm()));
    }
    public SubstituteSchedule(){
        this.startDate = event.getStartDate();
        this.slutDate = event.getEndDate();
    }
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getSlutDate() {
        return slutDate;
    }

    public void setSlutDate(Date slutDate) {
        this.slutDate = slutDate;
    }
    private Date previousDay8Pm(int tal) {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, tal);
         
        return t.getTime();
    }
     
    private Date previousDay11Pm(int tal) {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, tal);
         
        return t.getTime();
    }
    
    public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(Calendar.DATE, ((int) (Math.random()*30)) + 1);    //set random day of month
         
        return date.getTime();
    }
     
    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);
         
        return calendar.getTime();
    }
     
    public ScheduleModel getEventModel() {
        return eventModel;
    }
     
    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }
 
    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
 
        return calendar;
    }
     
    public ScheduleEvent getEvent() {
        return event;
    }
 
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
     
    public void addEvent(ActionEvent actionEvent) {
        if(event.getId() == null){
            eventModel.addEvent(event);
            Date d1 = event.getStartDate();
            Date d2 = event.getEndDate();
            Instant instant = Instant.ofEpochMilli(d1.getTime());
            LocalTime startDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
            instant = Instant.ofEpochMilli(d2.getTime());
            LocalTime endDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
            String title = event.getTitle();
            String zone = getChosenZone();
            
            //Service.createShift(d1,startDateTime,endDateTime,zone);
            
            
        }else
            eventModel.updateEvent(event);
         
        event = new DefaultScheduleEvent();
    }
    public void tilmeldVagt(ActionEvent ae){
        Date d1 = event.getStartDate();
        Date d2 = event.getEndDate();
        String zone = getChosenZone();
        
        // Service."tilmeldvagt"(
    }
    public ArrayList<String> getVagterForBestemtDag(){
        Date date = event.getStartDate();
        ArrayList<String> list=null;
        //Service.getVagterForBestemtDag(date){
        return list;
    }
     
    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }
     
    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
        public String getChosenZone() {
        return chosenZone;
    }

    public void setChosenZone(String chosenZone) {
        this.chosenZone = chosenZone;
    }
    public ArrayList<String> getColors(){
        return colors;
    }
    public void addcolor(String color){
        colors.add(color);
    }
    
}