package com.library.model;

import java.time.LocalDate;

/**
 * Represents a book in our library system.
 * Each book has basic info like title, author, and tracks whether it's available for borrowing.
 */
public class Book {
    private int id;                    // Unique database ID
    private String title;              // Book title
    private String author;             // Who wrote it
    private String isbn;               // International Standard Book Number
    private String category;           // Fiction, Science, History, etc.
    private boolean isAvailable;       // Can someone borrow this book right now?
    private LocalDate dateAdded;       // When we added it to our library
    
    // Create a new book with default settings
    public Book() {
        this.isAvailable = true;       // New books are available by default
        this.dateAdded = LocalDate.now(); // Remember when we got this book
    }
    
    // Create a book with all the essential info
    public Book(String title, String author, String isbn, String category) {
        this();
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
    }
    
    // Constructor with all parameters
    public Book(int id, String title, String author, String isbn, String category, boolean isAvailable, LocalDate dateAdded) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.isAvailable = isAvailable;
        this.dateAdded = dateAdded;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    
    public LocalDate getDateAdded() {
        return dateAdded;
    }
    
    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }
    
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", category='" + category + '\'' +
                ", isAvailable=" + isAvailable +
                ", dateAdded=" + dateAdded +
                '}';
    }
}
