package com.example.coursesystem.appClasses;

import com.example.coursesystem.dataStructures.Course;
import com.example.coursesystem.fxControllers.EditUserRightsController;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditUserRightsControllerDataTest extends ApplicationTest {

    private EditUserRightsController editUserRightsController;
    private Course testCourse;

    @BeforeEach
    public void setUp() {
        editUserRightsController = new EditUserRightsController();
        testCourse = new Course();
        testCourse.setCourse_id(1);
        testCourse.setCourseName("Test Course");
        testCourse.setDescription("A test course");
    }

    @Test
    public void initDataSetsCourseNameLabel() {
        // Set up mock UI components
        editUserRightsController.lblCourseName = new Label();

        // Call initData() with the test course
        editUserRightsController.initDataTest(testCourse);

        // Check if the label is set correctly
        assertEquals("Test Course", editUserRightsController.lblCourseName.getText());
    }
}
