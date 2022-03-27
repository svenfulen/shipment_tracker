package com.svenfulenchek.shipmenttracker.Controllers;

import com.svenfulenchek.shipmenttracker.Database.Repository;
import com.svenfulenchek.shipmenttracker.Database.database_operation;
import com.svenfulenchek.shipmenttracker.MainApplication;
import com.svenfulenchek.shipmenttracker.Models.Shipment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class view_shipment {
    public static Shipment selected_shipment;

    @FXML private Label shipment_number_label;
    @FXML private Label trailer_number_label;
    @FXML private Label tracking_number_label;
    @FXML private Label carrier_label;
    @FXML private Label supplier_label;
    @FXML private Label expected_label;
    @FXML private Label received_label;
    @FXML private TextArea notes_text_area;
    @FXML private ListView<String> list_items;

    public void initialize() throws Exception {
        shipment_number_label.setText("Shipment #" + selected_shipment.getId());
        trailer_number_label.setText("Trailer #" + selected_shipment.getTrailer_number());
        tracking_number_label.setText("Tracking #" + selected_shipment.getTracking_number());
        supplier_label.setText("Supplier: " + database_operation.getSupplierNameById(selected_shipment.getSupplier_id()));
        carrier_label.setText("Carrier: " + database_operation.getCarrierNameById(selected_shipment.getCarrier_id()));
        expected_label.setText("Expected: " + selected_shipment.getExpected_date());
        if (selected_shipment.received){
            if (selected_shipment.getReceived_date() != null) {
                received_label.setText(selected_shipment.getReceived_date().toString());
            }
        }
        notes_text_area.setText(selected_shipment.getNotes());
        list_items.getItems().setAll(selected_shipment.getObservableItemList());
    }

    public void onEdit() throws Exception {
        edit_shipment.selected_shipment = selected_shipment;
        Parent root = FXMLLoader.load(MainApplication.class.getResource("edit_shipment.fxml"));
        Stage editShipmentStage = new Stage();
        editShipmentStage.setTitle("Edit Shipment");
        editShipmentStage.setScene(new Scene(root, 770, 570));
        editShipmentStage.setResizable(false);
        editShipmentStage.getIcons().add(Repository.app_icon);
        editShipmentStage.show();
        editShipmentStage.setOnHiding( event -> {
            Stage thisStage = (Stage) shipment_number_label.getScene().getWindow();
            thisStage.close();
        });
    }


}
