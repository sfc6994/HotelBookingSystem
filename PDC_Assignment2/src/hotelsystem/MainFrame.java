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
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DatabaseManager.getDataBaseManagerInstance().shutdownDB();
                System.exit(0);
            }
        });

        JLabel titleLabel = new JLabel("Hotel System Main Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JButton adminButton = new JButton("Admin Login");
        JButton guestButton = new JButton("Guest Booking Form");
        adminButton.setPreferredSize(new Dimension(170, 40));
        guestButton.setPreferredSize(new Dimension(170, 40));
        
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 40)){
            private Image image;
            
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                image = new ImageIcon("hotelmain.jpg").getImage();
                
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        imagePanel.add(adminButton);
        imagePanel.add(guestButton);

        add(titleLabel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
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

    }
}
