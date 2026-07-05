package com.ssrmt.ui;

import com.ssrmt.modules.Student;
import com.ssrmt.services.StudentRecordManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Optional;

import com.ssrmt.modules.Student;
import com.ssrmt.services.StudentRecordManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Optional;

public class DashboardController {

    private final StudentRecordManager manager = new StudentRecordManager();
    private ObservableList<Student> tableData;

    // FXML UI Bindings
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtProgram;
    @FXML
    private TextField txtGpa;
    @FXML
    private ComboBox<Integer> cmbLevel;

    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> colId;
    @FXML
    private TableColumn<Student, String> colName;
    @FXML
    private TableColumn<Student, String> colEmail;
    @FXML
    private TableColumn<Student, String> colProgram;
    @FXML
    private TableColumn<Student, Integer> colLevel;
    @FXML
    private TableColumn<Student, Double> colGpa;

    /**
     * Automatically called by JavaFX framework after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        // Initialize table data collection and bind it to the TableView
        tableData = FXCollections.observableArrayList();
        studentTable.setItems(tableData);

        // Define how data is populated into columns
        colId.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId()));
        colName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        colEmail.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEmail()));
        colProgram.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getProgram()));

        // Fix: Use SimpleObjectProperty for non-String values to avoid type mismatch
        colLevel.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getLevel()));
        colGpa.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getGpa()));

        // Select first item of combo box by default
        cmbLevel.getSelectionModel().selectFirst();

        // Load initial records from the manager
        refreshTableData();
    }

    @FXML
    private void handleSaveStudent() {
        try {
            // Input Validations
            if (txtId.getText().trim().isEmpty() || txtName.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("ID and Name fields cannot be blank.");
            }
            if (cmbLevel.getValue() == null) {
                throw new IllegalArgumentException("Please select a student Level.");
            }

            double gpa = Double.parseDouble(txtGpa.getText().trim());
            int level = cmbLevel.getValue();

            // Object Creation matching backend framework rules
            Student student = new Student(
                    txtId.getText().trim(),
                    txtName.getText().trim(),
                    txtEmail.getText().trim(),
                    txtProgram.getText().trim(),
                    level,
                    gpa
            );

            manager.addStudent(student);
            refreshTableData();
            clearInputFields();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Student record saved successfully!");
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid Format: GPA must be a valid numerical decimal.");
        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", ex.getMessage());
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Application Error", ex.getMessage());
        }
    }

    @FXML
    private void handleDeleteStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Required", "Please select a student row from the table to delete.");
            return;
        }

        String studentId = selectedStudent.getId();
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Drop");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to remove student " + studentId + "?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            manager.deleteStudent(studentId);
            refreshTableData();
            showAlert(Alert.AlertType.INFORMATION, "Status Update", "Record successfully purged.");
        }
    }

    private void refreshTableData() {
        tableData.clear();
        tableData.addAll(manager.getAllStudents());
    }

    private void clearInputFields() {
        txtId.clear();
        txtName.clear();
        txtEmail.clear();
        txtProgram.clear();
        txtGpa.clear();
        cmbLevel.getSelectionModel().selectFirst();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}