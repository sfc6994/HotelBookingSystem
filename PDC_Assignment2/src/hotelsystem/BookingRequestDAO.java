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
public class BookingRequestDAO {
    
    private Connection conn;
    
    //Constructor for the active connection to be passed to for methods
    public BookingRequestDAO(Connection conn){
        this.conn = conn;
    }
    
    //Inserts the booking request inot the REQUESTS table in the database with the following values
    public void createBookingRequest(BookingRequest request)throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO REQUESTS (REQUESTID, GUESTNAME, ROOMTYPE, GUESTCOUNT,"
                + "CHECKIN, CHECKOUT, TOTALPRICE) VALUES(?, ?, ?, ?, ?, ?, ?)");
        
        pstmt.setInt(1, request.getRequestID());
        pstmt.setString(2, request.getGuestName());
        pstmt.setString(3, request.getRoomType().name());
        pstmt.setInt(4, request.getGuestCount());
        pstmt.setString(5, request.getCheckIn());
        pstmt.setString(6, request.getCheckOut());
        pstmt.setDouble(7, request.getTotalPrice());
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    
}
