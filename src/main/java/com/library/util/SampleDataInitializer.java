package com.library.util;

import com.library.dao.BookDAO;
import com.library.dao.MemberDAO;
import com.library.model.Book;
import com.library.model.Member;

/**
 * Populates our library with some sample books and members so you have something to work with!
 * Only runs if the database is empty - we don't want duplicates.
 */
public class SampleDataInitializer {
    
    private final BookDAO bookDAO;
    private final MemberDAO memberDAO;
    
    public SampleDataInitializer() {
        this.bookDAO = new BookDAO();
        this.memberDAO = new MemberDAO();
    }
    
    // Check if we need sample data and add it if the library is empty
    public void initializeSampleData() {
        // Let's check what's actually in the database
        int bookCount = bookDAO.getAllBooks().size();
        int memberCount = memberDAO.getAllMembers().size();
        System.out.println("Current database status:");
        System.out.println("Books in database: " + bookCount);
        System.out.println("Members in database: " + memberCount);
        
        // For debugging - let's force recreation of sample data
        if (bookCount == 0 && memberCount == 0) {
            System.out.println("Database is empty. Initializing with sample data...");
            addSampleBooks();
            addSampleMembers();
            System.out.println("Sample data initialization completed!");
        } else {
            System.out.println("Database already contains data. Current counts - Books: " + bookCount + ", Members: " + memberCount);
        }
        
        // Add sample books
        addSampleBooks();
        
        // Add sample members
        addSampleMembers();
        
        System.out.println("Sample data initialization completed!");
    }
    
    private void addSampleBooks() {
        Book[] sampleBooks = {
            new Book("To Kill a Mockingbird", "Harper Lee", "978-0-06-112008-4", "Fiction"),
            new Book("1984", "George Orwell", "978-0-452-28423-4", "Dystopian Fiction"),
            new Book("Pride and Prejudice", "Jane Austen", "978-0-14-143951-8", "Romance"),
            new Book("The Great Gatsby", "F. Scott Fitzgerald", "978-0-7432-7356-5", "Fiction"),
            new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "978-0-7475-3269-9", "Fantasy"),
            new Book("The Catcher in the Rye", "J.D. Salinger", "978-0-316-76948-0", "Fiction"),
            new Book("Lord of the Rings", "J.R.R. Tolkien", "978-0-544-00341-5", "Fantasy"),
            new Book("Jane Eyre", "Charlotte Brontë", "978-0-14-144114-6", "Gothic Fiction"),
            new Book("The Hobbit", "J.R.R. Tolkien", "978-0-547-92822-7", "Fantasy"),
            new Book("Fahrenheit 451", "Ray Bradbury", "978-1-4516-7331-9", "Science Fiction"),
            new Book("Introduction to Java Programming", "Y. Daniel Liang", "978-0-13-376131-3", "Programming"),
            new Book("Clean Code", "Robert C. Martin", "978-0-13-235088-4", "Programming"),
            new Book("The Art of Computer Programming", "Donald E. Knuth", "978-0-201-89683-1", "Computer Science"),
            new Book("Design Patterns", "Gang of Four", "978-0-201-63361-0", "Software Engineering"),
            new Book("Effective Java", "Joshua Bloch", "978-0-134-68599-1", "Programming")
        };
        
        for (Book book : sampleBooks) {
            bookDAO.save(book);
        }
        
        System.out.println("Added " + sampleBooks.length + " sample books.");
    }
    
    private void addSampleMembers() {
        Member[] sampleMembers = {
            new Member("John Smith", "john.smith@email.com", "555-0101", "123 Main St, Anytown, AT 12345"),
            new Member("Emily Johnson", "emily.johnson@email.com", "555-0102", "456 Oak Ave, Somewhere, ST 67890"),
            new Member("Michael Brown", "michael.brown@email.com", "555-0103", "789 Pine Rd, Elsewhere, ET 11111"),
            new Member("Sarah Davis", "sarah.davis@email.com", "555-0104", "321 Elm St, Nowhere, NT 22222"),
            new Member("David Wilson", "david.wilson@email.com", "555-0105", "654 Maple Dr, Anywhere, AT 33333"),
            new Member("Lisa Anderson", "lisa.anderson@email.com", "555-0106", "987 Cedar Ln, Someplace, SP 44444"),
            new Member("Robert Taylor", "robert.taylor@email.com", "555-0107", "147 Birch Ct, Othertown, OT 55555"),
            new Member("Jennifer Martinez", "jennifer.martinez@email.com", "555-0108", "258 Spruce Way, Newtown, NT 66666"),
            new Member("William Garcia", "william.garcia@email.com", "555-0109", "369 Ash Blvd, Oldtown, OT 77777"),
            new Member("Amanda Rodriguez", "amanda.rodriguez@email.com", "555-0110", "741 Willow Pl, Midtown, MT 88888")
        };
        
        for (Member member : sampleMembers) {
            memberDAO.save(member);
        }
        
        System.out.println("Added " + sampleMembers.length + " sample members.");
    }
}
