/*
package com.example.coursesystem.fxControllers;

import com.example.coursesystem.appClasses.Course;
import com.example.coursesystem.appClasses.UserDisplayItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.sql.SQLException;
import static org.mockito.Mockito.*;

public class EditUserRightsControllerTest {

    private EditUserRightsController editUserRightsController;
    private Course currentCourse;
    private ObservableList<UserDisplayItem> usersObservable;
    private UserDisplayItem selectedUser;
    private Database database;

    @BeforeEach
    public void setUp() {
        editUserRightsController = new EditUserRightsController();
        currentCourse = new Course();
        usersObservable = FXCollections.observableArrayList();
        selectedUser = new UserDisplayItem("John", "Doe", "Company A", true, 1);
        database = mock(Database.class);

        editUserRightsController.tblUserRights = mock(TableView.class);
        editUserRightsController.lblCourseName = mock(Label.class);
        editUserRightsController.txtName = mock(TextField.class);
        editUserRightsController.txtSurname = mock(TextField.class);
        editUserRightsController.txtEmail = mock(TextField.class);
        editUserRightsController.txtCompanyName = mock(TextField.class);
        editUserRightsController.colName = mock(TableColumn.class);
        editUserRightsController.colSurname = mock(TableColumn.class);
        editUserRightsController.colCompanyName = mock(TableColumn.class);
        editUserRightsController.colHasRights = mock(TableColumn.class);
        editUserRightsController.btnRevokeRights = mock(Button.class);
        editUserRightsController.btnGrantRights = mock(Button.class);

        editUserRightsController.initData(currentCourse);
        editUserRightsController.setDatabase(database);
    }

    @Test
    public void testHandleSQLException() {
        // Mock the behavior of database method to throw SQLException
        doThrow(new SQLException("Error executing SQL statement")).when(database).updateUserRights(anyInt(), anyBoolean());

        // Mock the selection model and selected item
        TableView.TableViewSelectionModel<UserDisplayItem> selectionModel = mock(TableView.TableViewSelectionModel.class);
        when(editUserRightsController.tblUserRights.getSelectionModel()).thenReturn(selectionModel);
        when(selectionModel.selectedItemProperty()).thenReturn(mock(ObservableValue.class));
        when(selectionModel.getSelectedItem()).thenReturn(selectedUser);

        // Mock the behavior of UI components
        when(editUserRightsController.txtName.getText()).thenReturn("John");
        when(editUserRightsController.txtSurname.getText()).thenReturn("Doe");
        when(editUserRightsController.txtEmail.getText()).thenReturn("john.doe@example.com");
        when(editUserRightsController.txtCompanyName.getText()).thenReturn("Company A");
        when(editUserRightsController.btnRevokeRights.isDisable()).thenReturn(false);
        when(editUserRightsController.btnGrantRights.isDisable()).thenReturn(false);

        // Call the updateRights method that may throw SQLException
        editUserRightsController.updateRights();

        // Verify the behavior
        verify(database, times(1)).updateUserRights(anyInt(), anyBoolean());
        // Assert that the error message is displayed
        // You can use your own assertions based on your implementation
        // For example, if you have a label to display error messages, you can verify that
    }*/
