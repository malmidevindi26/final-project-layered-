module lk.ijse.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires java.desktop;
    requires mysql.connector.j;
    requires java.mail;
    requires net.sf.jasperreports.core;


    opens lk.ijse.project.layered.controller to javafx.fxml;
    opens lk.ijse.project.layered.dto.tm to javafx.base;
    exports lk.ijse.project.layered;
}