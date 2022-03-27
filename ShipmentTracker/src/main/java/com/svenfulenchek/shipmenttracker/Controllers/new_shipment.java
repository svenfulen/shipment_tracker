package com.svenfulenchek.shipmenttracker.Controllers;

import com.svenfulenchek.shipmenttracker.Database.Repository;
import com.svenfulenchek.shipmenttracker.Database.database_operation;
import com.svenfulenchek.shipmenttracker.Models.Carrier;
import com.svenfulenchek.shipmenttracker.Models.Shipment;
import com.svenfulenchek.shipmenttracker.Models.Supplier;
import com.svenfulenchek.shipmenttracker.Utils.ui_popups;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Array;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public class new_shipment {
    @FXML private TextField tracking_number_text_field;
    @FXML private ChoiceBox<Carrier> carrier_choice_box;
    @FXML private Button remove_item_button;
    @FXML private TextField add_item_text_field;
    @FXML private Button add_item_button;
    @FXML private ChoiceBox<Supplier> supplier_choice_box;
    @FXML private DatePicker date_picker;
    @FXML private TextField time_text_field;
    @FXML private TextField trailer_text_field;
    @FXML private TextArea notes_text_area;
    @FXML private ListView<String> item_list_view;

    public void initialize(){
        carrier_choice_box.getItems().setAll(database_operation.getAllCarriers());
        supplier_choice_box.getItems().setAll(database_operation.getAllSuppliers());
    }

    public boolean formIsCorrect(String tracking_number, String expected_date, String time, String trailer_number, String notes){
        boolean success = true;
        String message = "Please ensure that the form is correct.";

        if(tracking_number.length() > 255) {
            message = "Please enter a tracking number that is under 255 characters.";
            success = false;
            ui_popups.errorMessage(message);
        }

        if(expected_date.isBlank() || expected_date.isEmpty()){
            message = "Please enter the date that this shipment is expected to arrive.";
            success = false;
            ui_popups.errorMessage(message);
        }

        if(time_text_field.getText().isBlank() || time_text_field.getText().isEmpty()){
            message = "Please enter the time that this shipment is expected to arrive.";
            success = false;
            ui_popups.errorMessage(message);
        }

        if(!Pattern.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", time_text_field.getText())){
            message = "Please enter the time that this shipment is expected to arrive in the 24h format HH:MM";
            success = false;
            ui_popups.errorMessage(message);
        }

        if(trailer_number.length() > 255) {
            message = "Please enter a trailer number that is under 255 characters.";
            success = false;
            ui_popups.errorMessage(message);
        }

        if(notes.length() > 255) {
            message = "Please enter notes under 255 characters.";
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
        if (carrier_choice_box.getSelectionModel().getSelectedItem() == null){
            ui_popups.errorMessage("You must select a Carrier for this shipment.");
            canSubmit = false;
        }
        if (supplier_choice_box.getSelectionModel().getSelectedItem() == null){
            ui_popups.errorMessage("You must select a Supplier for this shipment.");
            canSubmit = false;
        }
        if (date_picker.getValue() == null){
            ui_popups.errorMessage("You must enter the expected date for this shipment.");
            canSubmit = false;
        }
        if (time_text_field.getText() == null){
            ui_popups.errorMessage("You must enter the expected time for this shipment.");
        }
        if (canSubmit) {
            if (formIsCorrect(tracking_number_text_field.getText(), date_picker.getValue().toString(), time_text_field.getText(), trailer_text_field.getText(), notes_text_area.getText())) {
                int carrier_id = carrier_choice_box.getSelectionModel().getSelectedItem().getId();
                int supplier_id = supplier_choice_box.getSelectionModel().getSelectedItem().getId();

                // Create an array from the items in the list.
                String[] list_items = item_list_view.getItems().toArray(new String[item_list_view.getItems().size()]);
                Array list_items_array = Repository.connection.createArrayOf("text", list_items);

                // Create a timestamp from the expected date and time fields.
                String dateTimeExpected = date_picker.getValue().toString() + " " + time_text_field.getText() + ":00";
                DateTimeFormatter time_format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.from(time_format.parse(dateTimeExpected));
                Timestamp expected_timestamp = Timestamp.valueOf(localDateTime);

                // Create a new Shipment object.
                Shipment newShipment = new Shipment(Repository.current_user_id, supplier_id, carrier_id, list_items_array, tracking_number_text_field.getText(), notes_text_area.getText(), trailer_text_field.getText(), expected_timestamp);

                // Add the shipment to the database.
                database_operation.addShipment(newShipment);

                Stage new_shipment_stage = (Stage) supplier_choice_box.getScene().getWindow();
                new_shipment_stage.close();
            }
        }
    }

}
