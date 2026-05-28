package hotelsystem;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author kahn
 */
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
        midPanel.add(createViewRequestsPanel(), "VIEW_REQUESTS");
        midPanel.add(createApproveRequestPanel(), "APPROVE_REQUESTS");
        midPanel.add(createDeleteRequestPanel(), "DELETE_REQUESTS");

        topDropDownPanel.add(adminCategoryChoice);
        topDropDownPanel.add(adminActionChoice);
        northPanel.add(titleLabel, BorderLayout.NORTH);
        northPanel.add(topDropDownPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(midPanel, BorderLayout.CENTER);
        this.setResizable(false);
        updateActionChoice();
        setVisible(true);


        adminCategoryChoice.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent e){
            updateActionChoice();
            }
        });

        adminActionChoice.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent e){
            updateField();
            }
        });


    }

}