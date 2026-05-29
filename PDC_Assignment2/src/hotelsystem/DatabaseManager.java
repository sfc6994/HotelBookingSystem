/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelsystem;

import java.sql.*;

/**
 *
 * @author Cameron
 */
public final class DatabaseManager {
    
    private static DatabaseManager dbm;
    private Connection conn;
    private static final String URL = "jdbc:derby:HotelDB_Ebd;create=true"; //url for the DB host
    
    //Private constructor that establishes connection to database and calls createTable if tables don't exist
    private DatabaseManager(){
       try{
       conn = DriverManager.getConnection(URL);
       createTables();
       }catch(SQLException ex){
       System.err.println("Database Failed to Connect: \nMake sure only one instance is running.\nDetails: " + ex.getMessage());
       }
    }
    
    //Prevents multiple threads checking the null check at same time and creating 2 Databases 
    //Only one thread will enter the statement and create database rest will always just return dbm
    public static synchronized DatabaseManager getDataBaseManagerInstance(){
        if(dbm == null){
            dbm = new DatabaseManager();
        }
        return dbm;
    }
    
    //Prevents cloning so only one database is made as per Singleton
    @Override
    public Object clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException();
    }
    
    //Returns the active connection of the database for the DAO classes to use
    public Connection getConnection(){
        return this.conn;
    }
    
    //Checks if the tables have already been created
    //Returns true if table already exists or false if not
    public boolean checkTableExists(String tableName){
        boolean flag = false;
        try{
            DatabaseMetaData dbmd = conn.getMetaData();
            try (ResultSet rs = dbmd.getTables(null, null, null, null)) {
                while(rs.next()){
                    String tname = rs.getString("TABLE_NAME");
                    if(tname.equalsIgnoreCase(tableName)){
                        flag = true;
                    }
                }
            }

        }catch(SQLException ex){
            System.err.println("Table check failed: " + ex.getMessage());
        }
        return flag;
    }
    
    //Creates the tables for the database if they didn't exist already
    //Sets primary keys and foreign key relationships between each of the tables
    public void createTables() throws SQLException{
        try (Statement statement = conn.createStatement()) {
            if(!checkTableExists("ROOMS")){
                statement.executeUpdate("CREATE TABLE ROOMS (ROOMNUMBER INT PRIMARY KEY, ROOMTYPE VARCHAR(20), "
                        + "CAPACITY INT, STATUS VARCHAR(20))");
            }
            //ROOMNUMBER is foreign key from the ROOMS table
            if(!checkTableExists("BOOKINGS")){
                statement.executeUpdate("CREATE TABLE BOOKINGS (BOOKINGID INT PRIMARY KEY, GUESTNAME VARCHAR(75), "
                        + "ROOMNUMBER INT REFERENCES ROOMS(ROOMNUMBER), CHECKIN VARCHAR(20), CHECKOUT VARCHAR(20), TOTALPRICE DOUBLE)");
            }
            
            if(!checkTableExists("REQUESTS")){
                statement.executeUpdate("CREATE TABLE REQUESTS (REQUESTID INT PRIMARY KEY, GUESTNAME VARCHAR(75), "
                        + "ROOMTYPE VARCHAR(20), GUESTCOUNT INT, CHECKIN VARCHAR(20), CHECKOUT VARCHAR(20), TOTALPRICE DOUBLE)");
            }
        }
    }
    
    //Shut down the Database when called, prints if successful
    public void shutdownDB(){
        try{
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        }catch(SQLException ex){
            System.out.println("The Database has successfully been shutdown");
        }
    }
    
}
