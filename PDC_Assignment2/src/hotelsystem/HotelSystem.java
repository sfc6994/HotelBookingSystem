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
    public void updateRoomStatus(int roomNumber, RoomStatus status) throws SQLException{
        Room room = findRoom(roomNumber);
        if (room == null) {
            System.out.println("Error: Room " + roomNumber + " not found.");
            return;
        }
        roomDAO.updateRoomStatus(roomNumber, status);
    }

    //checks if roomNumber doesn't exist using findRoom, if it does prints a error message
    //otherwise adds a object to ROOMS table
    public void addRoom(int roomNumber, RoomType type, int capacity) throws SQLException{
        if (findRoom(roomNumber) != null) {
            System.out.println("Error: Room " + roomNumber + " already exists.");
            return;
        }
        roomDAO.addRoom(new Room(roomNumber,type, capacity));
    }

    //Loops through ROOMS database table checking the status and only returns OCCUPIED, AVAILABLE and MAINTENANCE rooms
    public ArrayList<Room> viewActiveRooms() throws SQLException{
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
    public ArrayList<Room> availableRooms() throws SQLException{
        ArrayList<Room> available = new ArrayList<>();
        for (Room r : roomDAO.getAllRooms()) {
            if (r.getStatus() == RoomStatus.AVAILABLE) {
                available.add(r);
            }
        }
        return available;
    }

    //Loops through ROOMS database table checking the status and only returns DECOMMISSIONED rooms
    public ArrayList<Room> decommissionedRooms() throws SQLException{
        ArrayList<Room> decommissioned = new ArrayList<>();
        for (Room r : roomDAO.getAllRooms()) {
            if (r.getStatus() == RoomStatus.DECOMMISSIONED) {
                decommissioned.add(r);
            }
        }
        return decommissioned;
    }

    //returns the full BOOKING table
    public ArrayList<Booking> viewBookings() throws SQLException{
        return bookingDAO.getAllBookings();
    }

    //loops through booking list and uses getBookingID to check if there are any id matches
    public Booking findBooking(int id) {
        for (Booking b : bookingList) {
            if (b.getBookingID() == id) {
                return b;
            }
        }
        return null;
    }

    //loops through booknig list and find the bookingId using findBooking
    //if not found returns error message 
    //if found removes it from bookingList and uses getRoomNumber to find room and change the status to AVAILABLE
    //Then saves the modified bookings and rooms
    public void cancelBooking(int id) {
        Booking booking = findBooking(id);
        if (booking == null) {
            System.out.println("Error: Booking " + id + " not found.");
            return;
        }
        bookingList.remove(booking);
        Room room = findRoom(booking.getRoomNumber());
        if (room != null) {
            room.setStatus(RoomStatus.AVAILABLE);
        }
        fileHandler.saveBookings(bookingList);
        fileHandler.saveRooms(roomList);
    }

    //uses findBooking to find the booking
    //if not found returns error message
    //if found uses getRoomNumber to find the room and sets the status to OCCUPIED
    //saves the modifed booking and room list
    public void checkIn(int id) {
        Booking booking = findBooking(id);
        if (booking == null) {
            System.out.println("Error: Booking " + id + " not found.");
            return;
        }
        Room room = findRoom(booking.getRoomNumber());
        if (room != null) {
            room.setStatus(RoomStatus.OCCUPIED);
        }
        fileHandler.saveBookings(bookingList);
        fileHandler.saveRooms(roomList);
    }

    //uses findBooking to find the booking
    //if not found returns error message
    //if found uses getRoomNumber to find the room and sets the status to AVAILABLE
    //saves the modifed booking and room list
    public void checkOut(int id) {
        Booking booking = findBooking(id);
        if (booking == null) {
            System.out.println("Error: Booking " + id + " not found.");
            return;
        }
        Room room = findRoom(booking.getRoomNumber());
        if (room != null) {
            room.setStatus(RoomStatus.AVAILABLE);
        }
        fileHandler.saveBookings(bookingList);
        fileHandler.saveRooms(roomList);
    }

    //use findRoom to check if room exists, if not print error and return
    //uses getStatus to check is room status isn't available, if it isnt prints error and returns
    //otherwise it checks the dates enerted to make sure they are correct
    //then calls calcualtePrice to work out the price
    //creates the new booking object and uses idCounter to give it unique ID and increments the counter
    //sets the room to occupied and adds the booking it to the booking list
    //then saves the updated roomList, bookinglist and idcounter via fileHandler
    public void createBooking(String name, int roomNumber, String checkIn, String checkOut) {
        Room room = findRoom(roomNumber);
        if (room == null) {
            System.out.println("Error: Room " + roomNumber + " not found.");
            return;
        }
        if (room.getStatus() != RoomStatus.AVAILABLE) {
            System.out.println("Error: Room " + roomNumber + " is not available.");
            return;
        }

        //Date logic to make sure dates are actually possible (checkout must be after check in)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate inDate = LocalDate.parse(checkIn, formatter);
        LocalDate outDate = LocalDate.parse(checkOut, formatter);
        if (outDate.isBefore(inDate) || outDate.isEqual(inDate)) {
            System.out.println("Error: Check out date must be after Check in date");
            return;
        }

        double totalPrice = calculatePrice(checkIn, checkOut, room.getRoomType());
        Booking b = new Booking(idCounter, name, roomNumber, checkIn, checkOut, totalPrice);
        idCounter++;
        room.setStatus(RoomStatus.OCCUPIED);
        bookingList.add(b);
        fileHandler.saveBookings(bookingList);
        fileHandler.saveRooms(roomList);
        fileHandler.saveIdCounter(idCounter);
    }

    //Returns the whole requestList
    public ArrayList<BookingRequest> viewRequests() {
        return requestList;
    }

    //calcualtes the price using the calcualtePrice method to show to guest
    //create a new booking request object with a unique ID, and a the price
    //increments the counter and saves it to the fileHandler
    //Also adds the request to the list and then saves the requestList to the fileHandler
    public void createBookingRequest(String name, RoomType type, int count, String in, String out) {
        double price = calculatePrice(in, out, type);
        BookingRequest request = new BookingRequest(idCounter, name, type, count, in, out, price);
        idCounter++;
        requestList.add(request);
        fileHandler.saveRequests(requestList);
        fileHandler.saveIdCounter(idCounter);
    }

    //Null booking request object created
    //Loops through the request list using a temporary value br
    //If the ID passed through is found by br.getRequestID stores the refercne to that bookingrequest in request
    //if its not found error message is returned
    //Null room object created
    //Loops roomList to check if an AVAILABLE room exists and that its of the right type
    //If not found error message and returns
    //If all checks match creates the new booking object with the info parsed into the parameters and a unique counter is added
    //Increments the counter, sets the status from AVAILABLE to OCCUPIED
    //Adds it to booking list, removes it from the requests list and save each of the lists and the updated idCounter
    public void approveRequest(int id) {
        BookingRequest request = null;
        for (BookingRequest br : requestList) {
            if (br.getRequestID() == id) {
                request = br;
                break;
            }
        }
        if (request == null) {
            System.out.println("Error: Request " + id + " not found.");
            return;
        }
        Room room = null;
        for (Room r : roomList) {
            if (r.getStatus() == RoomStatus.AVAILABLE && r.getRoomType() == request.getRoomType()) {
                room = r;
                break;
            }
        }
        if (room == null) {
            System.out.println("Error: No available room of type " + request.getRoomType() + " found.");
            return;
        }

        Booking booking = new Booking(idCounter, request.getGuestName(), room.getRoomNumber(),
                request.getCheckIn(), request.getCheckOut(), request.getTotalPrice());
        idCounter++;
        room.setStatus(RoomStatus.OCCUPIED);
        bookingList.add(booking);
        requestList.remove(request);
        fileHandler.saveRooms(roomList);
        fileHandler.saveBookings(bookingList);
        fileHandler.saveRequests(requestList);
        fileHandler.saveIdCounter(idCounter);
    }

    //loops the request list and using index based loop to remove while iterating uses getRequestID to compare ID being searched
    //If doesn't find an ID match returns an error message 
    //If it does find it it removes the request from the list and then saves the updated list
    public void deleteRequest(int id) {
        for (int i = 0; i < requestList.size(); i++) {
            if (requestList.get(i).getRequestID() == id) {
                requestList.remove(i);
                fileHandler.saveRequests(requestList);
                return;
            }
        }
        System.out.println("Error: Request " + id + " not found.");
    }

}
