/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelsystem;

/**
 *
 * @author Cameron
 */
public class BookingRequest 
{   
    private int requestID;
    private String guestName;
    private RoomType roomType;
    private int guestCount;
    private String checkIn;
    private String checkOut;
    private double totalPrice;
    
    public BookingRequest(int id, String name, RoomType type, int count, String in, String out, double price)
    {
        this.requestID = id;
        this.guestName = name;
        this.roomType = type;
        this.guestCount = count;
        this.checkIn = in;
        this.checkOut = out;
        this.totalPrice = price;
    }

    public int getRequestID() {
        return requestID;
    }

    public String getGuestName() {
        return guestName;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getGuestCount() {
        return guestCount;
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
    
    @Override
    public String toString()
    {
        return "Request ID " + requestID + " | " + "Guest Name " + guestName + " | " + "Room Type " + roomType + " | " + "Guest Count " + guestCount + " | " + "Check in " + checkIn + " | " 
                + "Check Out " + checkOut + " | " + String.format("$%.2f", totalPrice);
    }
    
}
