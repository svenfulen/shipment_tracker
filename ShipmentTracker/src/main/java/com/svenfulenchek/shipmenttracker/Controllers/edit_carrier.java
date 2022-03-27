package com.svenfulenchek.shipmenttracker.Controllers;

import com.svenfulenchek.shipmenttracker.Database.database_operation;
import com.svenfulenchek.shipmenttracker.Models.Carrier;
import com.svenfulenchek.shipmenttracker.Utils.ui_popups;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class edit_carrier {
    public static Carrier selected_carrier;

    @FXML private TextField name_field;
    @FXML private TextField phone_field;
    @FXML private TextField website_field;
    @FXML private TextField email_field;
    @FXML private TextField address_field;
    @FXML private TextArea notes_text_area;

    public void initialize(){
        name_field.setText(selected_carrier.getName());
        phone_field.setText(selected_carrier.getPhone());
        website_field.setText(selected_carrier.getWebsite());
        email_field.setText(selected_carrier.getEmail());
        address_field.setText(selected_carrier.getAddress());
        notes_text_area.setText(selected_carrier.getNotes());
    }

    public boolean formIsCorrect(String name, String phone, String website, String email, String address, String notes){
        // This message changes if something goes wrong with validation.
        String message = "The form is correct.";
        // This boolean changes to false if something goes wrong with validation.
        boolean success = true;

        // Check that the carrier name has been entered.
        if (name.equals("")) {
            message = "Please enter a carrier name.";
            success = false;
            ui_popups.errorMessage(message);
        }

        // Check that all fields are less than 255 characters.
        if (name.length() > 255) {
            message = "Please enter less than 255 characters for the carrier name.";
            success = false;
            ui_popups.errorMessage(message);
        }
        if (phone.length() > 255) {
            message = "Please enter less than 255 characters for the phone number.";
            success = false;
            ui_popups.errorMessage(message);
        }
        if (website.length() > 255) {
            message = "Please enter less than 255 characters for the website.";
            success = false;
            ui_popups.errorMessage(message);
        }
        if (email.length() > 255) {
            message = "Please enter less than 255 characters for the email.";
            success = false;
            ui_popups.errorMessage(message);
        }
        if (address.length() > 255) {
            message = "Please enter less than 255 characters for the address.";
            success = false;
            ui_popups.errorMessage(message);
        }
        if (notes.length() > 255) {
            message = "Please enter less than 255 characters for the notes.";
            success = false;
            ui_popups.errorMessage(message);
        }

        return success;
    }

    public void onDelete() throws Exception {
        database_operation.deleteCarrierById(selected_carrier.getId());
        Stage stage = (Stage) name_field.getScene().getWindow();
        stage.close();
    }

    public void onSubmit() throws Exception {
        int id = selected_carrier.getId();
        String name = name_field.getText();
        String phone = phone_field.getText();
        String website = website_field.getText();
        String email = email_field.getText();
        String address = address_field.getText();
        String notes = notes_text_area.getText();

        if (formIsCorrect(name, phone, website, email, address, notes)) {
            Carrier carrier = new Carrier(id, name, address, email, phone, website, notes);
            database_operation.updateCarrier(carrier);
            Stage stage = (Stage) name_field.getScene().getWindow();
            stage.close();
        }

    }
}
