package com.svenfulenchek.shipmenttracker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainApplicationController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}