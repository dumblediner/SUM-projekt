/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Mikkel
 */
public class Shift {
    private LocalDateTime time;
    private String zone; //Typen skal muligvis ændres
    private ArrayList<User> substituteList;
    private User substitute;
    
    public Shift(LocalDateTime time, String zone){
        this.time = time;
        this.zone = zone;
        this.substituteList = new ArrayList<>();
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
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
        return new ArrayList<>(substituteList);
    }
    
}
