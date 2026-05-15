/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelsystem;

/**
 *
 * @author Cameron
 */
public class Booking 
{
    private int bookingID;
    private String guestName;
    private int roomNumber;
    private String checkIn;
    private String checkOut;
    private double totalPrice;
    private RoomStatus status;
    
    public Booking(int id, String name, int roomNum, String in, String out, double price)
    {
        this.bookingID = id;
        this.guestName = name;
        this.roomNumber = roomNum;
        this.checkIn = in;
        this.checkOut = out;
        this.totalPrice = price;
        this.status = RoomStatus.OCCUPIED;
    }

    public int getBookingID() {
        return bookingID;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }
    
        @Override 
    public String toString()
    {
        return "Booking ID " + bookingID + " | " + "Guest Name " + guestName + " | "+ "Room Number " + roomNumber + 
                " | " + "Check In " + checkIn + " | " + " Check Out " + checkOut + " | " + String.format("$%.2f", totalPrice);
    }
    
}
