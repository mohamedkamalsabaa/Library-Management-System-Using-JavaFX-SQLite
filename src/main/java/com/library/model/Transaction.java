package com.library.model;

import java.time.LocalDate;

/**
 * Represents a book borrowing transaction - when someone borrows or returns a book.
 * Tracks all the important dates and calculates fines for late returns.
 */
public class Transaction {
    private int id;                    // Unique transaction ID
    private int bookId;                // Which book was borrowed
    private int memberId;              // Who borrowed it
    private LocalDate borrowDate;      // When they borrowed it
    private LocalDate dueDate;         // When it's due back
    private LocalDate returnDate;      // When they actually returned it (null if still out)
    private TransactionType type;      // BORROW or RETURN
    private double fine;               // Late fee if they returned it late
    
    // Full book and member objects for display purposes
    private Book book;
    private Member member;
    
    // Whether this is borrowing or returning a book
    public enum TransactionType {
        BORROW, RETURN
    }
    
    // Create a new transaction (borrowing happens today, due in 2 weeks)
    public Transaction() {
        this.borrowDate = LocalDate.now();
        this.dueDate = LocalDate.now().plusDays(14); // Standard 2-week loan period
        this.type = TransactionType.BORROW;
        this.fine = 0.0;
    }
    
    // Constructor for borrowing
    public Transaction(int bookId, int memberId) {
        this();
        this.bookId = bookId;
        this.memberId = memberId;
    }
    
    // Constructor with all parameters
    public Transaction(int id, int bookId, int memberId, LocalDate borrowDate, 
                      LocalDate dueDate, LocalDate returnDate, TransactionType type, double fine) {
        this.id = id;
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.type = type;
        this.fine = fine;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getBookId() {
        return bookId;
    }
    
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    public int getMemberId() {
        return memberId;
    }
    
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
    
    public LocalDate getBorrowDate() {
        return borrowDate;
    }
    
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public LocalDate getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public void setType(TransactionType type) {
        this.type = type;
    }
    
    public double getFine() {
        return fine;
    }
    
    public void setFine(double fine) {
        this.fine = fine;
    }
    
    public Book getBook() {
        return book;
    }
    
    public void setBook(Book book) {
        this.book = book;
    }
    
    public Member getMember() {
        return member;
    }
    
    public void setMember(Member member) {
        this.member = member;
    }
    
    // Check if the book is overdue
    public boolean isOverdue() {
        if (returnDate != null) {
            return false;
        }
        return LocalDate.now().isAfter(dueDate);
    }
    
    // Calculate fine for overdue books
    public double calculateFine() {
        if (!isOverdue()) {
            return 0.0;
        }
        
        long daysOverdue = LocalDate.now().toEpochDay() - dueDate.toEpochDay();
        return daysOverdue * 1.0; // $1 per day (fine)
    }
    
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", memberId=" + memberId +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", type=" + type +
                ", fine=" + fine +
                '}';
    }
}
