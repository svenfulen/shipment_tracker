package com.svenfulenchek.shipmenttracker.Controllers;

import com.svenfulenchek.shipmenttracker.Database.database_operation;
import com.svenfulenchek.shipmenttracker.Models.Shipment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;

public class trailer_history {
    ObservableList<Shipment> found_shipments;

    @FXML private TextField search_field;
    @FXML private TableView<Shipment> shipments_table;
    @FXML private TableColumn<Shipment, Integer> shipment_id_col;
    @FXML private TableColumn<Shipment, String> carrier_col;
    @FXML private TableColumn<Shipment, String> tracking_col;
    @FXML private TableColumn<Shipment, Timestamp> date_col;

    public void initialize(){
        shipments_table.setPlaceholder(new Label("No results found"));
    }

    public void populateShipmentsFound(ObservableList<Shipment> found_shipments) {
        if (found_shipments != null) {
            shipment_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            carrier_col.setCellValueFactory(new PropertyValueFactory<>("carrier_name"));
            tracking_col.setCellValueFactory(new PropertyValueFactory<>("tracking_number"));
            date_col.setCellValueFactory(new PropertyValueFactory<>("expected_date"));
            shipments_table.getItems().setAll(found_shipments);
        }
    }

    public void onSearch() throws Exception {
        found_shipments = FXCollections.observableArrayList();
        // Check that something is entered in the search field.
        if(!search_field.getText().isBlank()) {
            String search_text = search_field.getText();
            found_shipments = database_operation.trailerHistory(search_text);
            populateShipmentsFound(found_shipments);
        }
    }

}
