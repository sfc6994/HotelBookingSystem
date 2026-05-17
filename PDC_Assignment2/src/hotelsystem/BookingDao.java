/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelsystem;

import java.sql.*;
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
    
    
}
