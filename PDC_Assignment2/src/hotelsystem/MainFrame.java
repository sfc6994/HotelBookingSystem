/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Cameron
 */
public class MainFrame extends JFrame {

    private HotelSystem hotelSystem;

    public MainFrame(HotelSystem hotelSystem) {
        this.hotelSystem = hotelSystem;

        setTitle("Hotel System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1, 10, 10));

        JButton adminButton = new JButton("Admin");
        JButton guestButton = new JButton("Guest");

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = JOptionPane.showInputDialog(
                        MainFrame.this,
                        "Enter Admin Password:",
                        "Admin Login",
                        JOptionPane.PLAIN_MESSAGE
                );

                if (password == null) {
                    return;
                }

                if (hotelSystem.verifyPassword(password)) {
                    new AdminPanel(hotelSystem).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(
                            MainFrame.this,
                            "Incorrect password. Please try again.",
                            "Access Denied",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        guestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GuestPanel(hotelSystem).setVisible(true);
            }
        });

        add(adminButton);
        add(guestButton);
    }
}
