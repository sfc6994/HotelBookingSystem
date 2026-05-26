/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package hotelsystem;

import java.sql.Connection;
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
    
    public DatabaseManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getDataBaseManagerInstance method, of class DatabaseManager.
     */
    @Test
    public void testGetDataBaseManagerInstance() {
        System.out.println("getDataBaseManagerInstance");
        DatabaseManager expResult = null;
        DatabaseManager result = DatabaseManager.getDataBaseManagerInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clone method, of class DatabaseManager.
     */
    @Test
    public void testClone() throws Exception {
        System.out.println("clone");
        DatabaseManager instance = null;
        Object expResult = null;
        Object result = instance.clone();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConnection method, of class DatabaseManager.
     */
    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        DatabaseManager instance = null;
        Connection expResult = null;
        Connection result = instance.getConnection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkTableExists method, of class DatabaseManager.
     */
    @Test
    public void testCheckTableExists() {
        System.out.println("checkTableExists");
        String tableName = "";
        DatabaseManager instance = null;
        boolean expResult = false;
        boolean result = instance.checkTableExists(tableName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createTables method, of class DatabaseManager.
     */
    @Test
    public void testCreateTables() throws Exception {
        System.out.println("createTables");
        DatabaseManager instance = null;
        instance.createTables();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of shutdownDB method, of class DatabaseManager.
     */
    @Test
    public void testShutdownDB() {
        System.out.println("shutdownDB");
        DatabaseManager instance = null;
        instance.shutdownDB();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
