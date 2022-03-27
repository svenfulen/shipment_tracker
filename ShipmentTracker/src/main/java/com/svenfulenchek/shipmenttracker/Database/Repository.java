package com.svenfulenchek.shipmenttracker.Database;

import com.svenfulenchek.shipmenttracker.MainApplication;
import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Manages all database operation
 */
public class Repository {
    // Stores the username of the user that is currently logged in.
    public static int current_user_id;
    public static Connection connection;
    public static Statement statement;
    public static Image app_icon = new Image(MainApplication.class.getResourceAsStream("icon.png"));

}
