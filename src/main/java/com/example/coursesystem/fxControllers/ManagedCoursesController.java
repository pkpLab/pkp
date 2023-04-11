package com.example.coursesystem.fxControllers;

import com.example.coursesystem.appClasses.*;
import com.example.coursesystem.dataStructures.Course;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManagedCoursesController implements Initializable {
    public TreeTableView twManagedCourses;
    public TreeTableColumn colCourse;
    public TextField txtEnrolledCount;
    public TextField txtEditRightCount;
    public Button btnEditRights;
    public Button btnRemoveCourse;
    public Button btnEditCourse;

    public void editUserRights(ActionEvent actionEvent) {
    }

    public void removeCourse(ActionEvent actionEvent) {
    }

    public void editCourse(ActionEvent actionEvent) {
    }

    public void createCourse(ActionEvent actionEvent) {
    }

    public void refreshCourses(ActionEvent actionEvent) {
    }

    private CourseTree managedCourses;
    private Course selectedCourse;


    private void toggleButtons() {
        if (selectedCourse != null) {
            btnEditCourse.setDisable(false);
            try {
                if (Database.getInstance().checkIfUserIsCreator(CurrentUser.getUserId(), selectedCourse.getCourse_id())) {
                    btnEditRights.setDisable(false);
                    btnRemoveCourse.setDisable(false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            btnEditCourse.setDisable(true);
        }
    }

    public void loadManagedCourses() {
        managedCourses = new CourseTree(twManagedCourses);
        Array users = null;
        try {
            managedCourses.loadTree(Database.getInstance().getManagedCourses(CurrentUser.getUserId()));
        } catch (SQLException e) {
            Messages.showErrorMessage("Error", "Error in database");
            e.printStackTrace();
        }
        colCourse.setCellValueFactory((Callback<TreeTableColumn.CellDataFeatures, ObservableValue>) param -> ((TreeDisplayItem) param.getValue().getValue()).nameProperty());
        twManagedCourses.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            TreeItem<TreeDisplayItem> selectedItem = (TreeItem<TreeDisplayItem>) newValue;
            if (selectedItem != null) {
                if (managedCourses.getTreeItemHashtable().get(selectedItem) instanceof Course course) {
                    selectedCourse = course;
                    toggleButtons();
                }
            }
        });

    }

    public void initialize(URL location, ResourceBundle resources) {
        loadManagedCourses();
    }

}
