package library_management_system;

import java.util.Scanner;

public class BookMenu {
    Scanner scanner = new Scanner(System.in);

    public void bookMenu() {
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
                    manageBook.viewBooks();
                    break;
                    
                case 2:
                    manageBook.searchBook();
                    break;

                case 3:
                    manageBook.addBook();
                    break;

                case 4:
                    manageBook.updateBook();
                    break;

                case 5:
                    manageBook.deleteBook();
                    break;

                case 6:
                    break;

                default:
                    System.out.println("\nInvalid choice.");
            }

        } while (choice != 6);
    }
}