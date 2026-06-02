package library_management_system;

import java.util.*;

public class ManageBook {
    Scanner scanner = new Scanner(System.in);
        
    public static BookNode head;
    public static BookNode tail;
    public static BookNode current;

    public void addBook() {  
        System.out.println("\n==============================");
        System.out.println("ADD BOOK");
        
        Random random = new Random();
        int bookId = 100000 + random.nextInt(900000);

        System.out.print("Enter book number: ");
        String bookNumber = scanner.nextLine();

        // Bloom filter validation
        if (Main.bookNumberFilter.mightContain(bookNumber)) {
            current = head;

            while (current != null) {
                if (current.data.getBookNumber().equals(bookNumber)) {
                    System.out.println("\nBook number " + bookNumber + " already exists.");
                    return;
                }

                current = current.next;
            }
        }
        
        System.out.print("Enter book title: ");
        String bookTitle = scanner.nextLine();

        System.out.print("Enter book author: ");
        String bookAuthor = scanner.nextLine();
        
        System.out.print("Enter book location: ");
        String bookLocation = scanner.nextLine();
        
        Book book = new Book(bookId, bookNumber, bookTitle, bookAuthor, bookLocation, null);
        book.setBookStatus("Available");
        
        // Linked list insertion
        BookNode newNode = new BookNode(book);

        if (head == null) {
            head = tail = newNode;
            
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        
        // Bloom filter insertion
        Main.bookIdFilter.add(String.valueOf(book.getBookId()));
        Main.bookNumberFilter.add(book.getBookNumber());
        Main.bookTitleFilter.add(book.getBookTitle());
        Main.bookAuthorFilter.add(book.getBookAuthor());
        Main.bookLocationFilter.add(book.getBookLocation());
        Main.bookStatusFilter.add(book.getBookStatus());

        System.out.println("\nBook added successfully!");
        
        System.out.printf(
            "\n%-15s %-15s %-30s %-20s %-12s %-12s%n",
            "ID", "BOOK NO.", "TITLE", "AUTHOR", "LOCATION", "STATUS"
        );

        System.out.printf(
            "%-15s %-15s %-30s %-20s %-12s %-12s%n",
            book.getBookId(),
            book.getBookNumber(),
            book.getBookTitle(),
            book.getBookAuthor(),
            book.getBookLocation(),
            book.getBookStatus()
        );
    }

    public void updateBook() {
        System.out.println("\n==============================");
        System.out.println("UPDATE BOOK");
        
        System.out.print("Enter Book ID: ");
        int bookId = Integer.parseInt(scanner.nextLine());
        
        // Bloom filter validation
        if (!Main.bookIdFilter.mightContain(String.valueOf(bookId))) {
            System.out.println("\nBook id " + bookId + " not found.");
            return;
        }

        current = head;

        while (current != null) {
            if (current.data.getBookId() == bookId) {
                System.out.println("\nUPDATE BOOK (" + bookId + ")");
                
                System.out.print("Enter new number (leave blank to keep current): ");
                String bookNumber = scanner.nextLine();
                
                // Bloom filter validation
                if (Main.bookNumberFilter.mightContain(bookNumber)) {
                    current = head;

                    while (current != null) {
                        if (current.data.getBookNumber().equals(bookNumber)) {
                            System.out.println("\nBook number " + bookNumber + " already exists.");
                            return;
                        }

                        current = current.next;
                    }
                }
                
                System.out.print("Enter new title (leave blank to keep current): ");
                String bookTitle = scanner.nextLine();

                System.out.print("Enter new author (leave blank to keep current): ");
                String bookAuthor = scanner.nextLine();
                
                System.out.print("Enter new location (leave blank to keep current): ");
                String bookLocation = scanner.nextLine();
                
                if (!bookNumber.isEmpty()) {
                    current.data.setBookNumber(bookNumber);
                }
                
                if (!bookTitle.isEmpty()) {
                    current.data.setBookTitle(bookTitle);
                }

                if (!bookAuthor.isEmpty()) {
                    current.data.setBookAuthor(bookAuthor);
                }
                
                if (!bookLocation.isEmpty()) {
                    current.data.setBookLocation(bookLocation);
                }
                
                System.out.println("\nBook updated successfully!");
                
                System.out.printf(
                    "\n%-15s %-15s %-30s %-20s %-12s %-12s%n",
                    "ID", "BOOK NO.", "TITLE", "AUTHOR", "LOCATION", "STATUS"
                );

                System.out.printf(
                    "%-15s %-15s %-30s %-20s %-12s %-12s%n",
                    current.data.getBookId(),
                    current.data.getBookNumber(),
                    current.data.getBookTitle(),
                    current.data.getBookAuthor(),
                    current.data.getBookLocation(),
                    current.data.getBookStatus()
                );
                
                return;
            }

            current = current.next;
        }

        System.out.println("\nNo book found.");
    }

    public void deleteBook() {     
        System.out.println("\n==============================");
        System.out.println("DELETE BOOK");

        System.out.print("Enter Book ID: ");
        int bookId = Integer.parseInt(scanner.nextLine());
        
        // Bloom filter validation
        if (!Main.bookIdFilter.mightContain(String.valueOf(bookId))) {
            System.out.println("\nBook id " + bookId + " not found.");
            return;
        }

        current = head;

        while (current != null) {
            if (current.data.getBookId() == bookId) {
                if (current == head) {
                    head = current.next;
                    if (head != null) head.prev = null;
                }

                else if (current == tail) {
                    tail = current.prev;
                    if (tail != null) tail.next = null;
                }

                else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                
                // Bloom filter updation
                Main.bookIdFilter = new BloomFilter(1000);
                Main.bookNumberFilter = new BloomFilter(1000);
                Main.bookTitleFilter = new BloomFilter(1000);
                Main.bookAuthorFilter = new BloomFilter(1000);
                Main.bookLocationFilter = new BloomFilter(1000);
                Main.bookStatusFilter = new BloomFilter(1000);
                
                BookNode temp = head;

                while (temp != null) {
                    Book book = temp.data;

                    Main.bookIdFilter.add(String.valueOf(book.getBookId()));
                    Main.bookNumberFilter.add(book.getBookNumber());
                    Main.bookTitleFilter.add(book.getBookTitle());
                    Main.bookAuthorFilter.add(book.getBookAuthor());
                    Main.bookLocationFilter.add(book.getBookLocation());
                    Main.bookStatusFilter.add(book.getBookStatus());

                    temp = temp.next;
                }
                
                System.out.println("\nBook deleted successfully!");
                                
                System.out.printf(
                    "\n%-15s %-15s %-30s %-20s %-12s %-12s%n",
                    "ID", "BOOK NO.", "TITLE", "AUTHOR", "LOCATION", "STATUS"
                );

                System.out.printf(
                    "%-15s %-15s %-30s %-20s %-12s %-12s%n",
                    current.data.getBookId(),
                    current.data.getBookNumber(),
                    current.data.getBookTitle(),
                    current.data.getBookAuthor(),
                    current.data.getBookLocation(),
                    current.data.getBookStatus()
                );
                
                return;
            }

            current = current.next;
        }
                
        System.out.println("\nNo book found.");
    }
    
    public void viewBooks() {
        System.out.println("\n==============================");
        System.out.println("BOOK LIST");

        current = head;

        int i = 1;

        while (current != null) {
            System.out.printf(
                "\n%-15s %-15s %-30s %-20s %-12s %-12s%n",
                "ID", "BOOK NO.", "TITLE", "AUTHOR", "LOCATION", "STATUS"
            );

            System.out.printf(
                "%-15s %-15s %-30s %-20s %-12s %-12s%n",
                current.data.getBookId(),
                current.data.getBookNumber(),
                current.data.getBookTitle(),
                current.data.getBookAuthor(),
                current.data.getBookLocation(),
                current.data.getBookStatus()
            );

            current = current.next;
            i++;
        }
        
        if (head == null) {
            System.out.println("No books available yet.");
        }
    }
    
    public void searchBook() {    
        System.out.println("\n==============================");
        System.out.println("SEARCH BOOK");
        
        System.out.println("Search By: Book ID, Number, Title, Author, Location, or Status.");
        System.out.print("Enter: ");
        String input = scanner.nextLine();
        
        // Bloom filter validation
        if (!Main.bookIdFilter.mightContain(input) && !Main.bookNumberFilter.mightContain(input) && !Main.bookTitleFilter.mightContain(input) && !Main.bookAuthorFilter.mightContain(input) && !Main.bookLocationFilter.mightContain(input) && !Main.bookStatusFilter.mightContain(input)) {
            System.out.println("\n" + input + " not found.");
            return;
        }

        current = head;
        boolean found = false;

        while (current != null) {
            Book book = current.data;

            if ((String.valueOf(book.getBookId()).equalsIgnoreCase(input)) || book.getBookNumber().equalsIgnoreCase(input) || book.getBookTitle().equalsIgnoreCase(input) || book.getBookAuthor().equalsIgnoreCase(input) || book.getBookLocation().equalsIgnoreCase(input) || book.getBookStatus().equalsIgnoreCase(input)) {

                System.out.println("\nRESULT");
                
                System.out.printf(
                    "\n%-15s %-15s %-30s %-20s %-12s %-12s%n",
                    "ID", "BOOK NO.", "TITLE", "AUTHOR", "LOCATION", "STATUS"
                );

                System.out.printf(
                    "%-15s %-15s %-30s %-20s %-12s %-12s%n",
                    current.data.getBookId(),
                    current.data.getBookNumber(),
                    current.data.getBookTitle(),
                    current.data.getBookAuthor(),
                    current.data.getBookLocation(),
                    current.data.getBookStatus()
                );

                found = true;
            }

            current = current.next;
        }

        if (!found) {            
            System.out.println("\nNo Books found.");
        }
    }
}