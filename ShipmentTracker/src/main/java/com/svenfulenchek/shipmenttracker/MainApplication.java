package com.svenfulenchek.shipmenttracker;

import com.svenfulenchek.shipmenttracker.Database.Repository;
import com.svenfulenchek.shipmenttracker.Database.database_connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        Scene login_scene = new Scene(fxmlLoader.load(), 300, 220);
        stage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("icon.png")));
        stage.setTitle("Shipment Tracker Login");
        stage.setScene(login_scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        Repository.connection = database_connection.connect();
        Repository.statement = Repository.connection.createStatement();
        launch();

    }
}