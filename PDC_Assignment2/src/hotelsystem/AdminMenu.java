/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelsystem;

/**
 *
 * @author Cameron
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.sql.*;

public class AdminMenu {

    private HotelSystem hotelSystem;
    private Scanner scanner = new Scanner(System.in);

    public AdminMenu(HotelSystem hotelSystem) {
        this.hotelSystem = hotelSystem;
    }

    public void displayAdminMenu() {
        System.out.println("\n=== Admin Portal ===");
        System.out.println("1. View Active Rooms");
        System.out.println("2. View Available Rooms");
        System.out.println("3. View Decommissioned Rooms");
        System.out.println("4. Find Room");
        System.out.println("5. Add Room");
        System.out.println("6. Update Room Status");
        System.out.println("7. View All Bookings");
        System.out.println("8. Find Booking");
        System.out.println("9. Create Booking");
        System.out.println("10. Cancel Booking");
        System.out.println("11. Check In");
        System.out.println("12. Check Out");
        System.out.println("13. View Requests");
        System.out.println("14. Approve Request");
        System.out.println("15. Delete Request");
        System.out.println("16. Exit");
        System.out.print("Enter choice: ");
    }

    public void handleAdminInput() throws SQLException{
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine().trim();
        if (!hotelSystem.verifyPassword(password)) {
            System.out.println("Incorrect password. Returning to main screen.");
            return;
        }

        boolean running = true;
        while (running) {
            displayAdminMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    for (Room r : hotelSystem.viewActiveRooms()) {
                        System.out.println(r);
                    }
                    break;
                case 2:
                    for (Room r : hotelSystem.availableRooms()) {
                        System.out.println(r);
                    }
                    break;
                case 3:
                    for (Room r : hotelSystem.decommissionedRooms()) {
                        System.out.println(r);
                    }
                    break;
                case 4:
                    System.out.print("Enter room number: ");
                    int findRoomNum = getIntInput();
                    Room foundRoom = hotelSystem.findRoom(findRoomNum);
                    if (foundRoom != null) {
                        System.out.println(foundRoom);
                    } else {
                        System.out.println("Room not found.");
                    }
                    break;
                case 5:
                    System.out.print("Enter room number: ");
                    int newRoomNum = getIntInput();
                    System.out.println("Select room type: 1=SINGLE (1 Guest max), 2=DOUBLE (2 Guests max), 3=SUITE (4 Guests max)");
                    int typeChoice = getIntInput();
                    RoomType type;
                    switch (typeChoice) {
                        case 1:
                            type = RoomType.SINGLE;
                            break;
                        case 2:
                            type = RoomType.DOUBLE;
                            break;
                        case 3:
                            type = RoomType.SUITE;
                            break;
                        default:
                            System.out.println("Invalid type, defaulting to SINGLE.");
                            type = RoomType.SINGLE;
                    }
                    System.out.print("Enter capacity: ");
                    int capacity = getIntInput();
                    hotelSystem.addRoom(newRoomNum, type, capacity);
                    System.out.println("Room has been added successfully");
                    break;
                case 6:
                    System.out.print("Enter room number: ");
                    int statusRoomNum = getIntInput();
                    System.out.println("Select status: 1=AVAILABLE, 2=OCCUPIED, 3=MAINTENANCE, 4=DECOMMISSIONED");
                    int statusChoice = getIntInput();
                    RoomStatus status = null;
                    switch (statusChoice) {
                        case 1:
                            status = RoomStatus.AVAILABLE;
                            break;
                        case 2:
                            status = RoomStatus.OCCUPIED;
                            break;
                        case 3:
                            status = RoomStatus.MAINTENANCE;
                            break;
                        case 4:
                            status = RoomStatus.DECOMMISSIONED;
                            break;
                        default:
                            System.out.println("Invalid status.");
                            break;
                    }
                    if (status != null) {
                        hotelSystem.updateRoomStatus(statusRoomNum, status);
                        System.out.println("Status has been updated");
                    }
                    break;
                case 7:
                    for (Booking b : hotelSystem.viewBookings()) {
                        System.out.println(b);
                    }
                    break;
                case 8:
                    System.out.print("Enter booking ID: ");
                    int findBookingId = getIntInput();
                    Booking foundBooking = hotelSystem.findBooking(findBookingId);
                    if (foundBooking != null) {
                        System.out.println(foundBooking);
                    } else {
                        System.out.println("Booking not found.");
                    }
                    break;
                case 9:
                    System.out.print("Enter guest name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter room number: ");
                    int bookRoomNum = getIntInput();
                    String checkIn = null;
                    String checkOut = null;
                    boolean validDates = false;
                    while (!validDates) {
                        try {
                            System.out.print("Enter check in date (dd/MM/yyyy): ");
                            checkIn = scanner.nextLine().trim();
                            System.out.print("Enter check out date (dd/MM/yyyy): ");
                            checkOut = scanner.nextLine().trim();

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate.parse(checkIn, formatter);
                            LocalDate.parse(checkOut, formatter);

                            validDates = true;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use dd/MM/yyyy.");
                        }
                    }
                    hotelSystem.createBooking(name, bookRoomNum, checkIn, checkOut);
                    System.out.println("Booking has been created");
                    break;
                case 10:
                    System.out.print("Enter booking ID: ");
                    int cancelId = getIntInput();
                    hotelSystem.cancelBooking(cancelId);
                    System.out.println("Cancelled the Booking" + cancelId);
                    break;
                case 11:
                    System.out.print("Enter booking ID: ");
                    int checkInId = getIntInput();
                    hotelSystem.checkIn(checkInId);
                    System.out.println("Checked in " + checkInId);
                    break;
                case 12:
                    System.out.print("Enter booking ID: ");
                    int checkOutId = getIntInput();
                    hotelSystem.checkOut(checkOutId);
                    System.out.println("Checked Out " + checkOutId);
                    break;
                case 13:
                    for (BookingRequest br : hotelSystem.viewRequests()) {
                        System.out.println(br);
                    }
                    break;
                case 14:
                    System.out.print("Enter request ID: ");
                    int approveId = getIntInput();
                    hotelSystem.approveRequest(approveId);
                    System.out.println("The request was approved successfully " + approveId);
                    break;
                case 15:
                    System.out.print("Enter request ID: ");
                    int deleteId = getIntInput();
                    hotelSystem.deleteRequest(deleteId);
                    System.out.println("The request was deleted successfully " + deleteId);
                    break;
                case 16:
                    System.out.println("Returning to main screen.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a correct number.");
            }
        }
    }
}
