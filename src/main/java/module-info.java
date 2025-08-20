/**
 * 📚 Library Management System Module
 * 
 * This module defines what our application needs and what it shares with the outside world.
 * Think of it as the "front door" that tells Java what we're using and what others can use from us.
 */
module librarymanagement {
    // What we need from other modules
    requires javafx.controls;  // For UI components like buttons and tables
    requires javafx.fxml;      // For loading our UI layout files
    requires java.sql;         // For talking to our SQLite database
    
    // What we're willing to share with other modules
    exports com.library;                // Main app package
    exports com.library.controller;     // UI controllers
    exports com.library.model;          // Book, Member, Transaction classes
    exports com.library.dao;            // Database access objects
    exports com.library.database;       // Database connection management
    exports com.library.util;           // Helper utilities
    
    // Special permission for JavaFX to access our controller fields
    opens com.library.controller to javafx.fxml;
}
