module com.mks {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive java.sql;
    requires transitive javafx.graphics;
    requires javafx.base;

    opens com.mks to javafx.fxml;
    exports com.mks;
}
