package com.svenfulenchek.shipmenttracker.Models;

import com.svenfulenchek.shipmenttracker.Database.database_operation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Array;
import java.sql.Timestamp;

public class Shipment {
    private int id;
    private int user_id;
    private int supplier_id;
    private final String supplier_name;
    private int carrier_id;
    private final String carrier_name;
    private Array item_list;
    private String tracking_number;
    private String notes;
    private String trailer_number;
    private Timestamp expected_date;
    private Timestamp received_date;
    public boolean received;

    /**
     * Shipment Constructor
     * @param id The "id" primary key of the shipment in the database
     * @param user_id The id of the user who created the shipment.
     * @param supplier_id The id of the supplier associated with the shipment.
     * @param carrier_id The id of the carrier associated with the shipment.
     * @param item_list A list of items that were ordered, strings entered by the user
     * @param tracking_number The carrier's shipment tracking number
     * @param notes A string entered by the user
     * @param trailer_number Optional: The trailer number that contains / is the shipment
     */
    public Shipment(int id, int user_id, int supplier_id, int carrier_id, Array item_list, String tracking_number, String notes, String trailer_number, Timestamp expected_date) throws Exception {
        this.id = id;
        this.user_id = user_id;
        this.supplier_id = supplier_id;
        this.carrier_id = carrier_id;
        this.item_list = item_list;
        this.tracking_number = tracking_number;
        this.notes = notes;
        this.trailer_number = trailer_number;
        this.expected_date = expected_date;

        this.supplier_name = database_operation.getSupplierNameById(this.supplier_id);
        this.carrier_name = database_operation.getCarrierNameById(this.carrier_id);
    }

    public Shipment(int user_id, int supplier_id, int carrier_id, Array item_list, String tracking_number, String notes, String trailer_number, Timestamp expected_date) throws Exception {
        this.user_id = user_id;
        this.supplier_id = supplier_id;
        this.carrier_id = carrier_id;
        this.item_list = item_list;
        this.tracking_number = tracking_number;
        this.notes = notes;
        this.trailer_number = trailer_number;
        this.expected_date = expected_date;

        this.supplier_name = database_operation.getSupplierNameById(this.id);
        this.carrier_name = database_operation.getCarrierNameById(this.id);
    }

    /**
     * Used to set a shipment as received.
     * @param received_date The date/time that the shipment was received on.
     */
    public void Receive( Timestamp received_date ) {
        this.received = true;
        this.received_date = received_date;
    }

    /**
     * Can be used if something needs to be unreceived in the system due to a user mistake
     */
    public void unReceive(){
        this.received = false;
        this.received_date = null;
    }

    /**
     * Returns the Array "itemList" as an ObservableList of Strings
     * @return ObservableList<String> of list items
     */
    public ObservableList<String> getObservableItemList(){
        ObservableList<String> observable_item_list = FXCollections.observableArrayList();
        if (this.getItem_list() != null) {
            String list_as_string = this.item_list.toString().replace("{", "").replace("}", "");
            observable_item_list.addAll(list_as_string.split(","));
        }
        return observable_item_list;
    }

    // getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public int getCarrier_id() {
        return carrier_id;
    }

    public void setCarrier_id(int carrier_id) {
        this.carrier_id = carrier_id;
    }

    public Array getItem_list() {
        return item_list;
    }

    public void setItem_list(Array item_list) {
        this.item_list = item_list;
    }

    public String getTracking_number() {
        return tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTrailer_number() {
        return trailer_number;
    }

    public void setTrailer_number(String trailer_number) {
        this.trailer_number = trailer_number;
    }

    // extra getters and setters

    public Timestamp getExpected_date() {
        return expected_date;
    }

    public void setExpected_date(Timestamp expected_date) {
        this.expected_date = expected_date;
    }

    public Timestamp getReceived_date() {
        return received_date;
    }

    public void setReceived_date(Timestamp received_date) {
        this.received_date = received_date;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public String getCarrier_name() {
        return carrier_name;
    }
}
