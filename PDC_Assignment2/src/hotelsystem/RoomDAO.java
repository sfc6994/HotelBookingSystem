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
public class RoomDAO {
    
    private Connection conn;
    
    //Constructor for the active connection to be passed to for methods
    public RoomDAO(Connection conn){
        this.conn = conn;
    }
    
    
    //Inserts new room into the ROOMS table in the database with following values
    public void addRoom(Room room) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ROOMS(ROOMNUMBER, ROOMTYPE, CAPACITY, STATUS) VALUES (?, ?, ?, ?)");
        
        pstmt.setInt(1, room.getRoomNumber());
        pstmt.setString(2, room.getRoomType().name());
        pstmt.setInt(3, room.getCapacity());
        pstmt.setString(4, room.getStatus().name());
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    //Searches the SQL table ROOMS using room number and returns the Room object, returns null if not found
    public Room findRoom(int roomNumber) throws SQLException{
    
        return null;
    }
    
    //Uses room number to find room in ROOMS table and then updates the status of the Room inside the database
    public void updateRoomStatus(int roomNumber, RoomStatus status) throws SQLException{
    
    }
    
    //Returns all Rooms from ROOMS table and then returns them into the ArrayList as room objects
    public ArrayList<Room> getAllRooms() throws SQLException{
   
        return null;
    }
    
}
