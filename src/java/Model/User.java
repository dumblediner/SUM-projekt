/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
 
import java.util.ArrayList;
import java.util.HashMap;
 
 
public class User {
    private boolean admin;
    private HashMap<String, Integer> expertises;
    private ArrayList<Shift> desiredShifts;
    private ArrayList<Shift> assignedShifts;
    private String name;
    private String middlename;
    private String surname;
    private String password;
    private String mobilePhone;
    private String homePhone;
    private String emailAddress;
    private String homeAddress;
 
 public User(boolean admin, String name, String middlename, String surname, String mobilePhone, String homePhone, String emailAddress, String homeAddress, String password) {
        this.admin = admin;
        this.name = name;
        this.middlename = middlename;
        this.surname = surname;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.emailAddress = emailAddress;
        this.homeAddress = homeAddress;
        this.password = password;
    }
   
 
   
    //Simple getters and setters
 
 public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isAdmin() {
        return admin;
    }
 
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
 
    public HashMap<String, Integer> getExpertises() {
        return expertises;
    }
 
    public void setLevel(String expertise, Integer level) {
        this.expertises.get(expertise). = level;
    }
   
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getMiddlename() {
        return middlename;
    }
 
    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }
 
    public String getSurname() {
        return surname;
    }
 
    public void setSurname(String surname) {
        this.surname = surname;
    }
 
    public String getMobilePhone() {
        return mobilePhone;
    }
 
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
 
    public String getHomePhone() {
        return homePhone;
    }
 
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }
 
    public String getEmailAddress() {
        return emailAddress;
    }
 
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
 
    public String getHomeAddress() {
        return homeAddress;
    }
 
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }
   
    public ArrayList<Shift> getShifts(){
        return new ArrayList<>(desiredShifts);
    }
   
   
}