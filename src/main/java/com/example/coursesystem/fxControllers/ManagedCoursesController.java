package com.example.coursesystem.fxControllers;

import com.example.coursesystem.appClasses.CourseTree;
import com.example.coursesystem.appClasses.CurrentUser;
import com.example.coursesystem.appClasses.Database;
import com.example.coursesystem.dataStructures.Course;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import java.sql.SQLException;

public class ManagedCoursesController {
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



}
