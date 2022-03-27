package com.svenfulenchek.shipmenttracker.Database;

import java.sql.*;

public class database_connection {

    private static final String url = "jdbc:postgresql://216.229.50.187:5432/capstone";
    private static final String user = "postgres";
    private static final String password = "8008";

    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

}
