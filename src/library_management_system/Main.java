package library_management_system;

import java.util.Scanner;

public class Main {
    // Bloom filter 
    public static BloomFilter bookIdFilter = new BloomFilter(1000);
    public static BloomFilter bookNumberFilter = new BloomFilter(1000);
    public static BloomFilter bookTitleFilter = new BloomFilter(1000);
    public static BloomFilter bookAuthorFilter = new BloomFilter(1000);
    public static BloomFilter bookLocationFilter = new BloomFilter(1000);
    public static BloomFilter bookStatusFilter = new BloomFilter(1000);
    
    public static BloomFilter borrowedTransactionNumberFilter = new BloomFilter(1000);
    public static BloomFilter borrowedBookNumberFilter = new BloomFilter(1000);
    
    public static BloomFilter studentNameFilter = new BloomFilter(1000);

    // Scanner
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice = 0;

        do {
            System.out.println("\n==============================");
            System.out.println("LIBRARY MANAGEMENT SYSTEM");
            System.out.println("1. Login");
            System.out.println("2. Exit");

            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid choice. Please enter a number.");
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.println("\n==============================");
                    System.out.println("LOGIN");
                    
                    System.out.print("Enter Username: ");
                    String adminUsername = scanner.nextLine().trim();
                    
                    System.out.print("Enter Password: ");
                    String adminPassword = scanner.nextLine().trim();
                    
                    if (adminUsername.isEmpty() || adminPassword.isEmpty()) {
                        System.out.println("\nUsername or password cannot be empty.");
                        
                    } else if (adminUsername.equals("admin") && adminPassword.equals("admin")) {
                        MainMenu adminMenu = new MainMenu();
                        adminMenu.mainMenu(scanner);
                        
                    } else {
                        System.out.println("\nInvalid username or password.");
                    }

                    break;

                case 2:
                    System.out.println("\nProgram ended.");
                    break;

                default:
                    System.out.println("\nInvalid choice.");
            }

        } while (choice != 2);

        scanner.close();
    }
}