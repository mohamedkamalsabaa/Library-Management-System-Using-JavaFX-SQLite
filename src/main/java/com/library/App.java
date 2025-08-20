package com.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.library.database.DatabaseManager;
import com.library.util.SampleDataInitializer;

/**
 * Main application entry point - this is where everything begins!
 * Sets up the JavaFX window and initializes our library database.
 */
public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load our main UI from the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/library/view/main.fxml"));
        Parent root = loader.load();
        
        // Create the main window with a nice size
        Scene scene = new Scene(root, 1200, 800);
        
        // Apply our beautiful modern stylesheet
        scene.getStylesheets().add(getClass().getResource("/com/library/view/styles.css").toExternalForm());
        
        // Configure the main window
        primaryStage.setTitle("Library Management System");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(700);
        primaryStage.show();
        
        // Make sure we clean up the database when user closes the app
        primaryStage.setOnCloseRequest(e -> {
            DatabaseManager.getInstance().closeConnection();
        });
    }
    
    @Override
    public void stop() throws Exception {
        // Double-check database cleanup when app shuts down
        DatabaseManager.getInstance().closeConnection();
        super.stop();
    }
    
    public static void main(String[] args) {
        // Set up our database and sample data before launching the UI
        DatabaseManager.getInstance();
        
        // Add some books and members if the database is empty
        SampleDataInitializer initializer = new SampleDataInitializer();
        initializer.initializeSampleData();
        
        // Fire up the JavaFX application!
        launch(args);
    }
}
