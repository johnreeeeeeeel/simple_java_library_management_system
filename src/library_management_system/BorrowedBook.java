package library_management_system;

public class BorrowedBook {

    private Book book;
    
    private final int transactionNumber;
    private String studentName;
    private String studentYear;
    private String studentSet;

    public BorrowedBook(Book book, int transactionNumber, String studentName, String studentYear, String studentSet) {
        this.transactionNumber = transactionNumber;
        this.studentName = studentName;
        this.studentYear = studentYear;
        this.studentSet = studentSet;
        this.book = book;
    }

    // Getters
    public Book getBook() {
        return book;
    }
    
    public int getTransactionNumber() {
        return transactionNumber;
    }
    
    public String getStudentName() {
        return studentName;
    }

    public String getStudentYear() {
        return studentYear;
    }

    public String getStudentSet() {
        return studentSet;
    }   
}