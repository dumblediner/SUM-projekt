/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import Service.Service;

/**
 *
 * @author Mikkel
 */
@SessionScoped
@ManagedBean
public class ShiftBean implements Serializable {
    
    public void createShiftBean(Date startDate, Date endDate, String startTime, String endTime, String zone){
        java.util.Date utilDate1 = startDate;
        java.sql.Date sqlDate1 = new java.sql.Date(utilDate1.getTime());
        java.util.Date utilDate2 = endDate;
        java.sql.Date sqlDate2 = new java.sql.Date(utilDate1.getTime());
        
        
        Service.createShift(sqlDate1, sqlDate2, startTime, endTime, zone);
    }
    
}
