/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
 
import java.time.LocalDate;
import java.util.ArrayList;
 
/**
 *
 * @author Mikkel
 */
public class Shift {
    private LocalDate date;
    private double startTime;
    private double endTime;
    private String expertise; //Typen skal muligvis ændres
    private int level; // Ekspertise niveau der er krævet
    private ArrayList<User> unassignedList;
    private User substitute;
   
    public Shift(LocalDate date, double startTime, double endTime, String zone){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.expertise = zone;
        this.unassignedList = new ArrayList<>();
    }
   
   
     public int getLevel() {
        return level;
    }
 
    public void setLevel(int level) {
        this.level = level;
    }
 
    public LocalDate getDate() {
        return date;
    }
 
    public void setDate(LocalDate date) {
        this.date = date;
    }
 
    public double getStartTime() {
        return startTime;
    }
 
    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }
 
    public double getEndTime() {
        return endTime;
    }
 
    public void setEndTime(double endTime) {
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
        return new ArrayList<>(unassignedList);
    }
   
}