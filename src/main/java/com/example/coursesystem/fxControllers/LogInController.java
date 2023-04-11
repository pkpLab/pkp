package com.example.coursesystem.fxControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    @FXML
    public PasswordField txtPassword;
    @FXML
    public TextField txtName;
    @FXML
    public AnchorPane AnPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtName.setFocusTraversable(false);
        txtPassword.setFocusTraversable(false);
    }
    @FXML
    public void validationForMainForm(ActionEvent actionEvent) throws IOException{
    }
    @FXML
    public void openRegisterForm(ActionEvent actionEvent) throws IOException {
    }

}