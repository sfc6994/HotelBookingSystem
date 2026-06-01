/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelsystem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author Cameron
 */
public class HotelSystem {

    private Connection conn;
    private RoomDAO roomDAO;
    private BookingDAO bookingDAO;
    private BookingRequestDAO bookingRequestDAO;

    private String adminPassword;
    private int idCounter;

    public HotelSystem() throws SQLException {
        DatabaseManager dbm = DatabaseManager.getDataBaseManagerInstance();
        this.conn = dbm.getConnection();
        roomDAO = new RoomDAO(dbm.getConnection());
        bookingDAO = new BookingDAO(dbm.getConnection());
        bookingRequestDAO = new BookingRequestDAO(dbm.getConnection());

        //Gets the max booking id and max request id from the BOOKINGS and REQUESTS table and stores into variable
        int bookingNumID = 0;
        int requestNumID = 0;
        ResultSet rs1 = conn.createStatement().executeQuery("SELECT MAX (BOOKINGID) FROM BOOKINGS");
        ResultSet rs2 = conn.createStatement().executeQuery("SELECT MAX (REQUESTID) FROM REQUESTS");
        if (rs1.next()) {
            bookingNumID = rs1.getInt(1);
        }
        if (rs2.next()) {
            requestNumID = rs2.getInt(1);
        }

        //Sets the idCounter to +1 of the highest existing id between the REQUEST and BOOKING tables to prevent duplciate id
        if (bookingNumID > requestNumID) {
            this.idCounter = bookingNumID + 1;
        } else {
            this.idCounter = requestNumID + 1;
        }

        //password for admin
        this.adminPassword = "Admin123";

        //create rooms on startup if the ROOMS database is empty
        if (roomDAO.getAllRooms().isEmpty()) {
            roomDAO.addRoom(new Room(101, RoomType.SINGLE, 1));
            roomDAO.addRoom(new Room(102, RoomType.DOUBLE, 2));
            roomDAO.addRoom(new Room(103, RoomType.SUITE, 4));
            roomDAO.addRoom(new Room(104, RoomType.SINGLE, 1));
            roomDAO.addRoom(new Room(105, RoomType.DOUBLE, 2));
            roomDAO.addRoom(new Room(106, RoomType.SUITE, 4));
        }

    }

    //Check if password is correct. Boolean value to check this
    public boolean verifyPassword(String password) {
        return this.adminPassword.equals(password);
    }

    public int getIdCounter() {
        return idCounter;
    }

    public void setIdCounter(int count) {
        this.idCounter = count;
    }

    //Sets the date time format to day/month/year
    //Parses in the check in and check out day with the formatter as its structure (dd/mm/yyyy)
    //stores the difference between the dates via the unit Days a long called duartion
    //hard coded nightly prices for each room type using switch case and returns duration * price
    public double calculatePrice(String checkIn, String checkOut, RoomType type) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate inDate = LocalDate.parse(checkIn, formatter);
        LocalDate outDate = LocalDate.parse(checkOut, formatter);
        long duration = ChronoUnit.DAYS.between(inDate, outDate);

        double price;
        switch (type) {
            case SINGLE:
                price = 100.00;
                break;
            case DOUBLE:
                price = 200.00;
                break;
            case SUITE:
                price = 400.00;
                break;
            default:
                price = 0.00;
                break;
        }
        return duration * price;
    }

    //makes getRoomNumber search for roomNumber entered inside the ROOMS database and returns the object if found, if not returns null
    public Room findRoom(int roomNumber) throws SQLException {
        return roomDAO.findRoom(roomNumber);
    }

    //Simple code to allow admin to change room status whenever needed manually
    public void updateRoomStatus(int roomNumber, RoomStatus status) throws SQLException {
        Room room = findRoom(roomNumber);
        if (room == null) {
            throw new IllegalArgumentException("Error: Room " + roomNumber + " not found.");
        }
        roomDAO.updateRoomStatus(roomNumber, status);
    }

    //checks if roomNumber doesn't exist using findRoom, if it does throws illegalargumentexception
    //otherwise adds a object to ROOMS table
    public void addRoom(int roomNumber, RoomType type, int capacity) throws SQLException {
        if (findRoom(roomNumber) != null) {
            throw new IllegalArgumentException("Error: Room " + roomNumber + " already exists.");
        }
        roomDAO.addRoom(new Room(roomNumber, type, capacity));
    }

    //Loops through ROOMS database table checking the status and only returns OCCUPIED, AVAILABLE and MAINTENANCE rooms
    public ArrayList<Room> viewActiveRooms() throws SQLException {
        ArrayList<Room> active = new ArrayList<>();
        for (Room r : roomDAO.getAllRooms()) {
            if (r.getStatus() == RoomStatus.OCCUPIED
                    || r.getStatus() == RoomStatus.AVAILABLE
                    || r.getStatus() == RoomStatus.MAINTENANCE) {
                active.add(r);
            }
        }
        return active;
    }

    //Loops through ROOMS database table checking the status and only returns only AVAILABLE rooms
    public ArrayList<Room> availableRooms() throws SQLException {
        ArrayList<Room> available = new ArrayList<>();
        for (Room r : roomDAO.getAllRooms()) {
            if (r.getStatus() == RoomStatus.AVAILABLE) {
                available.add(r);
            }
        }
        return available;
    }

    //Loops through ROOMS database table checking the status and only returns DECOMMISSIONED rooms
    public ArrayList<Room> decommissionedRooms() throws SQLException {
        ArrayList<Room> decommissioned = new ArrayList<>();
        for (Room r : roomDAO.getAllRooms()) {
            if (r.getStatus() == RoomStatus.DECOMMISSIONED) {
                decommissioned.add(r);
            }
        }
        return decommissioned;
    }

    //returns the full BOOKING table
    public ArrayList<Booking> viewBookings() throws SQLException {
        return bookingDAO.getAllBookings();
    }

    //returns the current bookings from the BOOKING table
    public ArrayList<Booking> currentBookings() throws SQLException {
        return bookingDAO.getCurrentBookings();
    }

    //loops through BOOKING table and uses getBookingID to check if there are any id matches
    public Booking findBooking(int id) throws SQLException {
        return bookingDAO.findBooking(id);
    }

    //Finds the booking by its ID in BOOKINGS table
    //if not found throws illegalargumentexception 
    //if found deletes from BOOKINGS table and then updates the room status back to AVAILABLE in ROOMS table
    public void cancelBooking(int id) throws SQLException {
        Booking booking = findBooking(id);
        if (booking == null) {
            throw new IllegalArgumentException("Error: Booking " + id + " not found.");
        }
        bookingDAO.cancelBooking(id);
        roomDAO.updateRoomStatus(booking.getRoomNumber(), RoomStatus.AVAILABLE);

    }

    //Finds the booking by its ID in BOOKINGS table
    //if not found throws illegalargumentexception
    //if found uses updateRoomStatus to change the booking at the enerted room number to occupied
    public void checkIn(int id) throws SQLException {
        Booking booking = findBooking(id);
        if (booking == null) {
            throw new IllegalArgumentException("Error: Booking " + id + " not found.");
        }
        roomDAO.updateRoomStatus(booking.getRoomNumber(), RoomStatus.OCCUPIED);
    }

    //uses findBooking to find the booking
    //if not found throws illegalargumentexception
    //if found uses updateRoomStatus to change the booking at the enerted room number to available
    public void checkOut(int id) throws SQLException {
        Booking booking = findBooking(id);
        if (booking == null) {
            throw new IllegalArgumentException("Error: Booking " + id + " not found.");
        }
        roomDAO.updateRoomStatus(booking.getRoomNumber(), RoomStatus.AVAILABLE);
    }

    //use findRoom to check if room exists, if not throws illegalargumentexception
    //uses getStatus to check is room status isn't available, if it isnt throws illegalargumentexception
    //otherwise it checks the dates enerted to make sure they are correct
    //then calls calculatePrice to work out the price
    //creates the new booking object and uses idCounter to give it unique ID and increments the counter
    //sets the room to occupied in the ROOMS table and adds the booking into the BOOKINGS table
    public void createBooking(String name, int roomNumber, String checkIn, String checkOut) throws SQLException {
        Room room = findRoom(roomNumber);
        if (room == null) {
            throw new IllegalArgumentException("Error: Room " + roomNumber + " not found.");
        }
        if (room.getStatus() != RoomStatus.AVAILABLE) {
            throw new IllegalArgumentException("Error: Room " + roomNumber + " is not available.");
        }

        //Date logic to make sure dates are actually possible (checkout must be after check in)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate inDate = LocalDate.parse(checkIn, formatter);
        LocalDate outDate = LocalDate.parse(checkOut, formatter);
        if (outDate.isBefore(inDate) || outDate.isEqual(inDate)) {
            throw new IllegalArgumentException("Error: Check out date must be after Check in date");
        }

        double totalPrice = calculatePrice(checkIn, checkOut, room.getRoomType());
        Booking b = new Booking(idCounter, name, roomNumber, checkIn, checkOut, totalPrice);
        idCounter++;

        bookingDAO.addBooking(b);
        roomDAO.updateRoomStatus(roomNumber, RoomStatus.OCCUPIED);
    }

    //Returns the whole REQUEST table
    public ArrayList<BookingRequest> viewRequests() throws SQLException {
        return bookingRequestDAO.getAllRequests();
    }

    //calcualtes the price using the calcualtePrice method to show to guest
    //create a new booking request object with a unique ID, and the price
    //increments the counter and adds the booking request to the REQUESTS table
    public void createBookingRequest(String name, RoomType type, int count, String in, String out) throws SQLException {
        double price = calculatePrice(in, out, type);
        BookingRequest request = new BookingRequest(idCounter, name, type, count, in, out, price);
        idCounter++;
        bookingRequestDAO.addBookingRequest(request);
    }

    //Finds the booking request in the REQUESTS table using the id
    //if its not found throws illegalargumentexception
    //Loops the ROOM table to check if an AVAILABLE room exists and that its of the right type
    //If not found throws illegalargumentexception
    //If found sets status to OCCUPIED
    //Creates a new booknig with unique id, adds it to the BOOKINGS table, deletes the request from the REQUESTS table
    public void approveRequest(int id) throws SQLException {

        BookingRequest request = bookingRequestDAO.findRequest(id);

        if (request == null) {
            throw new IllegalArgumentException("Error: Request " + id + " not found.");
        }
        Room room = null;
        for (Room r : roomDAO.getAllRooms()) {
            if (r.getStatus() == RoomStatus.AVAILABLE && r.getRoomType() == request.getRoomType()) {
                room = r;
                break;
            }
        }
        if (room == null) {
            throw new IllegalArgumentException("Error: No available room of type " + request.getRoomType() + " found.");
        }

        Booking booking = new Booking(idCounter, request.getGuestName(), room.getRoomNumber(),
                request.getCheckIn(), request.getCheckOut(), request.getTotalPrice());
        idCounter++;
        bookingDAO.addBooking(booking);
        bookingRequestDAO.deleteRequest(id);
        roomDAO.updateRoomStatus(room.getRoomNumber(), RoomStatus.OCCUPIED);
    }

    //Finds the booking request by its id
    //if its not found throws illegalargumentexception
    //if found deletes the booking request from the REQUESTS table
    public void deleteRequest(int id) throws SQLException {
        BookingRequest request = bookingRequestDAO.findRequest(id);
        if (request == null) {
            throw new IllegalArgumentException("Error: Request " + id + " not found.");
        }
        bookingRequestDAO.deleteRequest(id);
    }

    //Finds booking request by its id, if unfound returns null
    public BookingRequest findRequest(int id) throws SQLException {
        return bookingRequestDAO.findRequest(id);
    }

}
