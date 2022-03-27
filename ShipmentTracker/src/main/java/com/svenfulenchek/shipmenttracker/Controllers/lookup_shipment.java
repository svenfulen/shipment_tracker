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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Timestamp;

public class lookup_shipment {
    ObservableList<Shipment> found_shipments;

    @FXML private ChoiceBox<String> choice_box;
    @FXML private TextField search_field;
    @FXML private Button search_button;
    @FXML private TableView<Shipment> shipments_table;
    @FXML private TableColumn<Shipment, Integer> shipment_id_col;
    @FXML private TableColumn<Shipment, String> supplier_col;
    @FXML private TableColumn<Shipment, String> trailer_col;
    @FXML private TableColumn<Shipment, String> carrier_col;
    @FXML private TableColumn<Shipment, String> tracking_col;
    @FXML private TableColumn<Shipment, Timestamp> received_col;
    @FXML private TableColumn<Shipment, Timestamp> expected_col;

    public void initialize(){
        shipments_table.setPlaceholder(new Label("No results found"));
        choice_box.getItems().add("Shipment ID");
        choice_box.getItems().add("Tracking Number");
        choice_box.getItems().add("List Item");
        choice_box.getItems().add("Notes");
    }

    public void populateShipmentsFound(ObservableList<Shipment> found_shipments) {
        if (found_shipments != null){
            shipment_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            supplier_col.setCellValueFactory(new PropertyValueFactory<>("supplier_name"));
            carrier_col.setCellValueFactory(new PropertyValueFactory<>("carrier_name"));
            trailer_col.setCellValueFactory(new PropertyValueFactory<>("trailer_number"));
            tracking_col.setCellValueFactory(new PropertyValueFactory<>("tracking_number"));
            expected_col.setCellValueFactory(new PropertyValueFactory<>("expected_date"));
            received_col.setCellValueFactory(new PropertyValueFactory<>("received_date"));
            shipments_table.getItems().setAll(found_shipments);
        }
    }

    public void onSearch() throws Exception {
        found_shipments = FXCollections.observableArrayList();
        // Check that something is entered in the search field.
        if(!search_field.getText().isBlank()) {
            String search_text = search_field.getText();
            // Check that something is selected in the choice box.
            if (choice_box.getSelectionModel().getSelectedItem() != null) {
                switch (choice_box.getSelectionModel().getSelectedItem()) {
                    case "Shipment ID":
                        found_shipments = database_operation.searchShipmentsById(search_text);
                        break;
                    case "Tracking Number":
                        found_shipments = database_operation.searchShipmentsByTrackingNumber(search_text);
                        break;
                    case "List Item":
                        found_shipments = database_operation.searchShipmentsByListItem(search_text);
                        break;
                    case "Notes":
                        found_shipments = database_operation.searchShipmentsByNotes(search_text);
                        break;
                    default:
                }
                if (found_shipments != null) {
                    populateShipmentsFound(found_shipments);
                }
            }
        }
    }

    public void onView() throws Exception{
        if (shipments_table.getSelectionModel().getSelectedItem() != null) {
            view_shipment.selected_shipment = shipments_table.getSelectionModel().getSelectedItem();
            Parent root = FXMLLoader.load(MainApplication.class.getResource("view_shipment.fxml"));
            Stage viewShipmentStage = new Stage();
            viewShipmentStage.setTitle("View Shipment");
            viewShipmentStage.setScene(new Scene(root, 566, 455));
            viewShipmentStage.setResizable(false);
            viewShipmentStage.getIcons().add(Repository.app_icon);
            viewShipmentStage.show();
            viewShipmentStage.setOnHiding( event -> shipments_table.getItems().clear() );
        }
    }
}
