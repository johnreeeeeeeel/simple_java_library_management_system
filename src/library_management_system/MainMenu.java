package library_management_system;

import java.util.*;

public class MainMenu {
    public void mainMenu(Scanner scanner) {
        int choice = 0;

        do {
            System.out.println("\n==============================");
            System.out.println("MAIN MENU");
            System.out.println("1. Manage Book");
            System.out.println("2. Manage Borrowed Book");
            System.out.println("3. Logout");

            System.out.print("Enter choice: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
                
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid choice. Please enter a number.");
                continue;
            }
            
            switch (choice) {

                case 1:
                    BookMenu bookMenu = new BookMenu();
                    bookMenu.bookMenu(scanner);
                    break;
                    
                case 2:
                    BorrowedBookMenu borrowedBookMenu = new BorrowedBookMenu();
                    borrowedBookMenu.borrowedBookMenu(scanner);
                    break;

                case 3:
                    System.out.println("\nLogged out.");
                    break;

                default:
                    System.out.println("\nInvalid choice.");
            }

        } while (choice != 3);
    }
}