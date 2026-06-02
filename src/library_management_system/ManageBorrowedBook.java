package library_management_system;

import java.util.*;

public class ManageBorrowedBook {
    Scanner scanner = new Scanner(System.in);
    
    public static BorrowedBookNode head;
    public static BorrowedBookNode tail;
    public static BorrowedBookNode current;
        
    public void borrowBook() {
        System.out.println("\n==============================");
        System.out.println("BORROW BOOK");
        
        Random random = new Random();
        int transactionNumber = 100000 + random.nextInt(900000);

        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();

        System.out.print("Enter student year: ");
        String studentYear = scanner.nextLine();

        System.out.print("Enter student set: ");
        String studentSet = scanner.nextLine();

        System.out.print("Enter book number: ");
        String bookNumber = scanner.nextLine();
        
        // Bloom filter validation
        if (Main.bookBorrowedFilter.mightContain(bookNumber)) {
            System.out.println("\nBook number " + bookNumber + " already borrowed.");
            return;
        }
        
        // Bloom filter validation
        if (!Main.bookNumberFilter.mightContain(bookNumber)) {
            System.out.println("\nBook number " + bookNumber + " not found.");
            return;
        }

        BookNode current = ManageBook.head;
        Book book = null;

        while (current != null) {
            if (current.data.getBookNumber().equals(bookNumber)) {
                book = current.data;
                break;
            }
            current = current.next;
        }

        BorrowedBook borrowedBook = new BorrowedBook(book, transactionNumber, studentName, studentYear, studentSet);
        book.setBookStatus("Borrowed");
        
        // Linked list insertion
        BorrowedBookNode newNode = new BorrowedBookNode(borrowedBook);

        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        
        // Bloom filter insertion
        Main.bookBorrowedFilter.add(bookNumber);

        System.out.println("\nBook borrowed successfully!");
        
        System.out.printf(
            "\n%-15s %-15s %-30s %-20s %-30s %-10s %-10s%n",
            "TRANSACTION NO.", "BOOK NO.", "TITLE", "AUTHOR", "STUDENT NAME", "YEAR", "SET"
        );

        System.out.printf(
            "%-15d %-15s %-30s %-20s %-30s %-10s %-10s%n",
            borrowedBook.getTransactionNumber(),
            book.getBookNumber(),
            book.getBookTitle(),
            book.getBookAuthor(),
            borrowedBook.getStudentName(),
            borrowedBook.getStudentYear(),
            borrowedBook.getStudentSet()
        );
    }
    
    public void returnBook() {
        System.out.println("\n==============================");
        System.out.println("RETURN BOOK");

        System.out.print("Enter book number: ");
        String bookNumber = scanner.nextLine();

        // Bloom filter validation
        if (!Main.bookBorrowedFilter.mightContain(bookNumber)) {
            System.out.println("\nBook number " + bookNumber + " is not currently borrowed.");
            return;
        }

        current = head;
        boolean found = false;

        while (current != null) {
            BorrowedBook borrowedBook = current.data;

            if (borrowedBook.getBook().getBookNumber().equalsIgnoreCase(bookNumber)) {
                borrowedBook.getBook().setBookStatus("Available");
                
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

                Main.bookBorrowedFilter = new BloomFilter(1000);

                BorrowedBookNode temp = head;
                while (temp != null) {
                    Main.bookBorrowedFilter.add(temp.data.getBook().getBookNumber());
                    temp = temp.next;
                }

                System.out.println("\nBook returned successfully!");
                
                System.out.printf(
                    "\n%-15s %-15s %-30s %-20s %-30s %-10s %-10s%n",
                    "TRANSACTION NO.", "BOOK NO.", "TITLE", "AUTHOR", "STUDENT NAME", "YEAR", "SET"
                );

                System.out.printf(
                    "%-15d %-15s %-30s %-20s %-30s %-10s %-10s%n",
                    borrowedBook.getTransactionNumber(),
                    borrowedBook.getBook().getBookNumber(),
                    borrowedBook.getBook().getBookTitle(),
                    borrowedBook.getBook().getBookAuthor(),
                    borrowedBook.getStudentName(),
                    borrowedBook.getStudentYear(),
                    borrowedBook.getStudentSet()
                );

                found = true;
                break;
            }

            current = current.next;
        }

        if (!found) {
            System.out.println("\nRETURN BOOK");
            
            System.out.println("\nBorrowed book not found.");
        }
    }
    
    public void viewBorrowedBooks() {
        System.out.println("\n==============================");
        System.out.println("BORROWED BOOK LIST");

        current = head;

        int i = 1;

        while (current != null) {
            BorrowedBook borrowedBook = current.data;

            System.out.printf(
                "\n%-15s %-15s %-30s %-20s %-10s %-12s %-30s %-10s %-10s%n",
                "TRANSACTION NO.", "BOOK NO.", "TITLE", "AUTHOR", "LOCATION", "STATUS", "STUDENT NAME", "YEAR", "SET"
            );

            System.out.printf(
                "%-15s %-15s %-30s %-20s %-10s %-12s %-30s %-10s %-10s%n",
                borrowedBook.getTransactionNumber(),
                borrowedBook.getBook().getBookNumber(),
                borrowedBook.getBook().getBookTitle(),
                borrowedBook.getBook().getBookAuthor(),
                borrowedBook.getBook().getBookLocation(),
                borrowedBook.getBook().getBookStatus(),
                borrowedBook.getStudentName(),
                borrowedBook.getStudentYear(),
                borrowedBook.getStudentSet()
            );

            current = current.next;
        }
        
        if (head == null) {
            System.out.println("No borrowed books yet");
            return;
        }
    }
    
    public void searchBorrowedBook() {     
        System.out.println("\n==============================");
        System.out.println("SEARCH BORROWED BOOK");
        
        System.out.print("Enter book number, title, author or student name: ");
        String input = scanner.nextLine();
        
        // Bloom filter validation
        if (!Main.bookNumberFilter.mightContain(input) && !Main.bookTitleFilter.mightContain(input) && !Main.bookAuthorFilter.mightContain(input) && !Main.bookBorrowedFilter.mightContain(input)) {
            System.out.println("\n" + input + " not found.");
            return;
        }

        current = head;
        boolean found = false;

        while (current != null) {
            BorrowedBook borrowedBook = current.data;

            if (
                borrowedBook.getBook().getBookNumber().equalsIgnoreCase(input)
                || borrowedBook.getBook().getBookTitle().equalsIgnoreCase(input)
                || borrowedBook.getBook().getBookAuthor().equalsIgnoreCase(input)
                || borrowedBook.getStudentName().equalsIgnoreCase(input)
            ) {

                System.out.println("\nRESULT");

                System.out.printf(
                    "\n%-15s %-15s %-30s %-20s %-10s %-12s %-30s %-10s %-10s%n",
                    "TRANSACTION NO.", "BOOK NO.", "TITLE", "AUTHOR", "LOCATION", "STATUS", "STUDENT NAME", "YEAR", "SET"
                );

                System.out.printf(
                    "%-15s %-15s %-30s %-20s %-10s %-12s %-30s %-10s %-10s%n",
                    borrowedBook.getTransactionNumber(),
                    borrowedBook.getBook().getBookNumber(),
                    borrowedBook.getBook().getBookTitle(),
                    borrowedBook.getBook().getBookAuthor(),
                    borrowedBook.getBook().getBookLocation(),
                    borrowedBook.getBook().getBookStatus(),
                    borrowedBook.getStudentName(),
                    borrowedBook.getStudentYear(),
                    borrowedBook.getStudentSet()
                );

                found = true;
            }

            current = current.next;
        }

        if (!found) {            
            System.out.println("Borrowed book not found.");
        }
    }
}
