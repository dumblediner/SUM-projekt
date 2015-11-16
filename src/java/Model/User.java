/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Dumblediner
 */
public class User {
    private boolean admin;
    private ArrayList<String> expertises;
    private String name;
    private String middlename;
    private String surname;
    
    private String mobilePhone;
    private String homePhone;
    private String emailAddress;
    private String homeAddress;

    public User(boolean admin, String name, String middlename, String surname, String mobilePhone, String homePhone, String emailAddress, String homeAddress) {
        this.admin = admin;
        this.name = name;
        this.middlename = middlename;
        this.surname = surname;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.emailAddress = emailAddress;
        this.homeAddress = homeAddress;
    }
    
    
    //Simple getters and setters
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public ArrayList<String> getExpertises() {
        return expertises;
    }

    public void setExpertises(ArrayList<String> expertises) {
        this.expertises = expertises;
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
    
    
}


//TODO overvej arraylist til styring af hvilke zoner og kompetencer en bruger har.
//sæt en zone til altid at have en specifik indeksplads, så data kan lagres så nemt som muligt
//f.eks. grøn = 0,1,2 afhængig af deres niveau.
