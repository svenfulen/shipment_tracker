package com.svenfulenchek.shipmenttracker.Controllers;

import com.svenfulenchek.shipmenttracker.Database.Repository;
import com.svenfulenchek.shipmenttracker.MainApplication;
import com.svenfulenchek.shipmenttracker.Utils.ui_popups;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.Statement;

public class login {
    @FXML private Button login_button;
    @FXML private PasswordField password_field;
    @FXML private TextField username_field;

    public void initialize(){

    }

    /**
     * Close the Login window and open the main application.
     * This is called upon successful login
     * @throws Exception JavaFX exception
     */
    public void loginSuccess() throws Exception {
        // Open the main page (shipments view)
        Parent root = FXMLLoader.load(MainApplication.class.getResource("shipments_view.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Shipment Tracker");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("icon.png")));
        primaryStage.show();

        // Close the login window.
        Stage login_stage = (Stage) login_button.getScene().getWindow();
        login_stage.close();
    }

    /**
     * Display an error message if the login fails.
     * @throws Exception JavaFX exception
     */
    public void loginFailure() throws Exception {
        ui_popups.errorMessage("Incorrect username or password.");
    }

    /**
     * Use the username and password entered and attempt to log in.
     * Display an error message if login fails.
     * @throws Exception JavaFX Exception
     */
    public void submitLoginForm() throws Exception {
        String username = username_field.getText();
        String password = password_field.getText();

        // set up database connection
        Repository.connection.setAutoCommit(false);
        Statement statement = Repository.connection.createStatement();

        // This will always only return 1 result or 0 results because each username is unique.
        ResultSet query_results = statement.executeQuery("SELECT id FROM auth.user WHERE username = \'" + username + "\'" + " AND password = \'" + password + "\';");

        while(query_results.next()){
            // If there is a result, log the user in and store the username in memory.
            Repository.current_user_id = query_results.getInt("id");
            loginSuccess();
        }

        query_results.close();
        statement.close();

    }


}
