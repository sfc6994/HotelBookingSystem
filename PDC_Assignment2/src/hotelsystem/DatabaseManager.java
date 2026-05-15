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
    
    private DatabaseManager(){
       try{
       conn = DriverManager.getConnection(URL);
       createTables();
       }catch(SQLException ex){
       System.err.println("Database Failed to Connect" + ex.getMessage());
       }
    }
    
    public static synchronized DatabaseManager getDataBaseManagerInstance(){
        if(dbm == null){
            dbm = new DatabaseManager();
        }
        return dbm;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException();
    }
    
    public Connection getConnection(){
        return this.conn;
    }
    
    public boolean checkTableExists(String tableName){
        boolean flag = false;
        try{
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, null, null);
            
            while(rs.next()){
            String tname = rs.getString("TABLE_NAME");
            if(tname.equalsIgnoreCase(tableName)){
                flag = true;
                }
            }
            
            rs.close();

        }catch(SQLException ex){
            System.err.println("Table check failed: " + ex.getMessage());
        }
        return flag;
    }
    
    public void createTables() throws SQLException{
        Statement statement = conn.createStatement();
        
        if(!checkTableExists("ROOMS")){
            statement.executeUpdate("CREATE TABLE ROOMS (ROOMNUMBER INT PRIMARY KEY, ROOMTYPE VARCHAR(20), "
                    + "CAPACITY INT, STATUS VARCHAR(20))");
        }
        
        if(!checkTableExists("BOOKINGS")){
            statement.executeUpdate("CREATE TABLE BOOKINGS (BOOKINGID INT PRIMARY KEY, GUESTNAME VARCHAR(75), "
                    + "ROOMNUMBER INT, CHECKIN VARCHAR(20), CHECKOUT VARCHAR(20), TOTALPRICE DOUBLE)");
        }
        
        if(!checkTableExists("REQUESTS")){
            statement.executeUpdate("CREATE TABLE REQUESTS (REQUESTID INT PRIMARY KEY, GUESTNAME VARCHAR(75), "
                    + "ROOMTYPE VARCHAR(20), GUESTCOUNT INT, CHECKIN VARCHAR(20), CHECKOUT VARCHAR(20), TOTALPRICE DOUBLE)");
        }
        statement.close();
    }
    
    public void shutdownDB(){
        try{
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        }catch(SQLException ex){
            System.out.println("The Database has successfully been shutdown");
        }
    }
    
}
