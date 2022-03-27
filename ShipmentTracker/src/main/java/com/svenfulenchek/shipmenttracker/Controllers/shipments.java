package com.svenfulenchek.shipmenttracker.Controllers;

import com.svenfulenchek.shipmenttracker.Database.Repository;
import com.svenfulenchek.shipmenttracker.Database.database_operation;
import com.svenfulenchek.shipmenttracker.MainApplication;
import com.svenfulenchek.shipmenttracker.Models.Shipment;
import com.svenfulenchek.shipmenttracker.Utils.ui_popups;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.Time;
import java.sql.Timestamp;

public class shipments {

    @FXML private TableView<Shipment> shipments_table;
    // table
    //each column
    @FXML private TableColumn<Shipment, Integer> shipment_id_col;
    @FXML private TableColumn<Shipment, String> supplier_col;
    @FXML private TableColumn<Shipment, String> trailer_col;
    @FXML private TableColumn<Shipment, String> carrier_col;
    @FXML private TableColumn<Shipment, String> tracking_col;
    @FXML private TableColumn<Shipment, String> notes_col;
    @FXML private TableColumn<Shipment, Timestamp> expected_col;

    public void initialize() throws Exception {
        shipments_table.setPlaceholder(new Label("There are currently no inbound shipments."));
        populateShipments(database_operation.getInboundShipments());
    }
    /**
     * Show all inbound shipments on the main screen.
     * This method can be called to refresh the main screen.
     */
    public void populateShipments(ObservableList<Shipment> inbound_shipments) {
        if (inbound_shipments != null){
            shipment_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            supplier_col.setCellValueFactory(new PropertyValueFactory<>("supplier_name"));
            trailer_col.setCellValueFactory(new PropertyValueFactory<>("trailer_number"));
            carrier_col.setCellValueFactory(new PropertyValueFactory<>("carrier_name"));
            tracking_col.setCellValueFactory(new PropertyValueFactory<>("tracking_number"));
            notes_col.setCellValueFactory(new PropertyValueFactory<>("notes"));
            expected_col.setCellValueFactory(new PropertyValueFactory<>("expected_date"));
            shipments_table.getItems().setAll(inbound_shipments);
        }
    }

    /**
     * Called when the user clicks the New -> Shipment menu button
     * @throws Exception any exception
     */
    public void onNewShipment() throws Exception {
        Parent root = FXMLLoader.load(MainApplication.class.getResource("new_shipment.fxml"));
        Stage newShipmentStage = new Stage();
        newShipmentStage.setTitle("New Shipment");
        newShipmentStage.setScene(new Scene(root, 770, 570));
        newShipmentStage.setResizable(false);
        newShipmentStage.getIcons().add(Repository.app_icon);
        newShipmentStage.show();
        newShipmentStage.setOnHiding( event -> populateShipments(database_operation.getInboundShipments()) );
    }

    /**
     * Called when the user clicks the Lookup -> Carriers menu button
     * @throws Exception any exception
     */
    public void onCarriers() throws Exception {
        Parent root = FXMLLoader.load(MainApplication.class.getResource("carrier_list.fxml"));
        Stage carrierListStage = new Stage();
        carrierListStage.setTitle("Carriers");
        carrierListStage.setScene(new Scene(root, 600, 440));
        carrierListStage.setResizable(false);
        carrierListStage.getIcons().add(Repository.app_icon);
        carrierListStage.show();
        carrierListStage.setOnHiding( event -> populateShipments(database_operation.getInboundShipments()) );;
    }

    /**
     * Called when the user clicks the Lookup -> Suppliers menu button
     * @throws Exception any exception
     */
    public void onSuppliers() throws Exception {
        Parent root = FXMLLoader.load(MainApplication.class.getResource("supplier_list.fxml"));
        Stage supplierListStage = new Stage();
        supplierListStage.setTitle("Suppliers");
        supplierListStage.setScene(new Scene(root, 600, 440));
        supplierListStage.setResizable(false);
        supplierListStage.getIcons().add(Repository.app_icon);
        supplierListStage.show();
        supplierListStage.setOnHiding( event -> populateShipments(database_operation.getInboundShipments()) );
    }

    /**
     * If a Shipment is selected in the table, this function will open a detailed view of that shipment
     * @throws Exception any exception
     */
    public void onView() throws Exception {
        if(shipments_table.getSelectionModel().getSelectedItem() != null) {
            view_shipment.selected_shipment = shipments_table.getSelectionModel().getSelectedItem();
            Parent root = FXMLLoader.load(MainApplication.class.getResource("view_shipment.fxml"));
            Stage viewShipmentStage = new Stage();
            viewShipmentStage.setTitle("View Shipment");
            viewShipmentStage.setScene(new Scene(root, 566, 455));
            viewShipmentStage.setResizable(false);
            viewShipmentStage.getIcons().add(Repository.app_icon);
            viewShipmentStage.show();
            viewShipmentStage.setOnHiding( event -> populateShipments(database_operation.getInboundShipments()) );
        }
    }

    public void onReceive() throws Exception {
        if(shipments_table.getSelectionModel().getSelectedItem() != null) {
            Shipment to_receive = shipments_table.getSelectionModel().getSelectedItem();
            // Confirm with the user that the shipment should be received.
            if(ui_popups.confirmPrompt("Receive shipment #" + to_receive.getId() + " ?")) {
                // Receive the shipment in the database.
                if(database_operation.receiveShipmentById(to_receive.getId())) {
                    ui_popups.infoMessage("Successfully received shipment #" + to_receive.getId());
                    populateShipments(database_operation.getInboundShipments());
                }
                else {
                    ui_popups.errorMessage("Error while receiving Shipment.  Please check your internet connection.");
                }
            }
        }
    }

    public void onLookupShipment() throws Exception {
        Parent root = FXMLLoader.load(MainApplication.class.getResource("lookup_shipment.fxml"));
        Stage lookupShipmentStage = new Stage();
        lookupShipmentStage.setTitle("Lookup Shipment");
        lookupShipmentStage.setScene(new Scene(root, 600, 425));
        lookupShipmentStage.setResizable(false);
        lookupShipmentStage.getIcons().add(Repository.app_icon);
        lookupShipmentStage.show();
        lookupShipmentStage.setOnHiding( event -> populateShipments(database_operation.getInboundShipments()) );;
    }

    public void onTrailerHistory() throws Exception {
        Parent root = FXMLLoader.load(MainApplication.class.getResource("trailer_history.fxml"));
        Stage trailerHistoryStage = new Stage();
        trailerHistoryStage.setTitle("Trailer History");
        trailerHistoryStage.setScene(new Scene(root, 520, 400));
        trailerHistoryStage.setResizable(false);
        trailerHistoryStage.getIcons().add(Repository.app_icon);
        trailerHistoryStage.show();
    }
}
