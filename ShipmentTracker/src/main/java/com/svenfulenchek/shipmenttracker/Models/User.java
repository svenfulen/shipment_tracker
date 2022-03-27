package com.svenfulenchek.shipmenttracker.Models;

public class User {
    private int id;
    private String username;
    private String password;

    /**
     * User Constructor
     * @param id Primary key "id" in database
     * @param username User's username in database
     * @param password User's password in database
     */
    public User(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() { return(username); }
}
