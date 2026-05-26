/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package hotelsystem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Cameron
 */
public class HotelSystemTest {

    private HotelSystem hotelSystem;

    @BeforeClass
    public static void setUpClass() throws SQLException {

    }

    //After all tests have finished shutdown database and removes the test rooms
    @AfterClass
    public static void tearDownClass() throws SQLException {
        Connection conn = DatabaseManager.getDataBaseManagerInstance().getConnection();
        conn.createStatement().execute("DELETE FROM BOOKINGS WHERE ROOMNUMBER = 996");
        conn.createStatement().execute("DELETE FROM ROOMS WHERE ROOMNUMBER IN (996, 997, 998, 999)");
        conn.createStatement().execute("DELETE FROM REQUESTS WHERE GUESTNAME = 'Johnny'");
        DatabaseManager.getDataBaseManagerInstance().shutdownDB();
    }

    //Before test creates HotelSystem object
    @Before
    public void setUp() throws SQLException {
        hotelSystem = new HotelSystem();
    }

    @After
    public void tearDown() throws SQLException {

    }

    /**
     * Test of verifyPassword method, of class HotelSystem.
     */
    @Test
    public void testVerifyPassword() throws SQLException {
        System.out.println("verifyPassword");
        String password = "Admin123";
        boolean expResult = true;
        boolean result = hotelSystem.verifyPassword(password);
        assertEquals(expResult, result);
        assertFalse(hotelSystem.verifyPassword("Hello World"));
    }

    /**
     * Test of calculatePrice method, of class HotelSystem.
     */
    @Test
    public void testCalculatePrice() throws SQLException {
        System.out.println("calculatePrice");
        String checkIn = "10/02/2026";
        String checkOut = "20/02/2026";
        assertEquals(1000, hotelSystem.calculatePrice(checkIn, checkOut, RoomType.SINGLE), 0.01);
        assertEquals(2000, hotelSystem.calculatePrice(checkIn, checkOut, RoomType.DOUBLE), 0.01);
        assertEquals(4000, hotelSystem.calculatePrice(checkIn, checkOut, RoomType.SUITE), 0.01);
    }

    /**
     * Test of addRoom method, of class HotelSystem. Checks if room was added by
     * its primary key if null it wasn't.
     */
    @Test
    public void testAddRoom() throws Exception {
        System.out.println("addRoom");
        hotelSystem.addRoom(997, RoomType.SINGLE, 1);
        hotelSystem.addRoom(998, RoomType.DOUBLE, 1);
        hotelSystem.addRoom(999, RoomType.SUITE, 1);
        assertNotNull(hotelSystem.findRoom(997));
        assertNotNull(hotelSystem.findRoom(998));
        assertNotNull(hotelSystem.findRoom(999));
    }

    /**
     * Test of createBooking method, of class HotelSystem.
     */
    @Test
    public void testCreateBooking() throws Exception {
        System.out.println("createBooking");
        int currentID = hotelSystem.getIdCounter();
        hotelSystem.addRoom(996, RoomType.SUITE, 4);
        hotelSystem.createBooking("John", 996, "10/02/2026", "20/02/2026");
        Booking testBooking = hotelSystem.findBooking(currentID);
        assertNotNull(testBooking);
        assertEquals("John", testBooking.getGuestName());
        assertEquals(996, testBooking.getRoomNumber());
    }

    /**
     * Test of createBookingRequest method, of class HotelSystem.
     */
    @Test
    public void testCreateBookingRequest() throws Exception {
        System.out.println("createBookingRequest");
        int currentID = hotelSystem.getIdCounter();
        hotelSystem.createBookingRequest("Johnny", RoomType.DOUBLE, 2, "10/02/2026", "20/02/2026");
        BookingRequest requestTest = hotelSystem.findRequest(currentID);
        assertNotNull(requestTest);
        assertEquals("Johnny", requestTest.getGuestName());
    }

}
