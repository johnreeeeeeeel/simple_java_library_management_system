package library_management_system;

import java.util.*;

public class ManageBorrowedBook {    
    public static BorrowedBookNode head;
    public static BorrowedBookNode tail;
    public static BorrowedBookNode current;
        
    public void borrowBook(Scanner scanner) {
        System.out.println("\n==============================");
        System.out.println("BORROW BOOK");
        
        Random random = new Random();
        int transactionNumber = 100000 + random.nextInt(900000);

        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine().trim();
        
        // Input validation
        if (studentName.isEmpty()) {
            System.out.println("\nStudent name cannot be empty.");
            return;
        }

        System.out.print("Enter student year: ");
        String studentYear = scanner.nextLine().trim();
        
        // Input validation
        if (studentYear.isEmpty()) {
            System.out.println("\nStudent year cannot be empty.");
            return;
        }

        System.out.print("Enter student set: ");
        String studentSet = scanner.nextLine().trim();
        
        // Validation
        if (studentSet.isEmpty()) {
            System.out.println("\nStudent set cannot be empty.");
            return;
        }

        System.out.print("Enter book number: ");
        String bookNumber = scanner.nextLine().trim();
        
        // Validation
        if (bookNumber.isEmpty()) {
            System.out.println("\nBook number cannot be empty.");
            return;
            
        } else if (Main.borrowedBookNumberFilter.mightContain(bookNumber)) {
            System.out.println("\nBook number " + bookNumber + " already borrowed.");
            return;
            
        } else if (!Main.bookNumberFilter.mightContain(bookNumber)) {
            System.out.println("\nBook number " + bookNumber + " not found.");
            return;
            
        } else {
            System.out.println("\nNo book found.");
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
        Main.borrowedTransactionNumberFilter.add(String.valueOf(transactionNumber));
        Main.borrowedBookNumberFilter.add(bookNumber);
        Main.borrowedBookNumberFilter.add(studentName);

        System.out.println("\nBook borrowed successfully!");
        
        System.out.printf(
            "\n%-20s %-15s %-30s %-20s %-30s %-10s %-10s%n",
            "TRANSACTION NO.", "BOOK NO.", "TITLE", "AUTHOR", "STUDENT NAME", "YEAR", "SET"
        );

        System.out.printf(
            "%-20s %-15s %-30s %-20s %-30s %-10s %-10s%n",
            borrowedBook.getTransactionNumber(),
            book.getBookNumber(),
            book.getBookTitle(),
            book.getBookAuthor(),
            borrowedBook.getStudentName(),
            borrowedBook.getStudentYear(),
            borrowedBook.getStudentSet()
        );
    }
    
    public void returnBook(Scanner scanner) {
        System.out.println("\n==============================");
        System.out.println("RETURN BOOK");

        System.out.print("Enter book or transaction number: ");
        String input = scanner.nextLine().trim();
        
        // Validation
        if (input.isEmpty()) {
            System.out.println("\nTransaction number cannot be empty.");
            return;
            
        } else if (!Main.borrowedBookNumberFilter.mightContain(input) && !Main.borrowedTransactionNumberFilter.mightContain(input)) {
            System.out.println("\nBook/Transaction number " + input + " is not currently borrowed.");
            return;
        }        

        current = head;
        boolean found = false;

        while (current != null) {
            BorrowedBook borrowedBook = current.data;

            if (borrowedBook.getBook().getBookNumber().equalsIgnoreCase(input)
                    || String.valueOf(borrowedBook.getTransactionNumber()).equals(input)) {

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

                // Bloom filter update
                Main.borrowedTransactionNumberFilter = new BloomFilter(1000);
                Main.borrowedBookNumberFilter = new BloomFilter(1000);
                Main.studentNameFilter = new BloomFilter(1000);

                BorrowedBookNode temp = head;
                while (temp != null) {
                    Main.borrowedBookNumberFilter.add(temp.data.getBook().getBookNumber());
                    temp = temp.next;
                }

                System.out.println("\nBook returned successfully!");

                System.out.printf(
                    "\n%-20s %-15s %-30s %-20s %-30s %-10s %-10s%n",
                    "TRANSACTION NO.", "BOOK NO.", "TITLE", "AUTHOR", "STUDENT NAME", "YEAR", "SET"
                );

                System.out.printf(
                    "%-20s %-15s %-30s %-20s %-30s %-10s %-10s%n",
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
            System.out.println("\nBorrowed book not found.");
        }
    }
    
    public void viewBorrowedBooks(Scanner scanner) {
        System.out.println("\n==============================");
        System.out.println("BORROWED BOOK LIST");

        current = head;
        
        while (current != null) {
            BorrowedBook borrowedBook = current.data;

            System.out.printf(
                "\n%-20s %-15s %-30s %-20s %-10s %-30s %-10s %-10s%n",
                "TRANSACTION NO.", "BOOK NO.", "TITLE", "AUTHOR", "LOCATION", "STUDENT NAME", "YEAR", "SET"
            );

            System.out.printf(
                "%-20s %-15s %-30s %-20s %-10s %-30s %-10s %-10s%n",
                borrowedBook.getTransactionNumber(),
                borrowedBook.getBook().getBookNumber(),
                borrowedBook.getBook().getBookTitle(),
                borrowedBook.getBook().getBookAuthor(),
                borrowedBook.getBook().getBookLocation(),
                borrowedBook.getStudentName(),
                borrowedBook.getStudentYear(),
                borrowedBook.getStudentSet()
            );

            current = current.next;
        }
        
        if (head == null) {
            System.out.println("\nNo borrowed books yet.");
        }
    }
    
    public void searchBorrowedBook(Scanner scanner) {     
        System.out.println("\n==============================");
        System.out.println("SEARCH BORROWED BOOK");
        
        System.out.println("Search By: Book ID, Number, Title, Transaction Number or Student Name.");
        System.out.print("Enter: ");
        String input = scanner.nextLine();
        
        // Validation
        if (input.isEmpty()) {
            System.out.println("\nInput cannot be empty.");
            return;
            
        } else if (!Main.bookIdFilter.mightContain(input) && !Main.bookNumberFilter.mightContain(input) && !Main.bookTitleFilter.mightContain(input) && !Main.borrowedTransactionNumberFilter.mightContain(input) && !Main.studentNameFilter.mightContain(input)) {
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
                    "\n%-20s %-15s %-30s %-20s %-10s %-30s %-10s %-10s%n",
                    "TRANSACTION NO.", "BOOK NO.", "TITLE", "AUTHOR", "LOCATION", "STUDENT NAME", "YEAR", "SET"
                );

                System.out.printf(
                    "%-20s %-15s %-30s %-20s %-10s %-30s %-10s %-10s%n",
                    borrowedBook.getTransactionNumber(),
                    borrowedBook.getBook().getBookNumber(),
                    borrowedBook.getBook().getBookTitle(),
                    borrowedBook.getBook().getBookAuthor(),
                    borrowedBook.getBook().getBookLocation(),
                    borrowedBook.getStudentName(),
                    borrowedBook.getStudentYear(),
                    borrowedBook.getStudentSet()
                );

                found = true;
            }

            current = current.next;
        }

        if (!found) {            
            System.out.println("\nNo borrowed books found.");
        }
    }
}
