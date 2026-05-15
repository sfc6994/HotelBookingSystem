/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelsystem;

import java.util.Scanner;

/**
 *
 * @author Cameron
 */
//Password for Admin is Admin123
public class Main {

    public static void main(String args[]) {

        HotelSystem hotelSystem = new HotelSystem();
        Scanner scan = new Scanner(System.in);
        boolean run = true;

        while (run) {
            System.out.println("Hotel Booking System");
            System.out.println("Press (1) for Admin | Press (2) for Guest | Press (3) to Exit");
            //simple main to start everything, try-catch block in case non integer input entered
            try {
                int numChoice = Integer.parseInt(scan.nextLine().trim());

                switch (numChoice) {
                    case 1:
                        AdminMenu adminMenu = new AdminMenu(hotelSystem);
                        adminMenu.handleAdminInput();
                        break;
                    case 2:
                        GuestMenu guestMenu = new GuestMenu(hotelSystem);
                        guestMenu.handleGuestInput();
                        break;
                    case 3:
                        System.out.println("Goodbye");
                        run = false;
                        break;
                    default:
                        System.out.println("Sorry that is not a Choice, Please Try again with valid input");

                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, Please enter either 1,2 or 3");
            }

        }
        scan.close();
    }
}
