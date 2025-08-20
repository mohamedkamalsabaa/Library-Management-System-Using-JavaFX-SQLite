package com.library.controller;

import com.library.dao.BookDAO;
import com.library.dao.MemberDAO;
import com.library.dao.TransactionDAO;
import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Transaction;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleBooleanProperty;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Main controller for our Library Management System UI.
 * Handles all the button clicks, table updates, and user interactions.
 * This is where the magic happens between the user interface and our data!
 */
public class MainController implements Initializable {
    
    // Our data access objects - these talk to the database
    private BookDAO bookDAO;
    private MemberDAO memberDAO;
    private TransactionDAO transactionDAO;
    
    // TabPane
    @FXML private TabPane tabPane;
    
    // Books Tab
    @FXML private TableView<Book> booksTable;
    @FXML private TableColumn<Book, Integer> bookIdColumn;
    @FXML private TableColumn<Book, String> bookTitleColumn;
    @FXML private TableColumn<Book, String> bookAuthorColumn;
    @FXML private TableColumn<Book, String> bookIsbnColumn;
    @FXML private TableColumn<Book, String> bookCategoryColumn;
    @FXML private TableColumn<Book, Boolean> bookAvailableColumn;
    @FXML private TextField bookSearchField;
    @FXML private TextField bookTitleField;
    @FXML private TextField bookAuthorField;
    @FXML private TextField bookIsbnField;
    @FXML private TextField bookCategoryField;
    
    // Members Tab
    @FXML private TableView<Member> membersTable;
    @FXML private TableColumn<Member, Integer> memberIdColumn;
    @FXML private TableColumn<Member, String> memberNameColumn;
    @FXML private TableColumn<Member, String> memberEmailColumn;
    @FXML private TableColumn<Member, String> memberPhoneColumn;
    @FXML private TableColumn<Member, Boolean> memberActiveColumn;
    @FXML private TextField memberSearchField;
    @FXML private TextField memberNameField;
    @FXML private TextField memberEmailField;
    @FXML private TextField memberPhoneField;
    @FXML private TextArea memberAddressField;
    
    // Transactions Tab
    @FXML private TableView<Transaction> transactionsTable;
    @FXML private TableColumn<Transaction, Integer> transactionIdColumn;
    @FXML private TableColumn<Transaction, String> transactionBookColumn;
    @FXML private TableColumn<Transaction, String> transactionMemberColumn;
    @FXML private TableColumn<Transaction, LocalDate> transactionBorrowDateColumn;
    @FXML private TableColumn<Transaction, LocalDate> transactionDueDateColumn;
    @FXML private TableColumn<Transaction, LocalDate> transactionReturnDateColumn;
    @FXML private TableColumn<Transaction, Double> transactionFineColumn;
    @FXML private ComboBox<Book> borrowBookComboBox;
    @FXML private ComboBox<Member> borrowMemberComboBox;
    @FXML private Label overdueCountLabel;
    
    // Observable lists
    private ObservableList<Book> booksList = FXCollections.observableArrayList();
    private ObservableList<Member> membersList = FXCollections.observableArrayList();
    private ObservableList<Transaction> transactionsList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize DAOs
        bookDAO = new BookDAO();
        memberDAO = new MemberDAO();
        transactionDAO = new TransactionDAO();
        
        // Load data FIRST
        loadBooks();
        loadMembers();
        loadTransactions();
        
        // THEN initialize tables with loaded data
        initializeBooksTable();
        initializeMembersTable();
        initializeTransactionsTable();
        
        updateOverdueCount();
        
        // Set up search functionality
        setupSearchFunctionality();
    }
    
    private void initializeBooksTable() {
        // Simple debugging: create test data to verify table is working
        System.out.println("=== DEBUGGING TABLE INITIALIZATION ===");
        
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookIsbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        bookCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        bookAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
        
        // Add explicit cell factory for ALL columns with maximum visibility
        bookIdColumn.setCellFactory(column -> {
            return new TableCell<Book, Integer>() {
                @Override
                protected void updateItem(Integer id, boolean empty) {
                    super.updateItem(id, empty);
                    if (empty || id == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText("🆔 " + String.valueOf(id));
                        setStyle("-fx-text-fill: #FF0000 !important; -fx-font-size: 20px !important; -fx-font-weight: bold !important; -fx-padding: 15px !important; -fx-background-color: #FFFF00 !important; -fx-border-color: #000000 !important; -fx-border-width: 2px !important;");
                        System.out.println("*** ULTRA VISIBLE BOOK ID: 🆔 " + String.valueOf(id));
                    }
                }
            };
        });
        
        bookTitleColumn.setCellFactory(column -> {
            return new TableCell<Book, String>() {
                @Override
                protected void updateItem(String title, boolean empty) {
                    super.updateItem(title, empty);
                    if (empty || title == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        // MAXIMUM VISIBILITY - prefix + large text + contrasting colors
                        setText("📖 " + title.toUpperCase());
                        setStyle("-fx-text-fill: #FF0000 !important; -fx-font-size: 20px !important; -fx-font-weight: bold !important; -fx-padding: 15px !important; -fx-background-color: #FFFF00 !important; -fx-border-color: #000000 !important; -fx-border-width: 2px !important;");
                        System.out.println("*** ULTRA VISIBLE BOOK TITLE: 📖 " + title.toUpperCase());
                    }
                }
            };
        });
        
        bookAuthorColumn.setCellFactory(column -> {
            return new TableCell<Book, String>() {
                @Override
                protected void updateItem(String author, boolean empty) {
                    super.updateItem(author, empty);
                    if (empty || author == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText("✍️ " + author.toUpperCase());
                        setStyle("-fx-text-fill: #FF0000 !important; -fx-font-size: 20px !important; -fx-font-weight: bold !important; -fx-padding: 15px !important; -fx-background-color: #FFFF00 !important; -fx-border-color: #000000 !important; -fx-border-width: 2px !important;");
                        System.out.println("*** ULTRA VISIBLE BOOK AUTHOR: ✍️ " + author.toUpperCase());
                    }
                }
            };
        });
        
        bookIsbnColumn.setCellFactory(column -> {
            return new TableCell<Book, String>() {
                @Override
                protected void updateItem(String isbn, boolean empty) {
                    super.updateItem(isbn, empty);
                    if (empty || isbn == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText("🔢 " + isbn.toUpperCase());
                        setStyle("-fx-text-fill: #FF0000 !important; -fx-font-size: 20px !important; -fx-font-weight: bold !important; -fx-padding: 15px !important; -fx-background-color: #FFFF00 !important; -fx-border-color: #000000 !important; -fx-border-width: 2px !important;");
                        System.out.println("*** ULTRA VISIBLE BOOK ISBN: 🔢 " + isbn.toUpperCase());
                    }
                }
            };
        });
        
        bookCategoryColumn.setCellFactory(column -> {
            return new TableCell<Book, String>() {
                @Override
                protected void updateItem(String category, boolean empty) {
                    super.updateItem(category, empty);
                    if (empty || category == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText("📚 " + category.toUpperCase());
                        setStyle("-fx-text-fill: #FF0000 !important; -fx-font-size: 20px !important; -fx-font-weight: bold !important; -fx-padding: 15px !important; -fx-background-color: #FFFF00 !important; -fx-border-color: #000000 !important; -fx-border-width: 2px !important;");
                        System.out.println("*** ULTRA VISIBLE BOOK CATEGORY: 📚 " + category.toUpperCase());
                    }
                }
            };
        });
        
        // Format the boolean value to display as text
        bookAvailableColumn.setCellFactory(column -> {
            return new TableCell<Book, Boolean>() {
                @Override
                protected void updateItem(Boolean available, boolean empty) {
                    super.updateItem(available, empty);
                    if (empty || available == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        String statusText = available ? "✅ AVAILABLE" : "❌ NOT AVAILABLE";
                        setText(statusText);
                        setStyle("-fx-text-fill: #FF0000 !important; -fx-font-size: 20px !important; -fx-font-weight: bold !important; -fx-padding: 15px !important; -fx-background-color: #FFFF00 !important; -fx-border-color: #000000 !important; -fx-border-width: 2px !important;");
                        System.out.println("*** ULTRA VISIBLE BOOK AVAILABILITY: " + statusText);
                    }
                }
            };
        });
        
        booksTable.setItems(booksList);
        
        // Modern table row styling
        booksTable.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setPrefHeight(80); // Much taller rows for ultra-visible text
            return row;
        });
        
        System.out.println("Books table initialized with " + booksList.size() + " items");
        
        // Debug: Print first few books to verify data
        for (int i = 0; i < Math.min(3, booksList.size()); i++) {
            Book book = booksList.get(i);
            System.out.println("Book " + i + ": ID=" + book.getId() + ", Title='" + book.getTitle() + "', Author='" + book.getAuthor() + "', Available=" + book.isAvailable());
        }
        
        // Force table to refresh and show data
        booksTable.refresh();
        booksTable.getColumns().get(0).setVisible(false);
        booksTable.getColumns().get(0).setVisible(true);
        
        // Force table refresh
        booksTable.refresh();
        
        // Add context menu for books table
        ContextMenu bookContextMenu = new ContextMenu();
        MenuItem editBook = new MenuItem("Edit");
        MenuItem deleteBook = new MenuItem("Delete");
        editBook.setOnAction(e -> editSelectedBook());
        deleteBook.setOnAction(e -> deleteSelectedBook());
        bookContextMenu.getItems().addAll(editBook, deleteBook);
        booksTable.setContextMenu(bookContextMenu);
    }
    
    private void initializeMembersTable() {
        // FIRST: Set up the data binding with PropertyValueFactory
        memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        memberNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        memberEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        memberPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        memberActiveColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
        
        // THEN: Add explicit cell factory for ALL member columns with maximum visibility
        memberIdColumn.setCellFactory(column -> {
            return new TableCell<Member, Integer>() {
                @Override
                protected void updateItem(Integer id, boolean empty) {
                    super.updateItem(id, empty);
                    if (empty || id == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText("🆔 " + String.valueOf(id));
                        setStyle("-fx-text-fill: #FF0000 !important; -fx-font-size: 20px !important; -fx-font-weight: bold !important; -fx-padding: 15px !important; -fx-background-color: #FFFF00 !important; -fx-border-color: #000000 !important; -fx-border-width: 2px !important;");
                        System.out.println("*** ULTRA VISIBLE MEMBER ID: 🆔 " + String.valueOf(id));
                    }
                }
            };
        });
        
        memberNameColumn.setCellFactory(column -> {
            return new TableCell<Member, String>() {
                @Override
                protected void updateItem(String name, boolean empty) {
                    super.updateItem(name, empty);
                    if (empty || name == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText("👤 " + name.toUpperCase());
                        setStyle("-fx-text-fill: #FF0000 !important; -fx-font-size: 20px !important; -fx-font-weight: bold !important; -fx-padding: 15px !important; -fx-background-color: #FFFF00 !important; -fx-border-color: #000000 !important; -fx-border-width: 2px !important;");
                        System.out.println("*** ULTRA VISIBLE MEMBER NAME: 👤 " + name.toUpperCase());
                    }
                }
            };
        });
        
        memberEmailColumn.setCellFactory(column -> {
            return new TableCell<Member, String>() {
                @Override
                protected void updateItem(String email, boolean empty) {
                    super.updateItem(email, empty);
                    if (empty || email == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText("📧 " + email.toUpperCase());
                        setStyle("-fx-text-fill: #FF0000 !important; -fx-font-size: 20px !important; -fx-font-weight: bold !important; -fx-padding: 15px !important; -fx-background-color: #FFFF00 !important; -fx-border-color: #000000 !important; -fx-border-width: 2px !important;");
                        System.out.println("*** ULTRA VISIBLE MEMBER EMAIL: 📧 " + email.toUpperCase());
                    }
                }
            };
        });
        
        memberPhoneColumn.setCellFactory(column -> {
            return new TableCell<Member, String>() {
                @Override
                protected void updateItem(String phone, boolean empty) {
                    super.updateItem(phone, empty);
                    if (empty || phone == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText("📞 " + phone.toUpperCase());
                        setStyle("-fx-text-fill: #FF0000 !important; -fx-font-size: 20px !important; -fx-font-weight: bold !important; -fx-padding: 15px !important; -fx-background-color: #FFFF00 !important; -fx-border-color: #000000 !important; -fx-border-width: 2px !important;");
                        System.out.println("*** ULTRA VISIBLE MEMBER PHONE: 📞 " + phone.toUpperCase());
                    }
                }
            };
        });
        
        // Format the boolean value to display as text
        memberActiveColumn.setCellFactory(column -> {
            return new TableCell<Member, Boolean>() {
                @Override
                protected void updateItem(Boolean active, boolean empty) {
                    super.updateItem(active, empty);
                    if (empty || active == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        String statusText = active ? "✅ ACTIVE" : "❌ INACTIVE";
                        setText(statusText);
                        setStyle("-fx-text-fill: #FF0000 !important; -fx-font-size: 20px !important; -fx-font-weight: bold !important; -fx-padding: 15px !important; -fx-background-color: #FFFF00 !important; -fx-border-color: #000000 !important; -fx-border-width: 2px !important;");
                        System.out.println("*** ULTRA VISIBLE MEMBER STATUS: " + statusText);
                    }
                }
            };
        });
        
        membersTable.setItems(membersList);
        
        // Modern table row styling
        membersTable.setRowFactory(tv -> {
            TableRow<Member> row = new TableRow<>();
            row.setPrefHeight(80); // Much taller rows for ultra-visible text
            return row;
        });
        
        System.out.println("Members table initialized with " + membersList.size() + " items");
        
        // Add context menu for members table
        ContextMenu memberContextMenu = new ContextMenu();
        MenuItem editMember = new MenuItem("Edit");
        MenuItem deleteMember = new MenuItem("Delete");
        editMember.setOnAction(e -> editSelectedMember());
        deleteMember.setOnAction(e -> deleteSelectedMember());
        memberContextMenu.getItems().addAll(editMember, deleteMember);
        membersTable.setContextMenu(memberContextMenu);
    }
    
    private void initializeTransactionsTable() {
        transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        transactionBookColumn.setCellValueFactory(cellData -> {
            Book book = cellData.getValue().getBook();
            return new javafx.beans.property.SimpleStringProperty(book != null ? book.getTitle() : "");
        });
        transactionMemberColumn.setCellValueFactory(cellData -> {
            Member member = cellData.getValue().getMember();
            return new javafx.beans.property.SimpleStringProperty(member != null ? member.getName() : "");
        });
        transactionBorrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        transactionDueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        transactionReturnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        transactionFineColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));
        
        transactionsTable.setItems(transactionsList);
        
        // Add context menu for transactions table
        ContextMenu transactionContextMenu = new ContextMenu();
        MenuItem returnBook = new MenuItem("Return Book");
        returnBook.setOnAction(e -> returnSelectedBook());
        transactionContextMenu.getItems().add(returnBook);
        transactionsTable.setContextMenu(transactionContextMenu);
    }
    
    private void setupSearchFunctionality() {
        bookSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                loadBooks();
            } else {
                searchBooks(newValue);
            }
        });
        
        memberSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                loadMembers();
            } else {
                searchMembers(newValue);
            }
        });
    }
    
    private void loadBooks() {
        booksList.clear();
        List<Book> books = bookDAO.getAllBooks();
        System.out.println("Loading books: found " + books.size() + " books in database");
        for (Book book : books) {
            System.out.println("  - " + book.getTitle() + " by " + book.getAuthor());
        }
        booksList.addAll(books);
        System.out.println("BooksList now has " + booksList.size() + " items");
        updateBorrowBookComboBox();
    }
    
    private void loadMembers() {
        membersList.clear();
        List<Member> members = memberDAO.getAllMembers();
        System.out.println("Loading members: found " + members.size() + " members in database");
        membersList.addAll(members);
        updateBorrowMemberComboBox();
    }
    
    private void loadTransactions() {
        transactionsList.clear();
        transactionsList.addAll(transactionDAO.getAllTransactions());
    }
    
    private void updateBorrowBookComboBox() {
        borrowBookComboBox.setItems(FXCollections.observableArrayList(bookDAO.getAvailableBooks()));
        borrowBookComboBox.setConverter(new javafx.util.StringConverter<Book>() {
            @Override
            public String toString(Book book) {
                return book != null ? book.getTitle() + " by " + book.getAuthor() : "";
            }
            
            @Override
            public Book fromString(String string) {
                return null;
            }
        });
    }
    
    private void updateBorrowMemberComboBox() {
        borrowMemberComboBox.setItems(FXCollections.observableArrayList(memberDAO.getActiveMembers()));
        borrowMemberComboBox.setConverter(new javafx.util.StringConverter<Member>() {
            @Override
            public String toString(Member member) {
                return member != null ? member.getName() : "";
            }
            
            @Override
            public Member fromString(String string) {
                return null;
            }
        });
    }
    
    private void updateOverdueCount() {
        int overdueCount = transactionDAO.getOverdueBooks().size();
        overdueCountLabel.setText("Overdue Books: " + overdueCount);
        if (overdueCount > 0) {
            overdueCountLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        } else {
            overdueCountLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        }
    }
    
    // Book operations
    @FXML
    private void addBook() {
        if (validateBookFields()) {
            Book book = new Book(
                bookTitleField.getText().trim(),
                bookAuthorField.getText().trim(),
                bookIsbnField.getText().trim(),
                bookCategoryField.getText().trim()
            );
            
            if (bookDAO.save(book)) {
                clearBookFields();
                loadBooks();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Book added successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add book!");
            }
        }
    }
    
    private void editSelectedBook() {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            bookTitleField.setText(selectedBook.getTitle());
            bookAuthorField.setText(selectedBook.getAuthor());
            bookIsbnField.setText(selectedBook.getIsbn());
            bookCategoryField.setText(selectedBook.getCategory());
        }
    }
    
    private void deleteSelectedBook() {
        Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Delete");
            confirmAlert.setHeaderText("Delete Book");
            confirmAlert.setContentText("Are you sure you want to delete this book?");
            
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (bookDAO.deleteBook(selectedBook.getId())) {
                    loadBooks();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Book deleted successfully!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete book!");
                }
            }
        }
    }
    
    private void searchBooks(String searchTerm) {
        booksList.clear();
        booksList.addAll(bookDAO.searchBooks(searchTerm));
    }
    
    // Member operations
    @FXML
    private void addMember() {
        if (validateMemberFields()) {
            Member member = new Member(
                memberNameField.getText().trim(),
                memberEmailField.getText().trim(),
                memberPhoneField.getText().trim(),
                memberAddressField.getText().trim()
            );
            
            if (memberDAO.save(member)) {
                clearMemberFields();
                loadMembers();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Member added successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add member!");
            }
        }
    }
    
    private void editSelectedMember() {
        Member selectedMember = membersTable.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            memberNameField.setText(selectedMember.getName());
            memberEmailField.setText(selectedMember.getEmail());
            memberPhoneField.setText(selectedMember.getPhone());
            memberAddressField.setText(selectedMember.getAddress());
        }
    }
    
    private void deleteSelectedMember() {
        Member selectedMember = membersTable.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Delete");
            confirmAlert.setHeaderText("Delete Member");
            confirmAlert.setContentText("Are you sure you want to delete this member?");
            
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (memberDAO.deleteMember(selectedMember.getId())) {
                    loadMembers();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Member deleted successfully!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete member!");
                }
            }
        }
    }
    
    private void searchMembers(String searchTerm) {
        membersList.clear();
        membersList.addAll(memberDAO.searchMembers(searchTerm));
    }
    
    // Transaction operations
    @FXML
    private void borrowBook() {
        Book selectedBook = borrowBookComboBox.getSelectionModel().getSelectedItem();
        Member selectedMember = borrowMemberComboBox.getSelectionModel().getSelectedItem();
        
        if (selectedBook == null || selectedMember == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select both a book and a member!");
            return;
        }
        
        Transaction transaction = new Transaction(selectedBook.getId(), selectedMember.getId());
        
        if (transactionDAO.borrowBook(transaction)) {
            borrowBookComboBox.getSelectionModel().clearSelection();
            borrowMemberComboBox.getSelectionModel().clearSelection();
            loadBooks();
            loadTransactions();
            updateBorrowBookComboBox();
            updateOverdueCount();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Book borrowed successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to borrow book!");
        }
    }
    
    private void returnSelectedBook() {
        Transaction selectedTransaction = transactionsTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null && selectedTransaction.getReturnDate() == null) {
            double fine = selectedTransaction.calculateFine();
            
            if (transactionDAO.returnBook(selectedTransaction.getId(), fine)) {
                loadBooks();
                loadTransactions();
                updateBorrowBookComboBox();
                updateOverdueCount();
                
                String message = "Book returned successfully!";
                if (fine > 0) {
                    message += String.format("\nFine: $%.2f", fine);
                }
                showAlert(Alert.AlertType.INFORMATION, "Success", message);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to return book!");
            }
        }
    }
    
    @FXML
    private void showOverdueBooks() {
        transactionsList.clear();
        transactionsList.addAll(transactionDAO.getOverdueBooks());
    }
    
    @FXML
    private void showAllTransactions() {
        loadTransactions();
    }
    
    // Utility methods
    private boolean validateBookFields() {
        if (bookTitleField.getText().trim().isEmpty() ||
            bookAuthorField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please fill in all required fields!");
            return false;
        }
        return true;
    }
    
    private boolean validateMemberFields() {
        if (memberNameField.getText().trim().isEmpty() ||
            memberEmailField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please fill in all required fields!");
            return false;
        }
        return true;
    }
    
    private void clearBookFields() {
        bookTitleField.clear();
        bookAuthorField.clear();
        bookIsbnField.clear();
        bookCategoryField.clear();
    }
    
    private void clearMemberFields() {
        memberNameField.clear();
        memberEmailField.clear();
        memberPhoneField.clear();
        memberAddressField.clear();
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
