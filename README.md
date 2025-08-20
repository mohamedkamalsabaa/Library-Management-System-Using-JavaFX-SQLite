# 📚 Library Management System

A modern Library Management System built with JavaFX and SQLite for efficient library operations.

![Java](https://img.shields.io/badge/Java-11+-blue?logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-19-orange?logo=java)
![SQLite](https://img.shields.io/badge/SQLite-3.42.0-green?logo=sqlite)
![Maven](https://img.shields.io/badge/Maven-3.9.6-red?logo=apache-maven)

## ✨ Features

- **📖 Book Management**: Add, search, edit, and delete books with real-time availability tracking
- **👥 Member Management**: Register and manage library members with contact information
- **📋 Transaction System**: Handle book borrowing, returns, and overdue tracking
- **🎨 Modern UI**: Clean JavaFX interface with sky blue theme and high-contrast display
- **📊 Dashboard**: Real-time statistics and library analytics

## 🛠️ Tech Stack

- **Frontend**: JavaFX 19 with FXML
- **Backend**: Java 11+
- **Database**: SQLite 3.42.0.0
- **Build**: Maven 3.9.6

## 🚀 Quick Start

### Prerequisites
- Java 11 or higher
- Maven (included in project)

### Run the Application

**Windows (Recommended)**:
```bash
./start.bat
```

**Alternative**:
```bash
mvn javafx:run
```

### First Launch
The app automatically creates the database and loads sample data (15 books, 10 members).

```

## � Usage

1. **Books Tab**: Manage book inventory, search, and availability
2. **Members Tab**: Register and manage library members  
3. **Transactions Tab**: Process borrowing and returns
4. **Dashboard**: View library statistics and overdue items

## 🗄️ Database Schema

**Books**: `id`, `title`, `author`, `isbn`, `category`, `available`  
**Members**: `id`, `name`, `email`, `phone`, `active`  
**Transactions**: `id`, `book_id`, `member_id`, `issue_date`, `return_date`, `status`

## 🔧 Troubleshooting

- **JavaFX issues**: Ensure Java 11+ with JavaFX support
- **Build failures**: Run `mvn clean compile` first
- **Database errors**: Check if `library.db` is writable

---

**Built using Java and JavaFX**

src/main/resources/
└── com/library/view/
    ├── main.fxml                  # UI layout
    └── styles.css                 # Styling
```

## Database

- SQLite database (`library.db`) is created automatically
- Sample data is loaded on the first run
- Tables: books, members, transactions
- Overdue book notifications
- Clean and intuitive user interface
- SQLite database for data persistence

## Technologies Used

- **Java 17** - Programming language
- **JavaFX 19** - GUI framework
- **SQLite** - Database
- **Maven** - Build tool and dependency management

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Setup and Installation

1. **Clone or download the project** to your local machine.

2. **Navigate to the project directory:**
   ```bash
   cd LMS
   ```

3. **Build the project using Maven:**
   ```bash
   mvn clean compile
   ```

4. **Run the application:**
   ```bash
   mvn javafx:run
   ```

   Alternatively, you can run directly using Maven:
   ```bash
   mvn clean javafx:run
   ```

## Database Schema

The application automatically creates the following tables:

### Books Table
- `id` (INTEGER PRIMARY KEY)
- `title` (TEXT NOT NULL)
- `author` (TEXT NOT NULL)
- `isbn` (TEXT UNIQUE)
- `category` (TEXT)
- `is_available` (BOOLEAN)
- `date_added` (DATE)

### Members Table
- `id` (INTEGER PRIMARY KEY)
- `name` (TEXT NOT NULL)
- `email` (TEXT UNIQUE)
- `phone` (TEXT)
- `address` (TEXT)
- `join_date` (DATE)
- `is_active` (BOOLEAN)

### Transactions Table
- `id` (INTEGER PRIMARY KEY)
- `book_id` (INTEGER FOREIGN KEY)
- `member_id` (INTEGER FOREIGN KEY)
- `borrow_date` (DATE)
- `due_date` (DATE)
- `return_date` (DATE)
- `type` (TEXT - BORROW/RETURN)
- `fine` (REAL)

## File Structure

```
LMS/
├── pom.xml                                    # Maven configuration
├── README.md                                  # This file
├── src/
│   └── main/
│       ├── java/
│       │   ├── module-info.java              # Java module configuration
│       │   └── com/library/
│       │       ├── App.java                  # Main application class
│       │       ├── controller/
│       │       │   └── MainController.java   # JavaFX controller
│       │       ├── dao/                      # Data Access Objects
│       │       │   ├── BookDAO.java
│       │       │   ├── MemberDAO.java
│       │       │   └── TransactionDAO.java
│       │       ├── database/
│       │       │   └── DatabaseManager.java  # Database connection manager
│       │       └── model/                    # Data models
│       │           ├── Book.java
│       │           ├── Member.java
│       │           └── Transaction.java
│       └── resources/
│           └── com/library/view/
│               └── main.fxml                 # JavaFX UI layout
└── library.db                               # SQLite database (created at runtime)
```

## Key Features Explained

### Fine Calculation
- Books have a 14-day borrowing period
- Overdue books incur a $1.00 fine per day
- Fines are automatically calculated when returning books

### Data Validation
- Required fields are validated before saving
- Email uniqueness is enforced for members
- ISBN uniqueness is enforced for books

### User Interface
- Tab-based navigation for different sections
- Table views with context menus for quick actions
- Real-time search functionality
- Responsive layout that adapts to window size

## Troubleshooting

### Common Issues

1. **JavaFX Runtime Error:**
   - Ensure you're using Java 17 or higher
   - Make sure JavaFX is properly included in the classpath

2. **Database Connection Issues:**
   - The SQLite database file will be created automatically in the project root
   - Ensure you have write permissions in the project directory

3. **Build Issues:**
   - Run `mvn clean` before building
   - Ensure Maven is properly installed and configured
