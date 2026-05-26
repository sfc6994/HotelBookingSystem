/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package hotelsystem;

import java.sql.Connection;
import java.sql.SQLException;
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
public class DatabaseManagerTest {

    //Set up database connection
    @BeforeClass
    public static void setUpClass() {
        DatabaseManager.getDataBaseManagerInstance();
    }

    //Shut down the database
    @AfterClass
    public static void tearDownClass() {
        DatabaseManager.getDataBaseManagerInstance().shutdownDB();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // Verifies instance exists and calling twice returns the same instance (Singleton)
    @Test
    public void testGetDataBaseManagerInstance() {
        System.out.println("getDataBaseManagerInstance");
        DatabaseManager instance1 = DatabaseManager.getDataBaseManagerInstance();
        DatabaseManager instance2 = DatabaseManager.getDataBaseManagerInstance();
        assertNotNull(instance1);
        assertSame(instance1, instance2);
    }

    // Verifies that cloning DatabaseManager throws CloneNotSupportedException
    @Test(expected = CloneNotSupportedException.class)
    public void testClone() throws Exception {
        System.out.println("clone");
        DatabaseManager instance = DatabaseManager.getDataBaseManagerInstance();
        instance.clone();
    }

    // Verifies the connection is not null and is active
    @Test
    public void testGetConnection() throws SQLException {
        System.out.println("getConnection");
        DatabaseManager instance = DatabaseManager.getDataBaseManagerInstance();
        Connection result = instance.getConnection();
        assertNotNull(result);
        assertFalse(result.isClosed());
    }

    // Verifies the three core tables exist in the database
    @Test
    public void testCheckTableExists() {
        System.out.println("checkTableExists");
        DatabaseManager instance = DatabaseManager.getDataBaseManagerInstance();
        assertTrue(instance.checkTableExists("ROOMS"));
        assertTrue(instance.checkTableExists("BOOKINGS"));
        assertTrue(instance.checkTableExists("REQUESTS"));
    }

}
