/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelsystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Cameron
 */
public class FileHandler {

    // --- ROOMS ---
    //saves the room information to the txt file
    public void saveRooms(ArrayList<Room> rooms) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("rooms.txt"))) {
            for (Room r : rooms) {
                // Saving 4 fields (3 from constructor + Status)
                String line = r.getRoomNumber() + "|" +
                              r.getRoomType().name() + "|" +
                              r.getCapacity() + "|" +
                              r.getStatus().name();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving rooms: " + e.getMessage());
        }
    }
    //loads the rooms information back into the system on startup
    public ArrayList<Room> loadRooms() {
        ArrayList<Room> rooms = new ArrayList<>();
        File file = new File("rooms.txt");
        if (!file.exists()) return rooms;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length < 4) continue;

                // 3-Arg Constructor
                int num = Integer.parseInt(p[0].trim());
                RoomType type = RoomType.valueOf(p[1].trim().toUpperCase());
                int cap = Integer.parseInt(p[2].trim());
                
                Room r = new Room(num, type, cap);
                
                // Set the 5th field via Setter
                r.setStatus(RoomStatus.valueOf(p[3].trim().toUpperCase()));
                rooms.add(r);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error loading rooms: " + e.getMessage());
        }
        return rooms;
    }

    // --- BOOKINGS ---
    //saves the bookings information to the txt file
    public void saveBookings(ArrayList<Booking> bookings) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("bookings.txt"))) {
            for (Booking b : bookings) {
                // Constructor order: id, name, roomNum, in, out, price
                String line = b.getBookingID() + "|" +
                              b.getGuestName() + "|" +
                              b.getRoomNumber() + "|" +
                              b.getCheckIn() + "|" +
                              b.getCheckOut() + "|" +
                              b.getTotalPrice();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving bookings: " + e.getMessage());
        }
    }
    //loads the bookings information back into the system on startup
    public ArrayList<Booking> loadBookings() {
        ArrayList<Booking> bookings = new ArrayList<>();
        File file = new File("bookings.txt");
        if (!file.exists()) return bookings;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length < 6) continue;

                // 6-Arg Constructor (Status is hardcoded to OCCUPIED in POJO)
                Booking b = new Booking(
                    Integer.parseInt(p[0].trim()),
                    p[1].trim(),
                    Integer.parseInt(p[2].trim()),
                    p[3].trim(),
                    p[4].trim(),
                    Double.parseDouble(p[5].trim())
                );
                bookings.add(b);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error loading bookings: " + e.getMessage());
        }
        return bookings;
    }

    // --- BOOKING REQUESTS ---
    //saves the requests information to the txt file
    public void saveRequests(ArrayList<BookingRequest> requests) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("requests.txt"))) {
            for (BookingRequest breq : requests) {
                String line = breq.getRequestID() + "|" +
                              breq.getGuestName() + "|" +
                              breq.getRoomType().name() + "|" +
                              breq.getGuestCount() + "|" +
                              breq.getCheckIn() + "|" +
                              breq.getCheckOut() + "|" +
                              breq.getTotalPrice();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving requests: " + e.getMessage());
        }
    }
    
    //loads the booking requests information back into the system on startup
    public ArrayList<BookingRequest> loadRequests() {
        ArrayList<BookingRequest> requests = new ArrayList<>();
        File file = new File("requests.txt");
        if (!file.exists()) return requests;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length < 7) continue;

                BookingRequest req = new BookingRequest(
                    Integer.parseInt(p[0].trim()),
                    p[1].trim(),
                    RoomType.valueOf(p[2].trim().toUpperCase()),
                    Integer.parseInt(p[3].trim()),
                    p[4].trim(),
                    p[5].trim(),
                    Double.parseDouble(p[6].trim())
                );
                requests.add(req);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error loading requests: " + e.getMessage());
        }
        return requests;
    }

    // --- ID COUNTER ---
    //Writes the current ID counter number to the file
    public void saveIdCounter(int counter) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("idcounter.txt"))) {
            bw.write(String.valueOf(counter));
        } catch (IOException e) {
            System.err.println("Error saving ID counter: " + e.getMessage());
        }
    }
    //loads it back when the system is restarted so no duplicate ID numbers are used
    public int loadIdCounter() {
        File file = new File("idcounter.txt");
        if (!file.exists()) return 1;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            return (line != null) ? Integer.parseInt(line.trim()) : 1;
        } catch (IOException | NumberFormatException e) {
            return 1;
        }
    }
}