/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;

/**
 *
 * @author Mikkel
 */
public class TestKlasse {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Date date1 = Date.valueOf("2015-11-29");
        Date date2 = Date.valueOf("2015-11-29");
        
     
       Service.Service.createShift(date1, date2, "08:00", "16:00", "Grøn");
    }
    
}
