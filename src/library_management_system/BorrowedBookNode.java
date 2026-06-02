package library_management_system;

public class BorrowedBookNode {
    BorrowedBook data;
    BorrowedBookNode next;
    BorrowedBookNode prev;

    public BorrowedBookNode(BorrowedBook data) {
        this.data = data;
    }
}