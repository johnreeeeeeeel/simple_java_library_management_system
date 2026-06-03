package library_management_system;

import java.util.Scanner;

public class BookMenu {
    public void bookMenu(Scanner scanner) {
        int choice = 0;
        
        ManageBook manageBook = new ManageBook();

        do {
            System.out.println("\n==============================");
            System.out.println("BOOK MENU");
            System.out.println("1. View Books");
            System.out.println("2. Search Book");
            System.out.println("3. Add Book");
            System.out.println("4. Update Book");
            System.out.println("5. Delete Book");
            System.out.println("6. Back To Main Menu");

            System.out.print("Enter choice: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
                
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid choice. Please enter a number.");
                continue;
            }
            
            switch (choice) {

                case 1:
                    manageBook.viewBooks(scanner);
                    break;
                    
                case 2:
                    manageBook.searchBook(scanner);
                    break;

                case 3:
                    manageBook.addBook(scanner);
                    break;

                case 4:
                    manageBook.updateBook(scanner);
                    break;

                case 5:
                    manageBook.deleteBook(scanner);
                    break;

                case 6:
                    break;

                default:
                    System.out.println("\nInvalid choice.");
            }

        } while (choice != 6);
    }
}