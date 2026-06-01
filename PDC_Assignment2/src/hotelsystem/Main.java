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
        } catch (NullPointerException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database connection has failed:\nPlease make sure only one instance is running", "Connection Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            System.err.println("Error: Default Look and Feel fall back used");
        }
    }
}
