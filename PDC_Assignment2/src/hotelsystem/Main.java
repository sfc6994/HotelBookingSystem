/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelsystem;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.UIManager;


/**
 *
 * @author Cameron
 */
//Password for Admin is Admin123
public class Main {

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            HotelSystem hotelSystem = new HotelSystem();
            MainFrame mfe = new MainFrame(hotelSystem);
            mfe.setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database connection has failed: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to set look and feel: " + ex.getMessage());
        }
    }
}
