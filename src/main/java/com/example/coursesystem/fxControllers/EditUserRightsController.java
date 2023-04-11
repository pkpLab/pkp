package com.example.coursesystem.fxControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EditUserRightsController {

    @FXML
    private Button btnGrantRights;

    @FXML
    private Button btnRevokeRights;

    @FXML
    private TableColumn<?, ?> colCompanyName;

    @FXML
    private TableColumn<?, ?> colHasRights;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSurname;

    @FXML
    private Label lblCourseName;

    @FXML
    private TableView<?> tblUserRights;

    @FXML
    void grantRights(ActionEvent event) {

    }

    @FXML
    void revokeRights(ActionEvent event) {

    }

}
