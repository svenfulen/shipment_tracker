package com.svenfulenchek.shipmenttracker.Models;

public class Supplier {
    private int id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String website;
    private String notes;

    /**
     * Supplier Constructor
     * @param id The supplier's primary key "id" in the database
     * @param name The name of the supplier / company name
     * @param address The supplier's address
     * @param email Contact email for the supplier
     * @param phone Contact phone number for the supplier
     * @param website The supplier's website
     * @param notes Notes that can be entered by users
     */
    public Supplier(int id, String name, String address, String email, String phone, String website, String notes) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.notes = notes;
    }

    /**
     * Supplier Constructor without id
     * @param name The name of the supplier / company name
     * @param address The supplier's address
     * @param email Contact email for the supplier
     * @param phone Contact phone number for the supplier
     * @param website The supplier's website
     * @param notes Notes that can be entered by users
     */
    public Supplier(String name, String address, String email, String phone, String website, String notes) {
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
    public String toString() { return(this.name); }
}
