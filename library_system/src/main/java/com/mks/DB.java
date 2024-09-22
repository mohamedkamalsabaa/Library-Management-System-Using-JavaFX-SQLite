package com.mks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    public static Connection conn = null;

    public static void connect() {
        try {
            // Load JDBC
            Class.forName("org.sqlite.JDBC");

            // SQLite database
            conn = DriverManager.getConnection("jdbc:sqlite:library.db");
            System.out.println("Connected to SQLite database.");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public static void createTables() {
        if (conn == null) {
            System.out.println("No database connection. Cannot create tables.");
            return;
        }

        String createBooksTableSQL = """
                    CREATE TABLE IF NOT EXISTS books (
                        id INTEGER PRIMARY KEY,
                        title TEXT NOT NULL,
                        author TEXT NOT NULL
                    );
                """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createBooksTableSQL);
            System.out.println("Books table created or already exists.");
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }

    public static void insertBook(Book book) {
        String insertBookSQL = "INSERT INTO books (title, author) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db"); // Ensure you have a method to get
                                                                                      // the connection
                PreparedStatement pstmt = conn.prepareStatement(insertBookSQL)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.executeUpdate();
            System.out.println("Book inserted into database.");
        } catch (SQLException e) {
            System.out.println("Error inserting book into database: " + e.getMessage());
        }
    }
}
