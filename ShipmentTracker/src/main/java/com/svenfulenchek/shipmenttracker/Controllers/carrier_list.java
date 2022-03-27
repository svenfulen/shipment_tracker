package com.svenfulenchek.shipmenttracker.Controllers;

import com.svenfulenchek.shipmenttracker.Database.Repository;
import com.svenfulenchek.shipmenttracker.Database.database_operation;
import com.svenfulenchek.shipmenttracker.MainApplication;
import com.svenfulenchek.shipmenttracker.Models.Carrier;
import com.svenfulenchek.shipmenttracker.Models.Shipment;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class carrier_list {
    @FXML private ListView<Carrier> carrier_list_view;
    @FXML private TextField name_field;
    @FXML private TextField phone_field;
    @FXML private TextField website_field;
    @FXML private TextField email_field;
    @FXML private TextField address_field;
    @FXML private TextArea notes_text_area;
    @FXML private TableView<Shipment> inbound_shipments_table;
    @FXML private TableColumn<Shipment, Integer> shipment_id_col;
    @FXML private TableColumn<Shipment, String> trailer_col;
    @FXML private TableColumn<Shipment, String> tracking_col;

    public void initialize(){
        populateCarriersList();

        // Fill the form whenever a Carrier is selected from the list.
        carrier_list_view.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Carrier>() {
            @Override
            public void changed(ObservableValue<? extends Carrier> observable, Carrier unSelectedCarrier, Carrier selectedCarrier) {
                if (selectedCarrier != null) {
                    name_field.setText(selectedCarrier.getName());
                    phone_field.setText(selectedCarrier.getPhone());
                    website_field.setText(selectedCarrier.getWebsite());
                    email_field.setText(selectedCarrier.getEmail());
                    address_field.setText(selectedCarrier.getAddress());
                    notes_text_area.setText(selectedCarrier.getNotes());
                    inbound_shipments_table.setPlaceholder(new Label("This carrier has no inbound shipments."));
                    shipment_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
                    trailer_col.setCellValueFactory(new PropertyValueFactory<>("trailer_number"));
                    tracking_col.setCellValueFactory(new PropertyValueFactory<>("tracking_number"));
                    inbound_shipments_table.getItems().setAll(database_operation.getInboundShipmentsByCarrierId(selectedCarrier.getId()));
                }
            }
        });
    }

    /**
     * Clear all text fields and table in the view
     */
    public void clearFields(){
        name_field.setText("");
        phone_field.setText("");
        website_field.setText("");
        email_field.setText("");
        address_field.setText("");
        notes_text_area.setText("");
        inbound_shipments_table.getItems().clear();
    }

    /**
     * Populates the Carriers list.
     */
    public void populateCarriersList(){
        ObservableList<Carrier> allCarriers = database_operation.getAllCarriers();
        carrier_list_view.getItems().setAll(allCarriers);
        clearFields();
    }


    /**
     * Opens the New Carrier window that allows the user to add a Carrier
     */
    public void onNewCarrier() throws IOException {
        Parent root = FXMLLoader.load(MainApplication.class.getResource("new_carrier.fxml"));
        Stage newCarrierStage = new Stage();
        newCarrierStage.setTitle("New Carrier");
        newCarrierStage.setScene(new Scene(root, 350, 300));
        newCarrierStage.setResizable(false);
        newCarrierStage.getIcons().add(Repository.app_icon);
        newCarrierStage.show();
        newCarrierStage.setOnHiding( event -> populateCarriersList() );
    }

    /**
     *
     */
    public void onEditCarrier() throws IOException {
        if (carrier_list_view.getSelectionModel().getSelectedItem() != null) {
            edit_carrier.selected_carrier = carrier_list_view.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(MainApplication.class.getResource("edit_carrier.fxml"));
            Stage editCarrierStage = new Stage();
            editCarrierStage.setTitle("Edit Carrier");
            editCarrierStage.setScene(new Scene(root, 350, 300));
            editCarrierStage.setResizable(false);
            editCarrierStage.getIcons().add(Repository.app_icon);
            editCarrierStage.show();
            editCarrierStage.setOnHiding(event -> populateCarriersList());
        }
    }

}
