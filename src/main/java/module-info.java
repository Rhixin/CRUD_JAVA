module com.example.csit228_f1_v2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.csit228_f1_v2 to javafx.fxml;
    exports com.example.csit228_f1_v2;
    exports com.example.csit228_f1_v2.HELPERS;
    opens com.example.csit228_f1_v2.HELPERS to javafx.fxml;
}