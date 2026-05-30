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
        /*
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
}
