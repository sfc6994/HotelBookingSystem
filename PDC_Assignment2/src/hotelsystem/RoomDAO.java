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
    public RoomDAO(Connection conn) {
        this.conn = conn;
    }

    //Inserts new room into the ROOMS table in the database with following values
    public void addRoom(Room room) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ROOMS(ROOMNUMBER, ROOMTYPE, CAPACITY, STATUS) VALUES (?, ?, ?, ?)");

        pstmt.setInt(1, room.getRoomNumber());
        pstmt.setString(2, room.getRoomType().name());
        pstmt.setInt(3, room.getCapacity());
        pstmt.setString(4, room.getStatus().name());
        pstmt.executeUpdate();
        pstmt.close();
    }

    //Searches the SQL table ROOMS using room number and returns the Room object, returns null if not found
    public Room findRoom(int roomNumber) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ROOMS WHERE ROOMNUMBER = ?");

        pstmt.setInt(1, roomNumber);
        ResultSet rs = pstmt.executeQuery();

        Room room = null;
        if (rs.next()) {
            room = new Room(
                    rs.getInt("ROOMNUMBER"),
                    RoomType.valueOf(rs.getString("ROOMTYPE")),
                    rs.getInt("CAPACITY"));
            room.setStatus(RoomStatus.valueOf(rs.getString("STATUS")));
        }

        rs.close();
        pstmt.close();
        return room;
    }

    //Uses room number to find room in ROOMS table and then updates the status of the Room inside the database
    public void updateRoomStatus(int roomNumber, RoomStatus status) throws SQLException {

        PreparedStatement pstmt = conn.prepareStatement("UPDATE ROOMS SET STATUS = ? WHERE ROOMNUMBER = ?");

        pstmt.setString(1, status.name());
        pstmt.setInt(2, roomNumber);
        pstmt.executeUpdate();
        pstmt.close();
    }

    //Returns all Rooms from ROOMS table and then returns them into the ArrayList as room objects
    public ArrayList<Room> getAllRooms() throws SQLException {

        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ROOMS");

        ResultSet rs = pstmt.executeQuery();
        ArrayList<Room> rooms = new ArrayList<>();

        while (rs.next()) {
            Room room = new Room(
                    rs.getInt("ROOMNUMBER"),
                    RoomType.valueOf(rs.getString("ROOMTYPE")),
                    rs.getInt("CAPACITY"));
            room.setStatus(RoomStatus.valueOf(rs.getString("STATUS")));
            rooms.add(room);
        }

        rs.close();
        pstmt.close();
        return rooms;
    }

}
