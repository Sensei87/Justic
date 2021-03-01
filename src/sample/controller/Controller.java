package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.animations.BadShake;
import sample.configs.DatabaseHandler;
import sample.entitys.User;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_fild;

    @FXML
    private PasswordField password_fild;

    @FXML
    private Button authSinButton;

    @FXML
    private Button lognSingUpButton;

    @FXML
    void initialize() {
        authSinButton.setOnAction(event -> {
            String loginText = login_fild.getText().trim();
            String loginPassword = password_fild.getText().trim();
            if(!loginText.equals("") && !loginPassword.equals("")) {
                loginUser(loginText, loginPassword);
            } else {
                System.out.println("Login or password is empty");
            }
        });

        lognSingUpButton.setOnAction(event -> {
            openScene("/sample/xml/singUp.fxml");
        });
    }

    // Авторизировасться из базы данных
    private void loginUser(String loginText, String loginPassword) {

        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(loginPassword);
        ResultSet resultSet = dbHandler.getUser(user);

        int counter = 0;
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            counter++;
        }
        if(counter >= 1) {
            openScene("/sample/xml/calculatinTable.fxml");

        } else {
            BadShake userLoginAnimation = new BadShake(login_fild);
            BadShake userPassAnimation = new BadShake(password_fild);
            userLoginAnimation.playAnimation();
            userPassAnimation.playAnimation();
        }
    }

    // Открыть новое окно сцену
    public void openScene(String window) {
        lognSingUpButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}

