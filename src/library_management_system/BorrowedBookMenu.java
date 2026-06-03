package library_management_system;

import java.util.Scanner;

public class BorrowedBookMenu {
    public void borrowedBookMenu(Scanner scanner) {
        int choice = 0;
        
        ManageBorrowedBook manageBorrowedBook = new ManageBorrowedBook();

        do {
            System.out.println("\n==============================");
            System.out.println("BORROWED BOOK MENU");
            System.out.println("1. View Borrowed Books");
            System.out.println("2. Search Borrowed Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Back To Main Menu");

            System.out.print("Enter choice: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
                
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid choice. Please enter a number.");
                continue;
            }
            
            switch (choice) {

                case 1:
                    manageBorrowedBook.viewBorrowedBooks(scanner);
                    break;
                    
                case 2:
                    manageBorrowedBook.searchBorrowedBook(scanner);
                    break;

                case 3:
                    manageBorrowedBook.borrowBook(scanner);
                    break;

                case 4:
                    manageBorrowedBook.returnBook(scanner);
                    break;

                case 5:
                    break;

                default:
                    System.out.println("\nInvalid choice.");
            }

        } while (choice != 5);
    }
}