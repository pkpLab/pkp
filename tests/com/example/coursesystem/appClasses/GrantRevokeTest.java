package com.example.coursesystem.fxControllers;

import com.example.coursesystem.appClasses.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
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
    }

    @Test
    public void testRevokeRights() throws SQLException {
        when(editUserRightsController.tblUserRights.getSelectionModel().getSelectedItem()).thenReturn(selectedUser);
        when(database.deleteUserPrivileges(selectedUser.getUserId(), currentCourse.getCourse_id())).thenReturn(true);

        editUserRightsController.revokeRights(Mockito.mock(ActionEvent.class));

        verify(database, times(1)).deleteUserPrivileges(selectedUser.getUserId(), currentCourse.getCourse_id());
        verify(editUserRightsController.tblUserRights, times(1)).getSelectionModel();
        verify(editUserRightsController.btnRevokeRights, times(1)).setDisable(true);
        verify(editUserRightsController.btnGrantRights, times(1)).setDisable(false);
    }

    @Test
    public void testGrantRights() throws SQLException {
        when(editUserRightsController.tblUserRights.getSelectionModel().getSelectedItem()).thenReturn(selectedUser);
        when(database.setUserPrivileges(selectedUser.getUserId(), currentCourse.getCourse_id())).thenReturn(true);

        editUserRightsController.grantRights(Mockito.mock(ActionEvent.class));

        verify(database, times(1)).setUserPrivileges(selectedUser.getUserId(), currentCourse.getCourse_id());
        verify(editUserRightsController.tblUserRights, times(1)).getSelectionModel();
        verify(editUserRightsController.btnRevokeRights, times(1)).setDisable(false);
        verify(editUserRightsController.btnGrantRights, times(1)).setDisable(true);
    }
}
