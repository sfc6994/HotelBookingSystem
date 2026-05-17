/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelsystem;

import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Cameron
 */
public class BookingDao {
    
    private Connection conn;
    
    //Constructor for the active connection to be passed to for methods
    public BookingDao(Connection conn){
        this.conn = conn;
    }
    
    //Inserts the new booking into the BOOKINGS table in the database with the following values
    public void addBooking(Booking booking) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO BOOKINGS(BOOKINGID, GUESTNAME, ROOMNUMBER, CHECKIN, "
                + "CHECKOUT, TOTALPRICE) VALUES(?, ?, ?, ?, ?, ?)");
        
        pstmt.setInt(1, booking.getBookingID());
        pstmt.setString(2, booking.getGuestName());
        pstmt.setInt(3, booking.getRoomNumber());
        pstmt.setString(4, booking.getCheckIn());
        pstmt.setString(5, booking.getCheckOut());
        pstmt.setDouble(6, booking.getTotalPrice());
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    //Uses bookingID to find the booking in BOOKING table and then returns the Booking object, return null if not found
    public Booking findBooking(int bookingID) throws SQLException{
    
        return null;
    }
    
    //Removes the cancelled booking from the BOOKING table by using the bookingID
    public void cancelBooking(int bookingID) throws SQLException{
    
    }
    
    //Returns all Bookings from BOOKINGS table and stores them inside ArrayList as booking objects
    public ArrayList<Booking> getAllBookings() throws SQLException{
    
        return null;
    }
    
}
