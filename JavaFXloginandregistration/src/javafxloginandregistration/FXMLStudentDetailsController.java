package javafxloginandregistration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;


public class FXMLStudentDetailsController implements Initializable {

    @FXML
    private TextField regno;

    @FXML
    private TextField fname;

    @FXML
    private TextField lname;

    @FXML
    private ComboBox<String> programme;

    @FXML
    private ComboBox<String> members;

    @FXML
    private TextField contact;

    @FXML
    private RadioButton male;

    @FXML
    private RadioButton female;

    private ToggleGroup gender;
    
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize any necessary setup here
     
        gender = new ToggleGroup();
        male.setToggleGroup(gender);
        female.setToggleGroup(gender);
        
        setupContactTextField();
        populatePrograms();
        populateMembers();
       // setupRegnoTextField();
    }

    private void populatePrograms() {
        String dburl = "jdbc:mysql://LocalHost:3306/results";
        String dbuser = "root";
        String dbpass = "";

        String sql = "SELECT prog_name FROM programme";

        try (Connection con = DriverManager.getConnection(dburl, dbuser, dbpass);
                PreparedStatement statement = con.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            ObservableList<String> programs = FXCollections.observableArrayList();

            while (resultSet.next()) {
                programs.add(resultSet.getString("prog_name"));
            }

            programme.setItems(programs);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLStudentDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void populateMembers() {
        String dburl = "jdbc:mysql://LocalHost:3306/results";
        String dbuser = "root";
        String dbpass = "";

        String sql = "SELECT regno FROM members";

        try (Connection con = DriverManager.getConnection(dburl, dbuser, dbpass);
                PreparedStatement statement = con.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            ObservableList<String> membersOL = FXCollections.observableArrayList();

            while (resultSet.next()) {
                membersOL.add(resultSet.getString("regno"));
            }

            members.setItems(membersOL);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLStudentDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void setupContactTextField() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,10}")) {
                return change;
            }
            return null;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        contact.setTextFormatter(textFormatter);
    }


// ...

   /* private void setupRegnoTextField() {
    UnaryOperator<TextFormatter.Change> filter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("([A-Z]{1,3})([/]?)([B]?)([/]?) ([0]?) ([1|2]?) ([-]?) (([0-9]?){1,5}?) ([/]?) (([0-9]?){1,4}?)")) {
            return change;
        }
        return null;
    };

    TextFormatter<String> textFormatter = new TextFormatter<>(filter);
    regno.setTextFormatter(textFormatter);
}*/



    
    
     @FXML
    private void handleButtonNewProgram(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent pane = FXMLLoader.load(getClass().getResource("FXMLProgramInput2.fxml"));

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
@FXML
  public void clearbuttonaction(ActionEvent event) {
        regno.clear();
        fname.clear();
        lname.clear();
        contact.clear();
        gender = null;
               
    }

    @FXML
    private void handleRegisterButton(ActionEvent event) {
        String dburl = "jdbc:mysql://LocalHost:3306/results";
        String dbuser = "root";
        String dbpass = "";

        String reg = regno.getText();
        String firstname = fname.getText();
        String lastname = lname.getText();
        String prog = programme.getValue().toString();
        String countyname = members.getValue().toString();
        String pnumber = contact.getText();
        String gender2 = null;
        if (male.isSelected()) {
            gender2 = "male";
        } else if (female.isSelected()) {
            gender2 = "female";
        }

        String storedProg = getProgramId(prog);
        if (storedProg == null) {
            System.out.println("No program ID found for the selected program: " + prog);
            // You can throw an exception or set a default value for storedProg
            // For example:
            // storedProg = "DEFAULT_PROGRAM_ID";
            return;
        }

        try (Connection con = DriverManager.getConnection(dburl, dbuser, dbpass)) {
            String sql = "INSERT INTO studentdetails (regno, fname, lname, prog, countyname, pnumber, gender) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, reg);
            statement.setString(2, firstname);
            statement.setString(3, lastname);
            statement.setString(4, storedProg);
            statement.setString(5, countyname);
            statement.setString(6, pnumber);
            statement.setString(7, gender2);
            
            

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Registration successful");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLStudentDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getProgramId(String prog) {
        String dburl = "jdbc:mysql://LocalHost:3306/results";
        String dbuser = "root";
        String dbpass = "";

        try (Connection con = DriverManager.getConnection(dburl, dbuser, dbpass)) {
            String sql = "SELECT prog_id FROM programme WHERE prog_name = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, prog);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("prog_id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLStudentDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
