package com.example.coursesystem.fxControllers;

import com.example.coursesystem.appClasses.Database;
import com.example.coursesystem.dataStructures.Course;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasChild;
import static org.testfx.matcher.control.TableViewMatchers.containsRow;
import static org.testfx.util.NodeQueryUtils.hasText;

@ExtendWith(ApplicationExtension.class)
public class EditUserRightsControllerTest {

    private EditUserRightsController controller;

    @Start
    public void start(Stage stage) throws SQLException {
        // Mock the Database instance
        Database mockDatabase = Mockito.mock(Database.class);

        // Set up the controller and scene
        controller = new EditUserRightsController();
        controller.setDatabase(mockDatabase);
        Scene scene = new Scene(controller, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void testInitData() {
        Course course = new Course(1, "Test Course", "A test course");
        controller.initData(course);

        verifyThat("#lblCourseName", hasText("Test Course"));
    }

    @Test
    public void testRevokeRights() {
        // Prepare the test data
        Course course = new Course(1, "Test Course", "A test course");
        UserDisplayItem user = new UserDisplayItem(1, "John", "Doe", "ACME Inc.", 1);
        controller.initData(course);
        controller.usersObservable.add(user);
        controller.selectedUser = user;

        // Simulate revoke rights action
        controller.revokeRights(null);

        // Verify that the revoke rights logic is executed
        Mockito.verify(controller.getDatabase()).deleteUserPrivileges(user.getUserId(), course.getCourse_id());
        assertEquals(0, user.getHasRights());
    }

    @Test
    public void testGrantRights() {
        // Prepare the test data
        Course course = new Course(1, "Test Course", "A test course");
        UserDisplayItem user = new UserDisplayItem(1, "John", "Doe", "ACME Inc.", 0);
        controller.initData(course);
        controller.usersObservable.add(user);
        controller.selectedUser = user;

        // Simulate grant rights action
        controller.grantRights(null);

        // Verify that the grant rights logic is executed
        Mockito.verify(controller.getDatabase()).setUserPrivileges(user.getUserId(), course.getCourse_id());
        assertEquals(1, user.getHasRights());
    }

}
