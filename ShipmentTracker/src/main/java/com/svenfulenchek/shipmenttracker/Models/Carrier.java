package com.svenfulenchek.shipmenttracker.Models;

public class Carrier {
    private int id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String website;
    private String notes;

    /**
     * Carrier Constructor
     * Use the "id" parameter when retrieving Carriers from the database.
     * Do not use the "id" parameter when adding a new Carrier to the database.
     *
     * @param id The "id" primary key of the Carrier in the database
     * @param name The Carrier's name
     * @param address The Carrier's address
     * @param email The Carrier's contact email address
     * @param phone The Carrier's contact phone number
     * @param website The Carrier's website
     * @param notes Notes that can be entered by any user
     */
    public Carrier(int id, String name, String address, String email, String phone, String website, String notes) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.notes = notes;
    }

    // Carrier constructor for creating a new carrier
    public Carrier(String name, String email, String phone, String website, String address, String notes) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.notes = notes;
    }

    // getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() { return this.name; }
}
