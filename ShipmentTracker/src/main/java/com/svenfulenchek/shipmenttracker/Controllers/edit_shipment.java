package com.svenfulenchek.shipmenttracker.Controllers;

import com.svenfulenchek.shipmenttracker.Database.Repository;
import com.svenfulenchek.shipmenttracker.Database.database_operation;
import com.svenfulenchek.shipmenttracker.Models.Carrier;
import com.svenfulenchek.shipmenttracker.Models.Shipment;
import com.svenfulenchek.shipmenttracker.Models.Supplier;
import com.svenfulenchek.shipmenttracker.Utils.ui_popups;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Array;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Pattern;

public class edit_shipment {
    public static Shipment selected_shipment;

    @FXML private TextField tracking_number_text_field;
    @FXML private ChoiceBox<Carrier> carrier_choice_box;
    @FXML private Button remove_item_button;
    @FXML private TextField add_item_text_field;
    @FXML private Button add_item_button;
    @FXML private ChoiceBox<Supplier> supplier_choice_box;
    @FXML private DatePicker expected_date_picker;
    @FXML private TextField expected_time_text_field;
    @FXML private DatePicker received_date_picker;
    @FXML private TextField received_time_text_field;
    @FXML private TextField trailer_text_field;
    @FXML private TextArea notes_text_area;
    @FXML private ListView<String> item_list_view;
    @FXML private CheckBox received_check_box;

    public void initialize() throws Exception {
        carrier_choice_box.getItems().setAll(database_operation.getAllCarriers());
        supplier_choice_box.getItems().setAll(database_operation.getAllSuppliers());
        tracking_number_text_field.setText(selected_shipment.getTracking_number());
        carrier_choice_box.setValue(database_operation.getCarrierById(selected_shipment.getCarrier_id()));
        supplier_choice_box.setValue(database_operation.getSupplierById(selected_shipment.getSupplier_id()));
        expected_date_picker.setValue(selected_shipment.getExpected_date().toLocalDateTime().toLocalDate());
        expected_time_text_field.setText(selected_shipment.getExpected_date().toLocalDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm")));
        trailer_text_field.setText(selected_shipment.getTrailer_number());
        notes_text_area.setText(selected_shipment.getNotes());
        item_list_view.getItems().setAll(selected_shipment.getObservableItemList());
        if (selected_shipment.getReceived_date() != null){
            received_check_box.setSelected(true);
        }
    }

    public void toggleReceivedEdit(){
        if (received_check_box.isSelected()) {
            // Allow the user to edit the received date and time if the "received" box is checked
            received_date_picker.setEditable(true);
            received_time_text_field.setEditable(true);
        }
        else {
            // Clear the received date/time and disable editing it
            received_date_picker.setEditable(false);
            received_time_text_field.setEditable(false);
            received_date_picker.setValue(null);
            received_time_text_field.setText("");
        }
    }

    public void onCancel(){
        Stage edit_shipment_stage = (Stage) supplier_choice_box.getScene().getWindow();
        edit_shipment_stage.close();
    }

    public void onDeleteShipment(){
        if(ui_popups.confirmPrompt("The record of this shipment will be lost forever.  Are you sure you want to delete it?")){
            database_operation.deleteShipmentById(selected_shipment.getId());
            onCancel();
        }
    }

    /**
     * Check that all form fields are entered correctly.
     * @return true if all fields are entered correctly
     */
    public boolean formIsCorrect(){
        boolean success = true;
        String message = "Please ensure that the form is correct.";

        // Check that the tracking number is under 255 characters
        if(tracking_number_text_field.getText().length() > 255) {
            message = "Please enter a tracking number that is under 255 characters.";
            success = false;
            ui_popups.errorMessage(message);
        }

        // Check that the trailer number is under 255 characters
        if(trailer_text_field.getText().length() > 255) {
            message = "Please enter a trailer number that is under 255 characters.";
            success = false;
            ui_popups.errorMessage(message);
        }

        // Check that the notes are under 255 characters
        if(notes_text_area.getText().length() > 255) {
            message = "Please enter notes under 255 characters.";
            success = false;
            ui_popups.errorMessage(message);
        }

        // Check that the expected date is entered and not blank
        if(expected_date_picker.getValue().toString().isBlank()) {
            message = "Please enter the date that this shipment is expected to arrive.";
            success = false;
            ui_popups.errorMessage(message);
        }

        // Check that the expected time is entered and not blank
        if(expected_time_text_field.getText().isBlank()){
            message = "Please enter the time that this shipment is expected to arrive.";
            success = false;
            ui_popups.errorMessage(message);
        }
        // Check that the expected arrival time is in the correct format
        else{
            if(!Pattern.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", expected_time_text_field.getText())){
                message = "Please enter the time that this shipment is expected to arrive in the 24h format HH:MM";
                success = false;
                ui_popups.errorMessage(message);
            }
        }

        // Check if this shipment has been received
        if(received_check_box.isSelected()) {
            // Check that the received date is entered and not blank
            if (received_date_picker.getValue().toString().isBlank()) {
                message = "Please enter the date that this shipment arrived.";
                success = false;
                ui_popups.errorMessage(message);
            }
            // Check that the received time is in the correct format.
            if (!Pattern.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", received_time_text_field.getText())) {
                message = "Please enter the time that this shipment arrived in the 24h format HH:MM";
                success = false;
                ui_popups.errorMessage(message);
            }
        }

        // Check that a Carrier is selected
        if (carrier_choice_box.getSelectionModel().getSelectedItem() == null){
            message = "You must select a Carrier for this shipment.";
            success = false;
            ui_popups.errorMessage(message);
        }

        // Check that a Supplier is selected
        if (supplier_choice_box.getSelectionModel().getSelectedItem() == null){
            message = "You must select a Supplier for this shipment.";
            success = false;
            ui_popups.errorMessage(message);
        }

        return success;
    }

    public void addListItem(){
        // Check that the field is not empty so that clicking the button doesn't just make empty list items.
        if(!Objects.equals(add_item_text_field.getText(), "")) {
            // Check that there are no special characters in the list items so that the sql array doesn't get corrupted.
            if(add_item_text_field.getText().contains("\"") || add_item_text_field.getText().contains("'") || add_item_text_field.getText().contains("$") || add_item_text_field.getText().contains("{") || add_item_text_field.getText().contains("}") ){
                ui_popups.errorMessage("You cannot use special characters when adding list items.");
            }
            else {
                item_list_view.getItems().add(add_item_text_field.getText());
                add_item_text_field.setText("");
            }
        }
    }

    public void removeListItem(){
        // Make sure that an item is selected that can be deleted.
        if(item_list_view.getSelectionModel().getSelectedItem() != null){
            item_list_view.getItems().remove(item_list_view.getSelectionModel().getSelectedIndex());
        }
    }

    public void submit() throws Exception {
        boolean canSubmit = true;
        // Validation

        if (formIsCorrect()) {
            int carrier_id = carrier_choice_box.getSelectionModel().getSelectedItem().getId();
            int supplier_id = supplier_choice_box.getSelectionModel().getSelectedItem().getId();

            // Create an array from the items in the list.
            String[] list_items = item_list_view.getItems().toArray(new String[item_list_view.getItems().size()]);
            Array list_items_array = Repository.connection.createArrayOf("text", list_items);

            // Create a timestamp from the expected date and time fields.
            String dateTimeExpected = expected_date_picker.getValue().toString() + " " + expected_time_text_field.getText() + ":00";
            DateTimeFormatter time_format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.from(time_format.parse(dateTimeExpected));
            Timestamp expected_timestamp = Timestamp.valueOf(localDateTime);

            // Create a new Shipment object.
            Shipment newShipment = new Shipment(Repository.current_user_id, supplier_id, carrier_id, list_items_array, tracking_number_text_field.getText(), notes_text_area.getText(), trailer_text_field.getText(), expected_timestamp);

            // Set received information.
            if (received_check_box.isSelected()){
                // Create a timestamp from the received date and time fields.
                String dateTimeReceived = received_date_picker.getValue().toString() + " " + received_time_text_field.getText() + ":00";
                LocalDateTime receivedLocalDateTime = LocalDateTime.from(time_format.parse(dateTimeReceived));
                Timestamp received_timestamp = Timestamp.valueOf(receivedLocalDateTime);
                newShipment.setReceived_date(received_timestamp);
            }
            else {
                newShipment.setReceived(false);
                newShipment.setReceived_date(null);
            }
            // Add the shipment to the database.
            database_operation.updateShipment(newShipment);
            view_shipment.selected_shipment = newShipment;

            onCancel();
        }
    }

}
