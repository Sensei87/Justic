package sample.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.configs.DatabaseHandler;
import sample.entitys.User;

public class SingUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField singUpLastname;

    @FXML
    private PasswordField password_fild;

    @FXML
    private Button authSinButton;

    @FXML
    private TextField singUpName;

    @FXML
    private TextField login_fild2;

    @FXML
    void initialize() {
        authSinButton.setOnAction(event -> {
            singUpNewUser();
            openMainScene("/sample/xml/sample.fxml");
        });

    }

    // Зарегистрировать нового пользователя
    private void singUpNewUser() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        String firstName = singUpName.getText();
        String lastName = singUpLastname.getText();
        String login = login_fild2.getText();
        String password = password_fild.getText();

        User user = new User(firstName, lastName, login, password);

        dbHandler.singUpUser(user);

    }
    public void openMainScene(String window) {
        authSinButton.getScene().getWindow().hide();
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
        stage.show();

    }
}

