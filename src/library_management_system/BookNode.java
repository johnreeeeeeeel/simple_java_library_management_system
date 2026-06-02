package library_management_system;

public class BookNode {
    Book data;
    BookNode next;
    BookNode prev;

    public BookNode(Book data) {
        this.data = data;
    }
}