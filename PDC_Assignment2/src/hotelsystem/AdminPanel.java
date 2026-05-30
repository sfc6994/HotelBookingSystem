package hotelsystem;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.*;



/**
 *
 
@author kahn*/

public class AdminPanel extends JFrame {

    private HotelSystem hotelSystem;

    private JComboBox<String> adminCategoryChoice;
    private JComboBox<String> adminActionChoice;
    private CardLayout cardLayout;
    private JPanel midPanel;

    public AdminPanel(HotelSystem hotelSystem) {
        this.hotelSystem = hotelSystem;
        setTitle("Hotel System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Admin Hotel Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel topDropDownPanel = new JPanel(new FlowLayout());
        adminCategoryChoice = new JComboBox<>(new String[]{"Room Management", "Booking Management", "Booking Request Management"});
        adminActionChoice = new JComboBox<>();
        JPanel northPanel = new JPanel(new BorderLayout());
        cardLayout = new CardLayout();
        
        midPanel = new JPanel(cardLayout);
        midPanel.add(createFindRoomPanel(), "FIND_ROOM");
        midPanel.add(createUpdateRoomStatusPanel(), "UPDATE_STATUS_ROOM");
        midPanel.add(createAddRoomPanel(), "ADD_ROOM");
        midPanel.add(createViewActiveRoomsPanel(), "ACTIVE_ROOMS");
        midPanel.add(createAvailableRoomsPanel(), "AVAILABLE_ROOMS");
        midPanel.add(createDecommissionedRoomsPanel(), "DECOMMISSIONED_ROOMS");
        midPanel.add(createViewBookingsPanel(), "VIEW_BOOKINGS");
        midPanel.add(createFindBookingPanel(), "FIND_BOOKING");
        midPanel.add(createCancelBookingPanel(), "CANCEL_BOOKING");
        midPanel.add(createCheckOutPanel(), "CHECKOUT_BOOKING");
        midPanel.add(createBookingPanel(), "CREATE_BOOKING");
        /*
        midPanel.add(createViewRequestsPanel(), "VIEW_REQUESTS");
        midPanel.add(createApproveRequestPanel(), "APPROVE_REQUEST");
        midPanel.add(createDeleteRequestPanel(), "DELETE_REQUEST");
        */
        
        topDropDownPanel.add(adminCategoryChoice);
        topDropDownPanel.add(adminActionChoice);
        northPanel.add(titleLabel, BorderLayout.NORTH);
        northPanel.add(topDropDownPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(midPanel, BorderLayout.CENTER);
        this.setResizable(false);
        updateActionChoice();
        setVisible(true);

        adminCategoryChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateActionChoice();
            }
        });

        adminActionChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateField();
            }
        });

    }
    
    private void updateActionChoice() {
        adminActionChoice.removeAllItems();
        int categoryChoice = adminCategoryChoice.getSelectedIndex();

        if (categoryChoice == 0) {
            adminActionChoice.addItem("Find Room");
            adminActionChoice.addItem("Update Room Status");
            adminActionChoice.addItem("Add Room");
            adminActionChoice.addItem("View Active Rooms");
            adminActionChoice.addItem("View Available Rooms");
            adminActionChoice.addItem("View Decommissioned Rooms");
        } else if (categoryChoice == 1) {
            adminActionChoice.addItem("View Bookings");
            adminActionChoice.addItem("Find Booking");
            adminActionChoice.addItem("Cancel Booking");
            adminActionChoice.addItem("Check Out");
            adminActionChoice.addItem("Create Booking");
        } else if (categoryChoice == 2) {
            adminActionChoice.addItem("View Requests");
            adminActionChoice.addItem("Approve Request");
            adminActionChoice.addItem("Delete Request");
        }
        updateField();
    }
    
    private void updateField() {
        int categoryChoice = adminCategoryChoice.getSelectedIndex();
        int actionChoice = adminActionChoice.getSelectedIndex();

        if (categoryChoice == 0) {
            if (actionChoice == 0) {
                cardLayout.show(midPanel, "FIND_ROOM");
            } else if (actionChoice == 1) {
                cardLayout.show(midPanel, "UPDATE_STATUS_ROOM");
            } else if (actionChoice == 2) {
                cardLayout.show(midPanel, "ADD_ROOM");
            } else if (actionChoice == 3) {
                cardLayout.show(midPanel, "ACTIVE_ROOMS");
            } else if (actionChoice == 4) {
                cardLayout.show(midPanel, "AVAILABLE_ROOMS");
            } else if (actionChoice == 5) {
                cardLayout.show(midPanel, "DECOMMISSIONED_ROOMS");
            }
        } else if (categoryChoice == 1) {
            if (actionChoice == 0) {
                cardLayout.show(midPanel, "VIEW_BOOKINGS");
            } else if (actionChoice == 1) {
                cardLayout.show(midPanel, "FIND_BOOKING");
            } else if (actionChoice == 2) {
                cardLayout.show(midPanel, "CANCEL_BOOKING");
            } else if (actionChoice == 3) {
                cardLayout.show(midPanel, "CHECKOUT_BOOKING");
            } else if (actionChoice == 4) {
                cardLayout.show(midPanel, "CREATE_BOOKING");
            }

        } else if (categoryChoice == 2) {
            if (actionChoice == 0) {
                cardLayout.show(midPanel, "VIEW_REQUESTS");
            } else if (actionChoice == 1) {
                cardLayout.show(midPanel, "APPROVE_REQUEST");
            } else if (actionChoice == 2) {
                cardLayout.show(midPanel, "DELETE_REQUEST");
            }
        }
    }
    
    private JPanel createFindRoomPanel() {
    JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
    JLabel roomLabel = new JLabel("Enter Room Number:");
    JTextField roomField = new JTextField();
    JButton btn = new JButton("Find Room");
    panel.add(roomLabel);
    panel.add(roomField);
    panel.add(new JLabel());
    panel.add(btn);
    btn.addActionListener(e -> {
        String roomStr = roomField.getText().trim();
        if (roomStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a room number.", "Missing Field", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int roomNum = Integer.parseInt(roomStr);
            Room room = hotelSystem.findRoom(roomNum);
            if (room == null) {
                JOptionPane.showMessageDialog(this, "Room " + roomNum + " not found.", "Not Found", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, room.toString(), "Room Found", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Room number must be a valid integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });
    return panel;
}

private JPanel createUpdateRoomStatusPanel() {
    JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
    JLabel roomLabel = new JLabel("Enter Room Number:");
    JTextField roomField = new JTextField();
    JLabel statusLabel = new JLabel("Select New Status:");
    JComboBox<RoomStatus> statusBox = new JComboBox<>(RoomStatus.values());
    JButton btn = new JButton("Update Status");
    panel.add(roomLabel);
    panel.add(roomField);
    panel.add(statusLabel);
    panel.add(statusBox);
    panel.add(new JLabel());
    panel.add(btn);
    btn.addActionListener(e -> {
        String roomStr = roomField.getText().trim();
        if (roomStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a room number.", "Missing Field", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int roomNum = Integer.parseInt(roomStr);
            RoomStatus status = (RoomStatus) statusBox.getSelectedItem();
            hotelSystem.updateRoomStatus(roomNum, status);
            JOptionPane.showMessageDialog(this, "Room " + roomNum + " status updated to " + status + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Room number must be a valid integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException | SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });
    return panel;
}

private JPanel createAddRoomPanel() {
    JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
    JLabel roomLabel = new JLabel("Enter Room Number:");
    JTextField roomField = new JTextField();
    JLabel typeLabel = new JLabel("Select Room Type:");
    JComboBox<RoomType> typeBox = new JComboBox<>(RoomType.values());
    JLabel capLabel = new JLabel("Enter Capacity:");
    JTextField capField = new JTextField();
    JButton btn = new JButton("Add Room");
    panel.add(roomLabel);
    panel.add(roomField);
    panel.add(typeLabel);
    panel.add(typeBox);
    panel.add(capLabel);
    panel.add(capField);
    panel.add(new JLabel());
    panel.add(btn);
    btn.addActionListener(e -> {
        String roomStr = roomField.getText().trim();
        String capStr = capField.getText().trim();
        
        if (roomStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in the room number.", "Missing Field", JOptionPane.WARNING_MESSAGE);
            return;
        }   
        
        if (capStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in the capacity.", "Missing Field", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int roomNum;
        int capacity;
        try {
            
            roomNum = Integer.parseInt(roomStr);
            capacity = Integer.parseInt(capStr);
            
            if (roomNum > 10000) {
                JOptionPane.showMessageDialog(this, "Room number must be lower than 10,000", "Invalid input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (roomNum <= 0) {
                JOptionPane.showMessageDialog(this, "Room number must be greater than 0", "Invalid input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                roomNum = Integer.parseInt(capStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Room number must be a valid number", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (capacity <= 0) {
                JOptionPane.showMessageDialog(this, "Capacity must be greater than 0", "Invalid input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                capacity = Integer.parseInt(capStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Capacity must be a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
           
            RoomType type = (RoomType) typeBox.getSelectedItem();
            hotelSystem.addRoom(roomNum, type, capacity);
            JOptionPane.showMessageDialog(this, "Room " + roomNum + " added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }   catch (IllegalArgumentException | SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } 
    });
    return panel;
}

private JPanel createViewActiveRoomsPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    JScrollPane scroll = new JScrollPane(textArea);
    JButton btn = new JButton("Refresh");
    panel.add(scroll, BorderLayout.CENTER);
    panel.add(btn, BorderLayout.SOUTH);
    btn.addActionListener(e -> {
        try {
            ArrayList<Room> rooms = hotelSystem.viewActiveRooms();
            textArea.setText("");
            if (rooms.isEmpty()) {
                textArea.setText("No active rooms found.");
            } else {
                for (Room r : rooms) textArea.append(r.toString() + "\n");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });
    btn.doClick();
    return panel;
}

    private JPanel createAvailableRoomsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        JButton btn = new JButton("Refresh");
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btn, BorderLayout.SOUTH);
        btn.addActionListener(e -> {
            try {
                ArrayList<Room> rooms = hotelSystem.availableRooms();
                textArea.setText("");
                if (rooms.isEmpty()) {
                    textArea.setText("No available rooms found.");
                } else {
                    for (Room r : rooms) textArea.append(r.toString() + "\n");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btn.doClick();
        return panel;
    }

    private JPanel createDecommissionedRoomsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        JButton btn = new JButton("Refresh");
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btn, BorderLayout.SOUTH);
        btn.addActionListener(e -> {
            try {
                ArrayList<Room> rooms = hotelSystem.decommissionedRooms();
                textArea.setText("");
                if (rooms.isEmpty()) {
                    textArea.setText("No decommissioned rooms found.");
                } else {
                    for (Room r : rooms) textArea.append(r.toString() + "\n");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btn.doClick();
        return panel;
    }
    
    // ---------------- Bookings 
    
    private JPanel createFindBookingPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel idLabel = new JLabel("Enter Booking ID:");
        JTextField idField = new JTextField();
        JButton btn = new JButton("Find Booking");
        panel.add(idLabel);
        panel.add(idField);
        panel.add(new JLabel());
        panel.add(btn);
        btn.addActionListener(e -> {
            String idStr = idField.getText().trim();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a booking ID.", "Missing Field", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Booking ID must be a valid integer.", "Please enter valid input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                Booking booking = hotelSystem.findBooking(id);
                if (booking == null) {
                    JOptionPane.showMessageDialog(this, "Booking " + id + " not found.", "Not Found", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, booking.toString(), "Booking Found", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private JPanel createCancelBookingPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel idLabel = new JLabel("Enter Booking ID to Cancel:");
        JTextField idField = new JTextField();
        JButton btn = new JButton("Cancel Booking");
        panel.add(idLabel);
        panel.add(idField);
        panel.add(new JLabel());
        panel.add(btn);
        btn.addActionListener(e -> {
            String idStr = idField.getText().trim();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a booking ID.", "Missing Field", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Booking ID must be a valid integer.", "Please enter valid input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                hotelSystem.cancelBooking(id);
                JOptionPane.showMessageDialog(this, "Booking " + id + " cancelled successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException | SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private JPanel createCheckOutPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel idLabel = new JLabel("Enter Booking ID to Check Out:");
        JTextField idField = new JTextField();
        JButton btn = new JButton("Check Out");
        panel.add(idLabel);
        panel.add(idField);
        panel.add(new JLabel());
        panel.add(btn);
        btn.addActionListener(e -> {
            String idStr = idField.getText().trim();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a booking ID.", "Missing Field", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Booking ID must be a valid number.", "Please enter valid input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                hotelSystem.checkOut(id);
                JOptionPane.showMessageDialog(this, "Booking " + id + " checked out successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException | SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private JPanel createBookingPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        JLabel nameLabel = new JLabel("Guest Name:");
        JTextField nameField = new JTextField();
        JLabel roomLabel = new JLabel("Room Number:");
        JTextField roomField = new JTextField();
        JLabel inLabel = new JLabel("Check In (dd/MM/yyyy):");
        JTextField inField = new JTextField();
        JLabel outLabel = new JLabel("Check Out (dd/MM/yyyy):");
        JTextField outField = new JTextField();
        JButton btn = new JButton("Create Booking");
        panel.add(nameLabel); panel.add(nameField);
        panel.add(roomLabel); panel.add(roomField);
        panel.add(inLabel); panel.add(inField);
        panel.add(outLabel); panel.add(outField);
        panel.add(new JLabel()); panel.add(btn);
        btn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String roomStr = roomField.getText().trim();
            String checkIn = inField.getText().trim();
            String checkOut = outField.getText().trim();
            
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in the name field.", "Missing Field", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (name.length() > 75) {
                JOptionPane.showMessageDialog(this, "Guest name cannot exceed 75 characters.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (roomStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in the room number field.", "Missing Field", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (checkIn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in the check in field.", "Missing Field", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (checkOut.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in the check out field.", "Missing Field", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int roomNum;
            
            try {
                roomNum = Integer.parseInt(roomStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Room number must be a valid integer.", "Please enter valid input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (roomNum <= 0) {
                JOptionPane.showMessageDialog(this, "Room number must be greater than 0.", "Please enter valid input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            try {
                LocalDate.parse(checkIn, fmt);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Check-in date is not valid.\nPlease use dd/MM/yyyy (e.g. 12/06/2025).", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                LocalDate.parse(checkOut, fmt);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Check-out date is not valid.\nPlease use dd/MM/yyyy (e.g. 15/06/2025).", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                hotelSystem.createBooking(name, roomNum, checkIn, checkOut);
                JOptionPane.showMessageDialog(this, "Booking created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException | SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }
        private JPanel createViewBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        JButton btn = new JButton("Refresh");
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btn, BorderLayout.SOUTH);
        btn.addActionListener(e -> {
            try {
                ArrayList<Booking> bookings = hotelSystem.viewBookings();
                textArea.setText("");
                if (bookings.isEmpty()) {
                    textArea.setText("No bookings found.");
                } else {
                    for (Booking b : bookings) textArea.append(b.toString() + "\n");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btn.doClick();
        return panel;
    }
}
