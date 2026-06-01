/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));

        JPanel jpanel = new JPanel(new GridLayout(5, 2, 10, 10));
        jpanel.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));

        jpanel.setBackground(new Color(197, 215, 217));
        JLabel nameLabel = new JLabel("Enter Full Name (e.g Rob Mallen)");
        guestName = new JTextField(50);
        JLabel typeLabel = new JLabel("Pick Room type");
        roomType = new JComboBox<>(new String[]{"Single $100 (1 Max Guest)", "Double $200 (2 Max Guests)", "Suite $400 (4 Max Guests)"});
        JLabel countLabel = new JLabel("How many guests?");
        guestCount = new JComboBox<>(new Integer[]{1, 2, 3, 4});
        JLabel checkInLabel = new JLabel("Enter check in date (e.g 12/06/2001)");
        checkIn = new JTextField(20);
        JLabel checkOutLabel = new JLabel("Enter check out date (e.g 12/07/2001)");
        checkOut = new JTextField(20);
        JButton submit = new JButton("Check Price & Submit");

        submit.setPreferredSize(new Dimension(0, 45));
        submit.setOpaque(true);

        submit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                submit.setBackground(new Color(200, 200, 200));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                submit.setBackground((null));
            }
        });

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
        add(submit, BorderLayout.SOUTH);
        this.setResizable(false);
        setVisible(true);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guestInput();
            }
        });
    }

    private void guestInput() {
        // 1. Collect inputs
        String name = guestName.getText().trim();
        String checkInStr = checkIn.getText().trim();
        String checkOutStr = checkOut.getText().trim();
        int roomIndex = roomType.getSelectedIndex();
        int guests = (Integer) guestCount.getSelectedItem();
        int maxGuest = 0;

        // 2. Missing field validation
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your full name.", "Missing Field", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (name.length() > 75) {
            JOptionPane.showMessageDialog(this, "Sorry name cannot be larger than 75 characters");
            return;
        }
        if (checkInStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a check-in date.", "Missing Field", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (checkOutStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a check-out date.", "Missing Field", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Date format validation
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate inDate, outDate;
        try {
            inDate = LocalDate.parse(checkInStr, fmt);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Check-in date is not valid.\nPlease use dd/MM/yyyy (e.g. 12/06/2025).", "Invalid Date", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            outDate = LocalDate.parse(checkOutStr, fmt);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Check-out date is not valid.\nPlease use dd/MM/yyyy (e.g. 15/06/2025).", "Invalid Date", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4. Date order validation
        if (!outDate.isAfter(inDate)) {
            JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date.", "Invalid Date Range", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 5. Map combobox index to RoomType
        RoomType selectedRoom;
        switch (roomIndex) {
            case 0:
                selectedRoom = RoomType.SINGLE;
                break;
            case 1:
                selectedRoom = RoomType.DOUBLE;
                break;
            case 2:
                selectedRoom = RoomType.SUITE;
                break;
            default:
                JOptionPane.showMessageDialog(this, "Room type can't be selected");
                return;
        }

        //Added check to make sure guest count no over room capacity
        switch (selectedRoom) {
            case SINGLE:
                maxGuest = 1;
                break;
            case DOUBLE:
                maxGuest = 2;
                break;
            case SUITE:
                maxGuest = 4;
                break;
            default:
                break;
        }

        if (guests > maxGuest) {
            JOptionPane.showMessageDialog(this, selectedRoom + " over maximum room capacity of " + maxGuest + " guests");
            return;
        }

        // 6. Calculate price
        double price = hotelSystem.calculatePrice(checkInStr, checkOutStr, selectedRoom);

        // 7. Show summary and confirm
        String summary = "Name: " + name + "\nRoom: " + selectedRoom + "\nGuests: " + guests
                + "\nCheck-in: " + checkInStr + "\nCheck-out: " + checkOutStr
                + "\nEstimated Total: $" + String.format("%.2f", price)
                + "\n\nConfirm booking request?";

        int choice = JOptionPane.showConfirmDialog(this, summary, "Confirm Booking", JOptionPane.YES_NO_OPTION);

        // 8. Denied
        if (choice != JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Booking not confirmed. You can edit your details.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // 9. Confirmed - save to database
        try {
            hotelSystem.createBookingRequest(name, selectedRoom, guests, checkInStr, checkOutStr);
            JOptionPane.showMessageDialog(this, "Booking request submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
