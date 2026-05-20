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

public class GuestMenu {

    private HotelSystem hotelSystem;
    private Scanner scanner = new Scanner(System.in);

    public GuestMenu(HotelSystem hotelSystem) {
        this.hotelSystem = hotelSystem;
    }

    public void displayGuestMenu() {
        System.out.println("=== Guest Portal ===");
        System.out.println("Would you like to make a booking request? (y/n): ");
    }

    public void handleGuestInput() throws SQLException{
        boolean running = true;
        while (running) {
            displayGuestMenu();
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("n")) {
                running = false;
            } else if (choice.equals("y")) {
                boolean validRequest = false;
                while (!validRequest) {
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine().trim();

                    RoomType type = null;
                    while (type == null) {
                        System.out.println("Select room type:");
                        System.out.println("1. SINGLE - Max 1 Guest");
                        System.out.println("2. DOUBLE - Max 2 Guests");
                        System.out.println("3. SUITE - Max 4 Guests");
                        System.out.print("Enter choice (1-3): ");
                        int roomChoice = getIntInput();
                        switch (roomChoice) {
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
                                System.out.println("Invalid choice. Please enter 1, 2 or 3.");
                        }
                    }
                    System.out.print("Enter number of guests: ");
                    int count = getIntInput();

                    String checkIn = null;
                    String checkOut = null;
                    boolean validDates = false;
                    while (!validDates) {
                        try {
                            System.out.print("Enter check in date (dd/MM/yyyy) example (12/08/2026): ");
                            checkIn = scanner.nextLine().trim();
                            System.out.print("Enter check out date (dd/MM/yyyy) example (14/08/2026): ");
                            checkOut = scanner.nextLine().trim();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate.parse(checkIn, formatter);
                            LocalDate.parse(checkOut, formatter);
                            validDates = true;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format. Please use dd/MM/yyyy.");
                        }
                    }

                    double estimatedCost = hotelSystem.calculatePrice(checkIn, checkOut, type);

                    System.out.println("\n--- Booking Request Summary ---");
                    System.out.println(name + " | " + type + " | " + count
                            + " guests | " + checkIn + " | " + checkOut
                            + " | $" + String.format("%.2f", estimatedCost));

                    System.out.print("\nIs this correct? (y/n/c to cancel): ");
                    String confirm = scanner.nextLine().trim().toLowerCase();

                    if (confirm.equals("y")) {
                        hotelSystem.createBookingRequest(name, type, count, checkIn, checkOut);
                        System.out.println("Booking request submitted successfully!");
                        validRequest = true;
                    } else if (confirm.equals("c")) {
                        System.out.println("Returning to main screen.");
                        running = false;
                        validRequest = true;
                    } else {
                        System.out.println("Restarting booking request...");
                    }
                }
            } else {
                System.out.println("Invalid input. Please enter y or n.");
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
