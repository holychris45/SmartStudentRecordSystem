module com.example.ssrmt {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.ssrmt to javafx.fxml;
    opens com.ssrmt.ui to javafx.fxml;
    exports com.example.ssrmt;
}