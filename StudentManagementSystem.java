package CODSOFT;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.Objects;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

class Student{
    String name,rollNo;
    int grade;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", rollNo='" + rollNo + '\'' +
                ", grade=" + grade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Student student)) return false;
        return getGrade() == student.getGrade() && Objects.equals(getName(), student.getName()) && Objects.equals(getRollNo(), student.getRollNo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getRollNo(), getGrade());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Student(int grade, String rollNo, String name) {
        this.grade = grade;
        this.rollNo = rollNo;
        this.name = name;
    }

}

public class StudentManagementSystem extends Application {

    private static final ObservableList<Student> students = FXCollections.observableArrayList();

    private static final String FILE_NAME = "src/CODSOFT/students.txt";


    private static void writeToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_NAME))) {
            for (Student s : students) {
                String line = s.getRollNo() + "," + s.getName() + "," + s.getGrade();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "File Error", "Could not write to file.");
        }
    }


    private static void readFromFile() {
        students.clear();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String rollNo = parts[0].trim();
                    String name = parts[1].trim();
                    int grade = Integer.parseInt(parts[2].trim());
                    students.add(new Student(grade, rollNo, name));
                }
            }
        } catch (FileNotFoundException e) {
            // if no file,it will be created
        } catch (IOException | NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "File Error", "Could not read from file.");
        }
    }


    @Override
    public void start(Stage primaryStage) {
        // Create TableView
        TableView<Student> tableView = new TableView<>(students);
        readFromFile();

        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<Student, String> rollNoCol = new TableColumn<>("Roll No");
        rollNoCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getRollNo()));

        TableColumn<Student, Integer> gradeCol = new TableColumn<>("Grade");
        gradeCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getGrade()).asObject());

        tableView.getColumns().addAll(nameCol, rollNoCol, gradeCol);

        // Input fields
        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField rollNoField = new TextField();
        rollNoField.setPromptText("Roll No");

        TextField gradeField = new TextField();
        gradeField.setPromptText("Grade");

        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button searchButton = new Button("Search");
        Button removeButton = new Button("Remove");
        Button displayButton = new Button("Display All");
        Button exitButton = new Button("Exit");



        // TableView colons

        addButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String rollNo = rollNoField.getText().trim();
            String gradeText = gradeField.getText().trim();

            if (name.isEmpty() || rollNo.isEmpty() || gradeText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Entry", "All fields are required.");
                return;
            }

            int grade;
            try {
                grade = Integer.parseInt(gradeText);
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Entry", "Grade must be an integer.");
                return;
            }

            students.add(new Student(grade, rollNo, name));
            tableView.refresh();
            nameField.clear();
            rollNoField.clear();
            gradeField.clear();
            writeToFile();
        });

        editButton.setOnAction(e -> {
            Student selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a student to edit.");
                return;
            }

            String newName = nameField.getText().trim();
            String newRollNo = rollNoField.getText().trim();
            String gradeText = gradeField.getText().trim();

            if (newName.isEmpty() && newRollNo.isEmpty() && gradeText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Entry", "Fields are required.");
                return;
            }

            int newGrade=selected.getGrade();
            try {
                if(!gradeText.isEmpty())
                    newGrade = Integer.parseInt(gradeText);
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Entry", "Grade must be an integer.");
                return;
            }
            if (!newName.isEmpty())
                selected.setName(newName);
            if (!newRollNo.isEmpty())
                selected.setRollNo(newRollNo);
            selected.setGrade(newGrade);
            tableView.refresh();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Student updated successfully.");
            nameField.clear();
            rollNoField.clear();
            gradeField.clear();
            writeToFile();
        });

        searchButton.setOnAction(e -> {
            String rollNo = rollNoField.getText().trim();
            if (rollNo.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Entry", "Please enter Roll No to search.");
                return;
            }

            boolean found = students.stream().anyMatch(s -> s.getRollNo().equals(rollNo));
            if (found) {
                showAlert(Alert.AlertType.INFORMATION, "Found", "Student with Roll No " + rollNo + " exists.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Not Found", "No student with Roll No " + rollNo);
            }
        });

        removeButton.setOnAction(e->{
            String rollNo = rollNoField.getText().trim();
            if (rollNo.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Entry", "Please enter Roll No to remove.");
                return;
            }

            boolean removed = students.removeIf(s -> s.getRollNo().equals(rollNo));
            if (removed) {
                showAlert(Alert.AlertType.INFORMATION, "Removed", "Student with Roll No " + rollNo + " removed.");

            } else {
                showAlert(Alert.AlertType.WARNING, "Not Found", "No student with Roll No " + rollNo);
            }
            writeToFile();
        });






        displayButton.setOnAction(e -> {
            tableView.refresh(); // TableView is already displaying
        });

        exitButton.setOnAction(e -> {
            // terminate app
            System.exit(0);
        });

        HBox buttonBox = new HBox(10, addButton, editButton, searchButton,removeButton, displayButton, exitButton);
        VBox vbox = new VBox(10, nameField, rollNoField, gradeField, buttonBox, tableView);
        vbox.setPadding(new Insets(10));


        Scene scene = new Scene(vbox, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Student Management System");
        primaryStage.show();
    }

    private static void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

