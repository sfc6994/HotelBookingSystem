/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelsystem;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Cameron Local
 */
public class GuestPanel extends JFrame {

    private HotelSystem hotelSystem;
    private JTextField guestName;
    private JComboBox<String> roomType;
    private JComboBox<Integer> guestCount;
    private JTextField checkIn;
    private JTextField checkOut;

    public GuestPanel(HotelSystem hotelSystem) {
        this.hotelSystem = hotelSystem;
        setTitle("Hotel System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Guest Request Form Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel jpanel = new JPanel(new GridLayout(5, 2, 10, 10));
        JLabel nameLabel = new JLabel("Enter Full Name (e.g Rob Mallen)");
        guestName = new JTextField(50);
        JLabel typeLabel = new JLabel("Pick Room type");
        roomType = new JComboBox<>(new String[]{"Single $100", "Double $200", "Suite $400"});
        JLabel countLabel = new JLabel("How many guests?");
        guestCount = new JComboBox<>(new Integer[]{1,2,3,4});
        JLabel checkInLabel = new JLabel("Enter check in date (e.g 12/06/2001)");
        checkIn = new JTextField(20);
        JLabel checkOutLabel = new JLabel("Enter check out date (e.g 12/07/2001)");
        checkOut = new JTextField(20);
        JButton submit = new JButton("Check Price & Submit");
        
        jpanel.add(nameLabel);
        jpanel.add(guestName);
        jpanel.add(typeLabel);
        jpanel.add(roomType);
        jpanel.add(countLabel);
        jpanel.add(guestCount);
        jpanel.add(checkInLabel);
        jpanel.add(checkIn);
        jpanel.add(checkOutLabel);
        jpanel.add(checkOut);
        

        add(titleLabel, BorderLayout.NORTH);
        add(jpanel, BorderLayout.CENTER);
        add(submit , BorderLayout.SOUTH);
        this.setResizable(false);
        setVisible(true);
    }

}
