package com.svenfulenchek.shipmenttracker.Database;

import com.svenfulenchek.shipmenttracker.Models.Carrier;
import com.svenfulenchek.shipmenttracker.Models.Shipment;
import com.svenfulenchek.shipmenttracker.Models.Supplier;
import com.svenfulenchek.shipmenttracker.Utils.ui_popups;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class database_operation {

    /**
     * Helper function used to create a list of results from a SQL query.
     * This function's purpose is to simplify/reduce code.
     * @param query The SQL query to execute.
     * @return ResultSet containing the results of query
     */
    public static ResultSet getQueryResults(String query){
        ResultSet resultSet = null;
        try{
            // Create a new statement for each Query
            Statement statement = Repository.connection.createStatement();
            Repository.statement = statement;
            // execute the query
            Repository.statement.execute(query);
            resultSet = Repository.statement.getResultSet();
        }
        catch(SQLException querySqlException){
            querySqlException.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Get an ObservableList of ALL shipments in the database.
     * @return FXCollections.observableArrayList();
     */
    public static ObservableList<Shipment> getAllShipments(){
        ObservableList<Shipment> allShipments = FXCollections.observableArrayList();

        try {
            ResultSet shipments = getQueryResults("SELECT * FROM db.shipment");
            if (shipments != null) {
                while (shipments.next()) {
                    int shipment_id = shipments.getInt("id");
                    int supplier_id = shipments.getInt("supplier_id");
                    int user_id = shipments.getInt("user_id");
                    int carrier_id = shipments.getInt("carrier_id");
                    Array item_list = shipments.getArray("item_list");
                    String notes = shipments.getString("notes");
                    String tracking_number = shipments.getString("tracking_number");
                    String trailer_number = shipments.getString("trailer_number");
                    Timestamp expected_date = shipments.getTimestamp("expected_date");

                    Shipment newShipment = new Shipment(shipment_id, user_id, supplier_id, carrier_id, item_list, tracking_number, notes, trailer_number, expected_date);
                    allShipments.add(newShipment);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            ui_popups.errorMessage(e.toString());
        }
        return allShipments;
    }

    /**
     * Get an ObservableList of all inbound shipments in the database.
     * @return FXCollections.observableArrayList();
     */
    public static ObservableList<Shipment> getInboundShipments(){
        ObservableList<Shipment> allShipments = FXCollections.observableArrayList();

        try {
            ResultSet shipments = getQueryResults("SELECT * FROM db.shipment WHERE received=\'false\'");
            if (shipments != null) {
                while (shipments.next()) {
                    int shipment_id = shipments.getInt("id");
                    int supplier_id = shipments.getInt("supplier_id");
                    int user_id = shipments.getInt("user_id");
                    int carrier_id = shipments.getInt("carrier_id");
                    Array item_list = shipments.getArray("item_list");
                    String notes = shipments.getString("notes");
                    String tracking_number = shipments.getString("tracking_number");
                    String trailer_number = shipments.getString("trailer_number");
                    Timestamp expected_date = shipments.getTimestamp("expected_date");

                    Shipment newShipment = new Shipment(shipment_id, user_id, supplier_id, carrier_id, item_list, tracking_number, notes, trailer_number, expected_date);
                    allShipments.add(newShipment);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            ui_popups.errorMessage(e.toString());
        }
        return allShipments;
    }

    /**
     * Get all inbound shipments that are associated with a single carrier
     * @param id The carrier id in the database
     * @return ObservableList of Shipment objects associated with the given carrier
     */
    public static ObservableList<Shipment> getInboundShipmentsByCarrierId(int id){
        ObservableList<Shipment> allShipments = FXCollections.observableArrayList();

        try {
            ResultSet shipments = getQueryResults("SELECT * FROM db.shipment WHERE received='false' AND carrier_id=" + id);
            if (shipments != null) {
                while (shipments.next()) {
                    int shipment_id = shipments.getInt("id");
                    int supplier_id = shipments.getInt("supplier_id");
                    int user_id = shipments.getInt("user_id");
                    int carrier_id = shipments.getInt("carrier_id");
                    Array item_list = shipments.getArray("item_list");
                    String notes = shipments.getString("notes");
                    String tracking_number = shipments.getString("tracking_number");
                    String trailer_number = shipments.getString("trailer_number");
                    Timestamp expected_date = shipments.getTimestamp("expected_date");

                    Shipment newShipment = new Shipment(shipment_id, user_id, supplier_id, carrier_id, item_list, tracking_number, notes, trailer_number, expected_date);
                    allShipments.add(newShipment);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            ui_popups.errorMessage(e.toString());
        }
        return allShipments;
    }

    /**
     * Get all inbound shipments that are associated with a single supplier
     * @param id The supplier id in the database
     * @return ObservableList of Shipment objects associated with the given supplier
     */
    public static ObservableList<Shipment> getInboundShipmentsBySupplierId(int id) {
        ObservableList<Shipment> allShipments = FXCollections.observableArrayList();

        try {
            ResultSet shipments = getQueryResults("SELECT * FROM db.shipment WHERE received='false' AND supplier_id=" + id);
            if (shipments != null) {
                while (shipments.next()) {
                    int shipment_id = shipments.getInt("id");
                    int supplier_id = shipments.getInt("supplier_id");
                    int user_id = shipments.getInt("user_id");
                    int carrier_id = shipments.getInt("carrier_id");
                    Array item_list = shipments.getArray("item_list");
                    String notes = shipments.getString("notes");
                    String tracking_number = shipments.getString("tracking_number");
                    String trailer_number = shipments.getString("trailer_number");
                    Timestamp expected_date = shipments.getTimestamp("expected_date");

                    Shipment newShipment = new Shipment(shipment_id, user_id, supplier_id, carrier_id, item_list, tracking_number, notes, trailer_number, expected_date);
                    allShipments.add(newShipment);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            ui_popups.errorMessage(e.toString());
        }
        return allShipments;
    }

    /**
     * Get all Carriers in the database
     * @return A list of all carriers in the database in the ObservableList format
     */
    public static ObservableList<Carrier> getAllCarriers(){
        ObservableList<Carrier> allCarriers = FXCollections.observableArrayList();

        try {
            ResultSet carriers = getQueryResults("SELECT * FROM db.carrier");
            if(carriers != null){
                while (carriers.next()) {
                    int id = carriers.getInt("id");
                    String name = carriers.getString("name");
                    String address = carriers.getString("address");
                    String email = carriers.getString("email");
                    String phone = carriers.getString("phone");
                    String website = carriers.getString("website");
                    String notes = carriers.getString("notes");

                    Carrier newCarrier = new Carrier(id, name, address, email, phone, website, notes);
                    allCarriers.add(newCarrier);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            ui_popups.errorMessage(e.toString());
        }
        return allCarriers;
    }

    /**
     * Get all Suppliers in the database
     * @return A list of all carriers in the database in the ObservableList format
     */
    public static ObservableList<Supplier> getAllSuppliers(){
        ObservableList<Supplier> allSuppliers = FXCollections.observableArrayList();

        try {
            ResultSet suppliers = getQueryResults("SELECT * FROM db.supplier");
            if(suppliers != null){
                while (suppliers.next()) {
                    int id = suppliers.getInt("id");
                    String name = suppliers.getString("name");
                    String address = suppliers.getString("address");
                    String email = suppliers.getString("email");
                    String phone = suppliers.getString("phone");
                    String website = suppliers.getString("website");
                    String notes = suppliers.getString("notes");

                    Supplier newSupplier = new Supplier(id, name, address, email, phone, website, notes);
                    allSuppliers.add(newSupplier);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            ui_popups.errorMessage(e.toString());
        }
        return allSuppliers;
    }

    /**
     * Update a carrier in the database
     * @param carrier A Carrier object which has the id of the carrier to be updated and the details for the update.
     * @throws Exception any exception
     */
    public static void updateCarrier(Carrier carrier) throws Exception {
        try{
            String query = "UPDATE db.carrier SET name = '" + carrier.getName()
                    + "'," + "address = '" + carrier.getAddress()
                    + "'," + "email = '" + carrier.getEmail()
                    + "'," + "phone = '" + carrier.getPhone()
                    + "'," + "website = '" + carrier.getWebsite()
                    + "'," + "notes = '" + carrier.getNotes()
                    + "' WHERE id = " + carrier.getId();
            Statement statement = Repository.connection.createStatement();
            Repository.statement = statement;
            Repository.statement.execute(query);
            Repository.connection.commit();
            System.out.println(query);
        }
        catch(Exception e){
            e.printStackTrace();
            ui_popups.errorMessage("Error while updating Carrier.  Please check your internet connection.");
        }
    }

    /**
     * Update a supplier in the database
     * @param supplier A Supplier object which has the id of the supplier to be updated and the details for the update.
     * @throws Exception any exception
     */
    public static void updateSupplier(Supplier supplier) throws Exception {
        try{
            String query = "UPDATE db.supplier SET name = '" + supplier.getName()
                    + "'," + "address = '" + supplier.getAddress()
                    + "'," + "email = '" + supplier.getEmail()
                    + "'," + "phone = '" + supplier.getPhone()
                    + "'," + "website = '" + supplier.getWebsite()
                    + "'," + "notes = '" + supplier.getNotes()
                    + "' WHERE id = " + supplier.getId();
            Statement statement = Repository.connection.createStatement();
            Repository.statement = statement;
            Repository.statement.execute(query);
            Repository.connection.commit();
            System.out.println(query);
        }
        catch(Exception e){
            e.printStackTrace();
            ui_popups.errorMessage("Error while updating Supplier.  Please check your internet connection.");
        }
    }

     /**
     * Removes a Supplier from the database given a carrier ID
     * @param id The ID of the supplier in the database
     * @throws Exception any
     */
    public static void deleteSupplierById(int id) throws Exception {
        ResultSet associated_shipments = getQueryResults("SELECT * FROM db.shipment WHERE supplier_id=" + id);
        if(associated_shipments != null){
            ui_popups.errorMessage("This Supplier cannot be deleted because there are associated Shipments.");
        }
        else {
            try {
                String query = "DELETE FROM db.supplier WHERE id=" + id;
                Statement statement = Repository.connection.createStatement();
                Repository.statement = statement;
                Repository.statement.execute(query);
                Repository.connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
                ui_popups.errorMessage("Error while deleting Supplier.  Please check your internet connection.");
            }
        }
    }

    /**
     * Removes a Carrier from the database given a carrier ID
     * @param id The ID of the carrier in the database
     * @throws Exception any
     */
    public static void deleteCarrierById(int id) throws Exception {
        ResultSet associated_carriers = getQueryResults("SELECT * FROM db.shipment WHERE carrier_id=" + id);
        if(associated_carriers != null){
            ui_popups.errorMessage("This Carrier cannot be deleted because there are associated Shipments.");
        }
        else {
            try {
                String query = "DELETE FROM db.carrier WHERE id=" + id;
                Statement statement = Repository.connection.createStatement();
                Repository.statement = statement;
                Repository.statement.execute(query);
                Repository.connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
                ui_popups.errorMessage("Error while deleting Carrier.  Please check your internet connection.");
            }
        }
    }

    /**
     * Adds a Supplier to the database.
     * @param supplier A Carrier object with the details of the new Supplier to be added.
     * @throws Exception SQLException
     */
    public static void addSupplier(Supplier supplier) throws Exception {
        String name = supplier.getName().replace("'", "''");
        String address = supplier.getAddress().replace("'", "''");
        String email = supplier.getEmail().replace("'", "''");
        String phone = supplier.getPhone().replace("'", "''");
        String website = supplier.getWebsite().replace("'", "''");
        String notes = supplier.getNotes().replace("'", "''");

        try{
            String query = "INSERT INTO db.supplier(name, address, email, phone, website, notes) VALUES ('" +
                    name + "', '" + address + "', '" + email + "', '" + phone + "', '" + website + "', '" + notes + "') ";
            Statement statement = Repository.connection.createStatement();
            Repository.statement = statement;
            Repository.statement.execute(query);
            Repository.connection.commit();
        }
        catch(Exception e){
            e.printStackTrace();
            ui_popups.errorMessage("Error while adding Supplier.  Please check your internet connection.");
        }

    }

    /**
     * Adds a Carrier to the database.
     * @param carrier A Carrier object with the details of the new Carrier to be added.
     * @throws Exception SQLException
     */
    public static void addCarrier(Carrier carrier) throws Exception {
        String name = carrier.getName().replace("'", "''");
        String address = carrier.getAddress().replace("'", "''");
        String email = carrier.getEmail().replace("'", "''");
        String phone = carrier.getPhone().replace("'", "''");
        String website = carrier.getWebsite().replace("'", "''");
        String notes = carrier.getNotes().replace("'", "''");

        try{
            String query = "INSERT INTO db.carrier(name, address, email, phone, website, notes) VALUES ('" +
                    name + "', '" + address + "', '" + email + "', '" + phone + "', '" + website + "', '" + notes + "') ";
            Statement statement = Repository.connection.createStatement();
            Repository.statement = statement;
            Repository.statement.execute(query);
            Repository.connection.commit();
        }
        catch(Exception e){
            e.printStackTrace();
            ui_popups.errorMessage("Error while adding Carrier.  Please check your internet connection.");
        }

    }

    /**
     * Add a Shipment to the database using a Shipment object.
     * @param shipment The Shipment object containing the details of the shipment to be added to the database.
     * @throws Exception Any exception
     */
    public static void addShipment(Shipment shipment) throws Exception {
        try {
            String query = "INSERT INTO db.shipment(user_id, supplier_id, carrier_id, item_list, tracking_number, notes, trailer_number, expected_date, received) VALUES (" +
                    shipment.getUser_id() + "," + shipment.getSupplier_id() + "," + shipment.getCarrier_id() + ",'" + shipment.getItem_list() + "','" + shipment.getTracking_number() + "','" + shipment.getNotes() + "','" +
                    shipment.getTrailer_number() + "','" + shipment.getExpected_date() + "', 'false')";
            System.out.println(query);
            Statement statement = Repository.connection.createStatement();
            Repository.statement = statement;
            Repository.statement.execute(query);
            Repository.connection.commit();
        }
        catch(Exception e){
            e.printStackTrace();
            ui_popups.errorMessage("Error while adding Shipment.  Please check your internet connection.");
        }
    }

    /**
     * Update a shipment in the database using a Shipment object.
     * @param shipment The Shipment object containing the details of the shipment to be updated in the database.
     * @throws Exception Any exception
     */
    public static void updateShipment(Shipment shipment) throws Exception {
        boolean received = shipment.getReceived_date() != null;
        Timestamp received_date = null;
        if (shipment.getReceived_date() != null) {
            received_date = shipment.getReceived_date();
        }
        try {
            String query;
            if (received_date == null) {
                query = "UPDATE db.shipment SET user_id = " + Repository.current_user_id
                        + "," + "supplier_id = " + shipment.getSupplier_id()
                        + "," + "carrier_id = " + shipment.getCarrier_id()
                        + "," + "item_list = '" + shipment.getItem_list()
                        + "'," + "tracking_number = '" + shipment.getTracking_number()
                        + "'," + "notes = '" + shipment.getNotes()
                        + "'," + "trailer_number = '" + shipment.getTrailer_number()
                        + "'," + "expected_date = '" + shipment.getExpected_date()
                        + "'," + "received = '" + received
                        // don't enter a received date
                        + "' WHERE id = " + shipment.getId();
            }
            else {
                query = "UPDATE db.shipment SET user_id = " + Repository.current_user_id
                        + "," + "supplier_id = " + shipment.getSupplier_id()
                        + "," + "carrier_id = " + shipment.getCarrier_id()
                        + "," + "item_list = '" + shipment.getItem_list()
                        + "'," + "tracking_number = '" + shipment.getTracking_number()
                        + "'," + "notes = '" + shipment.getNotes()
                        + "'," + "trailer_number = '" + shipment.getTrailer_number()
                        + "'," + "expected_date = '" + shipment.getExpected_date()
                        + "'," + "received = '" + received
                        + "'," + "received_date = '" + shipment.getReceived_date()
                        + "' WHERE id = " + shipment.getId();
            }

            Statement statement = Repository.connection.createStatement();
            Repository.statement = statement;
            Repository.statement.execute(query);
            Repository.connection.commit();
            System.out.println(query);
        }
        catch(Exception e){
            e.printStackTrace();
            ui_popups.errorMessage("Error while updating Shipment.  Please check your internet connection.");
        }
    }
    /**
     * Since Shipment objects only retrieve Supplier ID from database, this is how the Supplier Name can be retrieved
     * @param id The Supplier ID in the database
     * @return Supplier name as a String
     * @throws Exception SQLException
     */
    public static String getSupplierNameById(int id) throws Exception {
        String result = "SUPPLIER NAME NOT FOUND";
        ResultSet supplier_name = getQueryResults("SELECT * FROM db.supplier WHERE id = " + id);
        if(supplier_name.next()){
            result = supplier_name.getString("name");
        }
        return result;
    }

    /**
     * Since Shipment objects only retrieve Carrier ID from database, this is how the Carrier Name can be retrieved
     * @param id The Carrier ID in the database
     * @return Carrier name as a String
     * @throws Exception SQLException
     */
    public static String getCarrierNameById(int id) throws Exception {
        String result = "CARRIER NAME NOT FOUND";
        ResultSet carrier_name = getQueryResults("SELECT * FROM db.carrier WHERE id = " + id);
        if(carrier_name.next()){
            result = carrier_name.getString("name");
        }
        return result;
    }

    /**
     * Since Shipment objects only retrieve Carrier ID from database, this is how a Carrier Object can be retrieved
     * @param id The Carrier ID in the database
     * @return Carrier Object
     * @throws Exception SQLException
     */
    public static Carrier getCarrierById(int id) throws Exception {
        Carrier carrier = null;
        ResultSet carrier_results = getQueryResults("SELECT * FROM db.carrier WHERE id = " + id);
        if(carrier_results.next()){
            // Make a new carrier object using the result found in the database.
            carrier = new Carrier
            (
            carrier_results.getInt("id"),
            carrier_results.getString("name"),
            carrier_results.getString("address"),
            carrier_results.getString("email"),
            carrier_results.getString("phone"),
            carrier_results.getString("website"),
            carrier_results.getString("notes")
            );
        }
        return carrier;
    }

    /**
     * Since Shipment objects only retrieve Supplier ID from database, this is how a Supplier Object can be retrieved
     * @param id The Supplier ID in the database
     * @return Supplier Object
     * @throws Exception SQLException
     */
    public static Supplier getSupplierById(int id) throws Exception {
        Supplier supplier = null;
        ResultSet supplier_results = getQueryResults("SELECT * FROM db.supplier WHERE id = " + id);
        if(supplier_results.next()){
            supplier = new Supplier
            (
            supplier_results.getInt("id"),
            supplier_results.getString("name"),
            supplier_results.getString("address"),
            supplier_results.getString("email"),
            supplier_results.getString("phone"),
            supplier_results.getString("website"),
            supplier_results.getString("notes")
            );
        }
        return supplier;
    }

    public static void deleteShipmentById(int id){
        try {
            String query = "DELETE FROM db.shipment WHERE id=" + id;
            Statement statement = Repository.connection.createStatement();
            Repository.statement = statement;
            Repository.statement.execute(query);
            Repository.connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            ui_popups.errorMessage("Error while deleting Shipment.  Please check your internet connection.");
        }
    }

    public static boolean receiveShipmentById(int id) throws SQLException {
        try {
            Timestamp received_timestamp = new Timestamp(System.currentTimeMillis());
            String query = "UPDATE db.shipment SET received='true', received_date ='" + received_timestamp + "' WHERE id = " + id;
            Statement statement = Repository.connection.createStatement();
            Repository.statement = statement;
            Repository.statement.execute(query);
            Repository.connection.commit();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ObservableList<Shipment> searchShipmentsById(String id) throws Exception {
        ObservableList<Shipment> found_shipments_list = FXCollections.observableArrayList();
        String query = "SELECT * FROM db.shipment";
        ResultSet found_shipments = getQueryResults(query);
        while (found_shipments.next()){
            int shipment_id = found_shipments.getInt("id");

            if (String.valueOf(shipment_id).contains(id)) {
                int supplier_id = found_shipments.getInt("supplier_id");
                int user_id = found_shipments.getInt("user_id");
                int carrier_id = found_shipments.getInt("carrier_id");
                Array item_list = found_shipments.getArray("item_list");
                String notes = found_shipments.getString("notes");
                String tracking_number = found_shipments.getString("tracking_number");
                String trailer_number = found_shipments.getString("trailer_number");
                Timestamp expected_date = found_shipments.getTimestamp("expected_date");

                Shipment newShipment = new Shipment(shipment_id, user_id, supplier_id, carrier_id, item_list, tracking_number, notes, trailer_number, expected_date);

                found_shipments_list.add(newShipment);
            }
        }
        return found_shipments_list;
    }

    public static ObservableList<Shipment> searchShipmentsByTrackingNumber(String tracking_number) throws Exception {
        ObservableList<Shipment> found_shipments_list = FXCollections.observableArrayList();
        String query = "SELECT * FROM db.shipment";
        ResultSet found_shipments = getQueryResults(query);
        while (found_shipments.next()){
            String tracking_number_new = found_shipments.getString("tracking_number");

            if(tracking_number_new.contains(tracking_number)) {
                int shipment_id = found_shipments.getInt("id");
                int supplier_id = found_shipments.getInt("supplier_id");
                int user_id = found_shipments.getInt("user_id");
                int carrier_id = found_shipments.getInt("carrier_id");
                Array item_list = found_shipments.getArray("item_list");
                String notes = found_shipments.getString("notes");

                String trailer_number = found_shipments.getString("trailer_number");
                Timestamp expected_date = found_shipments.getTimestamp("expected_date");
                Shipment newShipment = new Shipment(shipment_id, user_id, supplier_id, carrier_id, item_list, tracking_number_new, notes, trailer_number, expected_date);
                found_shipments_list.add(newShipment);
            }
        }
        return found_shipments_list;
    }

    public static ObservableList<Shipment> searchShipmentsByNotes(String notes_string) throws Exception {
        ObservableList<Shipment> found_shipments_list = FXCollections.observableArrayList();
        String query = "SELECT * FROM db.shipment";
        ResultSet found_shipments = getQueryResults(query);
        while (found_shipments.next()){
            String notes = found_shipments.getString("notes");
            if(notes.contains(notes_string)) {
                int shipment_id = found_shipments.getInt("id");
                int supplier_id = found_shipments.getInt("supplier_id");
                int user_id = found_shipments.getInt("user_id");
                int carrier_id = found_shipments.getInt("carrier_id");
                Array item_list = found_shipments.getArray("item_list");
                String tracking_number_new = found_shipments.getString("tracking_number");
                String trailer_number = found_shipments.getString("trailer_number");
                Timestamp expected_date = found_shipments.getTimestamp("expected_date");

                Shipment newShipment = new Shipment(shipment_id, user_id, supplier_id, carrier_id, item_list, tracking_number_new, notes, trailer_number, expected_date);
                found_shipments_list.add(newShipment);
            }
        }
        return found_shipments_list;
    }

    public static ObservableList<Shipment> searchShipmentsByListItem(String list_item) throws Exception {
        ObservableList<Shipment> found_shipments_list = FXCollections.observableArrayList();
        String query = "SELECT * from db.shipment;";
        ResultSet found_shipments = getQueryResults(query);
        while (found_shipments.next()){
            Array item_list = found_shipments.getArray("item_list");
            if (item_list.toString().contains(list_item)) {
                int shipment_id = found_shipments.getInt("id");
                int supplier_id = found_shipments.getInt("supplier_id");
                int user_id = found_shipments.getInt("user_id");
                int carrier_id = found_shipments.getInt("carrier_id");
                String notes = found_shipments.getString("notes");
                String tracking_number = found_shipments.getString("tracking_number");
                String trailer_number = found_shipments.getString("trailer_number");
                Timestamp expected_date = found_shipments.getTimestamp("expected_date");

                Shipment newShipment = new Shipment(shipment_id, user_id, supplier_id, carrier_id, item_list, tracking_number, notes, trailer_number, expected_date);
                found_shipments_list.add(newShipment);
            }
        }
        return found_shipments_list;
    }

    public static ObservableList<Shipment> trailerHistory(String trailer_no) throws Exception {
        ObservableList<Shipment> allShipments = FXCollections.observableArrayList();
        String query = "SELECT * from db.shipment WHERE trailer_number = '" + trailer_no + "' ORDER BY expected_date DESC;";
        try {
            ResultSet shipments = getQueryResults(query);
            if (shipments != null) {
                while (shipments.next()) {
                    int shipment_id = shipments.getInt("id");
                    int supplier_id = shipments.getInt("supplier_id");
                    int user_id = shipments.getInt("user_id");
                    int carrier_id = shipments.getInt("carrier_id");
                    Array item_list = shipments.getArray("item_list");
                    String notes = shipments.getString("notes");
                    String tracking_number = shipments.getString("tracking_number");
                    String trailer_number = shipments.getString("trailer_number");
                    Timestamp expected_date = shipments.getTimestamp("expected_date");

                    Shipment newShipment = new Shipment(shipment_id, user_id, supplier_id, carrier_id, item_list, tracking_number, notes, trailer_number, expected_date);
                    allShipments.add(newShipment);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            ui_popups.errorMessage(e.toString());
        }
        return allShipments;
    }

}
