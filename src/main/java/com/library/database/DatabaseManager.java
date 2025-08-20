package com.library.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages our SQLite database connection and creates the tables we need.
 * Uses the Singleton pattern so we only have one connection throughout the app.
 */
public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:library.db";  // Our database file
    private static DatabaseManager instance;                               // The one and only instance
    private Connection connection;                                          // Database connection
    
    // Private constructor - only we can create instances
    private DatabaseManager() {
        try {
            // Connect to our SQLite database file (creates it if it doesn't exist)
            connection = DriverManager.getConnection(DATABASE_URL);
            createTables();  // Make sure all our tables exist
        } catch (SQLException e) {
            System.err.println("Oops! Couldn't connect to the database: " + e.getMessage());
        }
    }
    
    // Get the single instance of the database manager
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();  // Create it if this is the first time
        }
        return instance;
    }
    
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DATABASE_URL);
            }
        } catch (SQLException e) {
            System.err.println("Failed to get database connection: " + e.getMessage());
        }
        return connection;
    }
    
    private void createTables() {
        try (Statement stmt = connection.createStatement()) {
            // Create Books table
            String createBooksTable = "CREATE TABLE IF NOT EXISTS books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "author TEXT NOT NULL," +
                "isbn TEXT UNIQUE," +
                "category TEXT," +
                "is_available BOOLEAN DEFAULT 1," +
                "date_added DATE DEFAULT CURRENT_DATE" +
                ")";
            stmt.execute(createBooksTable);
            
            // Create Members table
            String createMembersTable = "CREATE TABLE IF NOT EXISTS members (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "email TEXT UNIQUE," +
                "phone TEXT," +
                "address TEXT," +
                "join_date DATE DEFAULT CURRENT_DATE," +
                "is_active BOOLEAN DEFAULT 1" +
                ")";
            stmt.execute(createMembersTable);
            
            // Create Transactions table
            String createTransactionsTable = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "book_id INTEGER NOT NULL," +
                "member_id INTEGER NOT NULL," +
                "borrow_date DATE NOT NULL," +
                "due_date DATE NOT NULL," +
                "return_date DATE," +
                "type TEXT NOT NULL CHECK (type IN ('BORROW', 'RETURN'))," +
                "fine REAL DEFAULT 0.0," +
                "FOREIGN KEY (book_id) REFERENCES books (id)," +
                "FOREIGN KEY (member_id) REFERENCES members (id)" +
                ")";
            stmt.execute(createTransactionsTable);
            
            // Create indexes for better performance
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_books_title ON books(title)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_books_author ON books(author)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_members_name ON members(name)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_transactions_book_id ON transactions(book_id)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_transactions_member_id ON transactions(member_id)");
            
            System.out.println("Database tables created successfully.");
            
        } catch (SQLException e) {
            System.err.println("Failed to create database tables: " + e.getMessage());
        }
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to close database connection: " + e.getMessage());
        }
    }
}
