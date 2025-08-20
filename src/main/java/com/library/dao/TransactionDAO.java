package com.library.dao;

import com.library.database.DatabaseManager;
import com.library.model.Transaction;
import com.library.model.Book;
import com.library.model.Member;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Transaction operations.
 * Handles book borrowing, returning, and fine calculations.
 */
public class TransactionDAO {
    private final DatabaseManager dbManager;
    private final BookDAO bookDAO;  // We need this to update book availability
    
    public TransactionDAO() {
        this.dbManager = DatabaseManager.getInstance();
        this.bookDAO = new BookDAO();
    }
    
    /**
     * Add a new transaction (borrow a book)
     */
    public boolean borrowBook(Transaction transaction) {
        String sql = "INSERT INTO transactions (book_id, member_id, borrow_date, due_date, type) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, transaction.getBookId());
            pstmt.setInt(2, transaction.getMemberId());
            pstmt.setDate(3, Date.valueOf(transaction.getBorrowDate()));
            pstmt.setDate(4, Date.valueOf(transaction.getDueDate()));
            pstmt.setString(5, transaction.getType().toString());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        transaction.setId(generatedKeys.getInt(1));
                    }
                }
                
                // Update book availability
                bookDAO.updateBookAvailability(transaction.getBookId(), false);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error borrowing book: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Return a book
     */
    public boolean returnBook(int transactionId, double fine) {
        String sql = "UPDATE transactions SET return_date = ?, fine = ? WHERE id = ? AND return_date IS NULL";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(LocalDate.now()));
            pstmt.setDouble(2, fine);
            pstmt.setInt(3, transactionId);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                // Get the book ID to update availability
                Transaction transaction = getTransactionById(transactionId);
                if (transaction != null) {
                    bookDAO.updateBookAvailability(transaction.getBookId(), true);
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error returning book: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Get all transactions
     */
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.*, b.title as book_title, b.author, m.name as member_name " +
                    "FROM transactions t " +
                    "JOIN books b ON t.book_id = b.id " +
                    "JOIN members m ON t.member_id = m.id " +
                    "ORDER BY t.borrow_date DESC";
        
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Transaction transaction = createTransactionFromResultSet(rs);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all transactions: " + e.getMessage());
        }
        return transactions;
    }
    
    /**
     * Get a transaction by ID
     */
    public Transaction getTransactionById(int id) {
        String sql = "SELECT t.*, b.title as book_title, b.author, m.name as member_name " +
                    "FROM transactions t " +
                    "JOIN books b ON t.book_id = b.id " +
                    "JOIN members m ON t.member_id = m.id " +
                    "WHERE t.id = ?";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return createTransactionFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting transaction by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get active borrowings (not returned yet)
     */
    public List<Transaction> getActiveBorrowings() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.*, b.title as book_title, b.author, m.name as member_name " +
                    "FROM transactions t " +
                    "JOIN books b ON t.book_id = b.id " +
                    "JOIN members m ON t.member_id = m.id " +
                    "WHERE t.return_date IS NULL " +
                    "ORDER BY t.due_date ASC";
        
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Transaction transaction = createTransactionFromResultSet(rs);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Error getting active borrowings: " + e.getMessage());
        }
        return transactions;
    }
    
    /**
     * Get overdue books
     */
    public List<Transaction> getOverdueBooks() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.*, b.title as book_title, b.author, m.name as member_name " +
                    "FROM transactions t " +
                    "JOIN books b ON t.book_id = b.id " +
                    "JOIN members m ON t.member_id = m.id " +
                    "WHERE t.return_date IS NULL AND t.due_date < ? " +
                    "ORDER BY t.due_date ASC";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(LocalDate.now()));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = createTransactionFromResultSet(rs);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting overdue books: " + e.getMessage());
        }
        return transactions;
    }
    
    /**
     * Get transactions by member ID
     */
    public List<Transaction> getTransactionsByMember(int memberId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.*, b.title as book_title, b.author, m.name as member_name " +
                    "FROM transactions t " +
                    "JOIN books b ON t.book_id = b.id " +
                    "JOIN members m ON t.member_id = m.id " +
                    "WHERE t.member_id = ? " +
                    "ORDER BY t.borrow_date DESC";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = createTransactionFromResultSet(rs);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting transactions by member: " + e.getMessage());
        }
        return transactions;
    }
    
    /**
     * Check if a book is currently borrowed
     */
    public boolean isBookCurrentlyBorrowed(int bookId) {
        String sql = "SELECT COUNT(*) FROM transactions WHERE book_id = ? AND return_date IS NULL";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking if book is borrowed: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Helper method to create Transaction object from ResultSet
     */
    private Transaction createTransactionFromResultSet(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction(
            rs.getInt("id"),
            rs.getInt("book_id"),
            rs.getInt("member_id"),
            rs.getDate("borrow_date").toLocalDate(),
            rs.getDate("due_date").toLocalDate(),
            rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null,
            Transaction.TransactionType.valueOf(rs.getString("type")),
            rs.getDouble("fine")
        );
        
        // Create Book and Member objects for display
        Book book = new Book();
        book.setId(rs.getInt("book_id"));
        book.setTitle(rs.getString("book_title"));
        book.setAuthor(rs.getString("author"));
        transaction.setBook(book);
        
        Member member = new Member();
        member.setId(rs.getInt("member_id"));
        member.setName(rs.getString("member_name"));
        transaction.setMember(member);
        
        return transaction;
    }
}
