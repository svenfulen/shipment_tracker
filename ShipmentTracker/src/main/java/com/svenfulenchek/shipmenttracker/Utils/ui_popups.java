package com.svenfulenchek.shipmenttracker.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ui_popups {

    /**
     * Generates a popup window with an OK button and an error message.
     * @param errorText The error message to display.
     */
    public static void errorMessage(String errorText){
        Alert error = new Alert(Alert.AlertType.ERROR, errorText, ButtonType.OK);
        error.showAndWait();
        //if(error.getResult() == ButtonType.OK){}
    }

    /**
     * Generates a popup window with an OK button and an information message.
     * @param infoText The message to display.
     */
    public static void infoMessage(String infoText){
        Alert info = new Alert(Alert.AlertType.INFORMATION, infoText, ButtonType.OK);
        info.showAndWait();
    }

    /**
     * Generates a popup with a "Yes" and "No" button and an information message.
     * @param promptText The text to show in the prompt.
     * @return true if the user clicks "yes"
     */
    public static boolean confirmPrompt(String promptText){
        Alert prompt = new Alert(Alert.AlertType.CONFIRMATION, promptText);
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        prompt.getButtonTypes().setAll(yesButton, noButton);

        // Returns true if the "Yes" button is clicked.
        Optional<ButtonType> result = prompt.showAndWait();
        if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
            return true;
        }
        else {
            return false;
        }
    }
}
