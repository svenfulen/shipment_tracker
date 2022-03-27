package com.svenfulenchek.shipmenttracker.Controllers;

import com.svenfulenchek.shipmenttracker.Database.Repository;
import com.svenfulenchek.shipmenttracker.Database.database_operation;
import com.svenfulenchek.shipmenttracker.MainApplication;
import com.svenfulenchek.shipmenttracker.Models.Carrier;
import com.svenfulenchek.shipmenttracker.Models.Shipment;
import com.svenfulenchek.shipmenttracker.Models.Supplier;
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

public class supplier_list {
    @FXML private ListView<Supplier> supplier_list_view;
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
        populateSuppliersList();

        // Fill the form whenever a Supplier is selected from the list.
        supplier_list_view.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Supplier>() {
            @Override
            public void changed(ObservableValue<? extends Supplier> observableValue, Supplier unSelectedSupplier, Supplier selectedSupplier) {
                if (selectedSupplier != null) {
                    name_field.setText(selectedSupplier.getName());
                    phone_field.setText(selectedSupplier.getPhone());
                    website_field.setText(selectedSupplier.getWebsite());
                    email_field.setText(selectedSupplier.getEmail());
                    address_field.setText(selectedSupplier.getAddress());
                    notes_text_area.setText(selectedSupplier.getNotes());
                    inbound_shipments_table.setPlaceholder(new Label("This supplier has no inbound shipments."));
                    shipment_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
                    trailer_col.setCellValueFactory(new PropertyValueFactory<>("trailer_number"));
                    tracking_col.setCellValueFactory(new PropertyValueFactory<>("tracking_number"));
                    inbound_shipments_table.getItems().setAll(database_operation.getInboundShipmentsBySupplierId(selectedSupplier.getId()));
                }
            }
        });
    }

    /**
     * Clear all text fields and table in the view
     */
    public void clearFields() {
        name_field.setText("");
        phone_field.setText("");
        website_field.setText("");
        email_field.setText("");
        address_field.setText("");
        notes_text_area.setText("");
        inbound_shipments_table.getItems().clear();
    }

    /**
     * Populates the Suppliers list.
     */
    public void populateSuppliersList(){
        ObservableList<Supplier> allSuppliers = database_operation.getAllSuppliers();
        supplier_list_view.getItems().setAll(allSuppliers);
        clearFields();
    }

    public void onNewSupplier() throws IOException {
        Parent root = FXMLLoader.load(MainApplication.class.getResource("new_supplier.fxml"));
        Stage newSupplierStage = new Stage();
        newSupplierStage.setTitle("New Supplier");
        newSupplierStage.setScene(new Scene(root, 350, 300));
        newSupplierStage.setResizable(false);
        newSupplierStage.getIcons().add(Repository.app_icon);
        newSupplierStage.show();
        newSupplierStage.setOnHiding( event -> populateSuppliersList() );
    }

    public void onEditSupplier() throws IOException {
        if (supplier_list_view.getSelectionModel().getSelectedItem() != null) {
            edit_supplier.selected_supplier = supplier_list_view.getSelectionModel().getSelectedItem();

            Parent root = FXMLLoader.load(MainApplication.class.getResource("edit_supplier.fxml"));
            Stage editSupplierStage = new Stage();
            editSupplierStage.setTitle("Edit Supplier");
            editSupplierStage.setScene(new Scene(root, 350, 300));
            editSupplierStage.setResizable(false);
            editSupplierStage.getIcons().add(Repository.app_icon);
            editSupplierStage.show();
            editSupplierStage.setOnHiding(event -> populateSuppliersList());
        }
    }

}
