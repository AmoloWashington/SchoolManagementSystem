/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxloginandregistration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLRegistrationController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField cpassword;
    @FXML
    private Button save;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent pane = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        save.setOnAction(event -> {
            String user = username.getText();
            String pass = password.getText();
            String cpass = cpassword.getText();

            if (pass.equals(cpass)) {
                String dburl = "jdbc:mysql://LocalHost:3306/results";
                String dbuser = "root";
                String dbpass = "";
                Connection con = null;
                PreparedStatement statement;
                int x = 0;
                try {
                    con = DriverManager.getConnection(dburl, dbuser, dbpass);
                    String sql = "INSERT INTO login (username, password) VALUES (?, ?)";
                    statement = con.prepareStatement(sql);
                    statement.setString(1, user);
                    statement.setString(2, pass);
                    x = statement.executeUpdate();

                } catch (SQLException ex) {
                    Logger.getLogger(FXMLRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (con != null) {
                        try {
                            con.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                if (x > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Registration successful");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("The passwords do not match");
                alert.showAndWait();
            }
        });

    }

}
