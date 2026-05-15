package hotelsystem;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Cameron
 */
public class Room
{
    
    //variables
    private final int roomNumber;
    private final RoomType roomType;
    private final int capacity;
    private double priceNightly;
    private RoomStatus status;
    
    //constructor for room
    public Room(int roomNumber, RoomType type, int capacity)
    {
        this.roomNumber = roomNumber;
        this.roomType = type;
        this.capacity = capacity;
        this.status = RoomStatus.AVAILABLE;
        
        //hardcoded rates for different room types
        switch(type)
        {
            case SINGLE:
                this.priceNightly = 100.00;
                break;
            case DOUBLE:
                this.priceNightly = 200.00;
                break;
            case SUITE:
                this.priceNightly = 400.00;
                break;
            default:
                this.priceNightly = 0.00;
            break;
        }
        
        
    }
    //retrieve the data from the variable
    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getPriceNightly() {
        return priceNightly;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }
    
    
    @Override 
    public String toString()
    {
        return "Room " + roomNumber + " | " + "Room Type " + roomType + " | " + String.format("$%.2f ", priceNightly) + " | " + "Capacity = " + capacity + " | " + "Status " + status;
    }
      
}
