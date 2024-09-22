package com.mks;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PrimaryController {

    @FXML
    private ListView<Books_DB> bookListView;

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField genreField;

    @FXML
    private TextField heightField;

    @FXML
    private TextField publisherField;

    @FXML
    public void initialize() {
        //book data
        bookListView.getItems().addAll(
                new Books_DB("Book 1", "Author 1", "Fiction", 200, "Publisher 1"),
                new Books_DB("Book 2", "Author 2", "Non-Fiction", 150, "Publisher 2"),
                new Books_DB("Book 3", "Author 3", "Mystery", 300, "Publisher 3"));
    }

    @FXML
    private void handleAddBook() {
        // Fetch data
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreField.getText();
        int height;
        try {
            height = Integer.parseInt(heightField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid height", "Please enter a valid integer for height.");
            return;
        }
        String publisher = publisherField.getText();

        if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || publisher.isEmpty()) {
            showAlert("Incomplete Information", "Please fill all fields.");
            return;
        }

        // Add new book
        Books_DB newBook = new Books_DB(title, author, genre, height, publisher);
        bookListView.getItems().add(newBook);

        clearFields(); 
    }

    @FXML
    private void handleDeleteBook() {
        Books_DB selectedBook = bookListView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            bookListView.getItems().remove(selectedBook);
        } else {
            showAlert("No Selection", "Please select a book to delete.");
        }
    }

    @FXML
    private void handleModifyBook() {
        Books_DB selectedBook = bookListView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert("No Selection", "Please select a book to modify.");
            return;
        }

        // Fetch new data
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreField.getText();
        int height;
        try {
            height = Integer.parseInt(heightField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid height", "Please enter a valid integer for height.");
            return;
        }
        String publisher = publisherField.getText();

        if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || publisher.isEmpty()) {
            showAlert("Incomplete Information", "Please fill all fields.");
            return;
        }

        // Modify 
        selectedBook = new Books_DB(title, author, genre, height, publisher);
        int index = bookListView.getSelectionModel().getSelectedIndex();
        bookListView.getItems().set(index, selectedBook);

        clearFields();
    }

    private void clearFields() {
        titleField.clear();
        authorField.clear();
        genreField.clear();
        heightField.clear();
        publisherField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
