/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package javafxloginandregistration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Gg's pc
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button register;
    @FXML
    private Button login;
    @FXML
    private TextField username; // Assuming you have a TextField for username
    @FXML
    private PasswordField password; // Assuming you have a PasswordField for password

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent pane = FXMLLoader.load(getClass().getResource("FXMLRegistration.fxml"));

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleButtonLogin(ActionEvent event) {
        String user = username.getText();
        String pass = password.getText();

        String dburl = "jdbc:mysql://LocalHost:3306/results";
        String dbuser = "root";
        String dbpass = "";

        try ( Connection con = DriverManager.getConnection(dburl, dbuser, dbpass)) {
            String sql = "SELECT password FROM login WHERE username = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, user);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                if (pass.equals(storedPassword)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("SUCCESS");
                    alert.setContentText("LOGIN SUCCESSFUL");
                    alert.showAndWait();

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLStudentDetails.fxml"));
                        Parent pane = loader.load();
                        // You can access the controller of FXMLStudentDetails.fxml by calling the getController() method on the loader
                        FXMLStudentDetailsController detailsController = loader.getController();
                        // You can pass any necessary data to the FXMLStudentDetailsController using its public methods/variables
                        // For example:
                        // detailsController.setUserData(user);

                        Scene scene = new Scene(pane);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("group 13");
                        stage.show();

                        // Close the current login window
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("UNSUCCESSFUL");
                    alert.setContentText("LOGIN UNSUCCESSFUL");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("UNSUCCESSFUL");
                alert.setContentText("LOGIN UNSUCCESSFUL");
                alert.showAndWait();
            }

        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
