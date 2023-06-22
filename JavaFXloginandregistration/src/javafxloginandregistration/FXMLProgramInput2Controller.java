/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxloginandregistration;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Gg's pc
 */
public class FXMLProgramInput2Controller implements Initializable {
    @FXML
    private TextField program;

    @FXML
    private TextField programid;

    @FXML 
    private Button save;
    private Button clear;

    public void savebuttonaction(ActionEvent event) throws SQLException {
        String dburl = "jdbc:mysql://localhost:3306/results";
        String dbuser = "root";
        String dbpass = "2396";
        String sql = "INSERT INTO programme (prog_name, prog_id) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(dburl, dbuser, dbpass);
             PreparedStatement statement = con.prepareStatement(sql)) {
            String programme = program.getText();
            String progid = programid.getText();
            statement.setString(1, programme);
            statement.setString(2, progid);
            statement.executeUpdate();
        }
    }
     public void clearbuttonaction(ActionEvent event) {
        program.clear();
        programid.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization code here
    }    
}
