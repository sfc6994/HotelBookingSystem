/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelsystem;

import java.sql.*;

/**
 *
 * @author Cameron
 */
//Password for Admin is Admin123
public class Main {

    public static void main(String args[]) {
        
        try{
        HotelSystem hotelSystem = new HotelSystem();
        MainFrame mfe = new MainFrame(hotelSystem);
        mfe.setVisible(true);
        }catch(SQLException ex){
            System.err.println("Error: " + ex.getMessage());
        }
    }
}
