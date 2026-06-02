package library_management_system;

public class Book {

    private final int bookId;
    
    private String bookNumber;
    private String bookTitle;
    private String bookAuthor;
    private String bookLocation;
    private String bookStatus;

    public Book(int bookId, String bookNumber, String bookTitle, String bookAuthor, String bookLocation, String bookStatus) {
        this.bookId = bookId;
        this.bookNumber = bookNumber;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookLocation = bookLocation;
        this.bookStatus = bookStatus;
    }
    
    // Getters
    public int getBookId() {
        return bookId;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }
    
    public String getBookLocation() {
        return bookLocation;
    }
    
    public String getBookStatus() {
        return bookStatus;
    }
    
    // Setters
    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }
    
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
    
    public void setBookLocation(String bookLocation) {
        this.bookLocation = bookLocation;
    }
    
    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }
}
