/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author Mikkel
 */
public class Shift {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String zone; //Typen skal muligvis ændres
    private ArrayList<User> unassignedList;
    private User substitute;
    public Shift(LocalDate date, LocalTime startTime, LocalTime endTime, String zone){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.zone = zone;
        this.unassignedList = new ArrayList<>();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public User getSubstitute() {
        return substitute;
    }

    public void setSubstitute(User substitute) {
        this.substitute = substitute;
    }
    
    public ArrayList<User> getSubstitutes(){
        return new ArrayList<>(unassignedList);
    }
    
}
