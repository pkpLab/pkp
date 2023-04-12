package com.example.coursesystem.fxControllers;

import com.example.coursesystem.appClasses.*;
import com.example.coursesystem.dataStructures.Course;
import com.example.coursesystem.dataStructures.File;
import com.example.coursesystem.dataStructures.Folder;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EnrolledCoursesController implements Initializable {
    @FXML
    public TreeTableView twEnrolledCourses;
    @FXML
    public TreeTableColumn colMyCourse;
    @FXML
    public Label lblSelectedCourse;
    @FXML
    public TextField txtUsername;
    @FXML
    public TextField txtCourseLenght;
    @FXML
    public TextArea txtDescription;
    @FXML
    public TextField txtTotalSize;

    private CourseTree enrolledCourses;
    private Course selectedCourse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadEnrolledCoursesTab();
    }

    private void loadEnrolledCoursesTab() {
        enrolledCourses = new CourseTree(twEnrolledCourses);
        try {
            enrolledCourses.loadTree(Database.getInstance().getEnrolledCourses(CurrentUser.getUserId()));
        } catch (SQLException e) {
            Messages.showErrorMessage("Error", "Error in database");
            e.printStackTrace();
        }
        colMyCourse.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures, ObservableValue>) param -> ((TreeDisplayItem) param.getValue().getValue()).nameProperty());
        twEnrolledCourses.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            TreeItem<TreeDisplayItem> selectedItem = (TreeItem<TreeDisplayItem>) newValue;
            if (selectedItem != null) {
                if (enrolledCourses.getTreeItemHashtable().get(selectedItem) instanceof Course course) {
                    selectedCourse = course;
                    lblSelectedCourse.setText(course.getCourseName());
                    txtDescription.setText(course.getDescription());
                    txtUsername.setText(course.getUsername());
                    txtCourseLenght.setText(course.getCourseLength());
                }
            }
        });
    }

    public void refreshEnrolledCourses(ActionEvent actionEvent) {
        enrolledCourses.clearTree();
        try {
            enrolledCourses.loadTree(Database.getInstance().getEnrolledCourses(CurrentUser.getUserId()));
        } catch (SQLException e) {
            Messages.showErrorMessage("Error", "Error in database");
            e.printStackTrace();
        }
    }

    public void leaveCourse(ActionEvent actionEvent) {
        if (selectedCourse != null) {
            if (Messages.showConfirmationDialog("Confirm", "Are you sure you want to leave course \"" + selectedCourse.getCourseName() + "\"?")) {
                try {
                    Database.getInstance().removeUserFromCourse(CurrentUser.getUserId(), selectedCourse.getCourse_id());
                    Messages.showInfoMessage("Success", "Successfully left course: " + selectedCourse.getCourseName());
                    refreshEnrolledCourses(null);
                    lblSelectedCourse.setText("");
                    txtDescription.setText("");
                } catch (SQLException e) {
                    Messages.showErrorMessage("Error", "Error in database");
                    e.printStackTrace();
                }
            }
        }
    }

}
