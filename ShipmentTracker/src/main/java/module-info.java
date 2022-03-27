module com.svenfulenchek.shipmenttracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.svenfulenchek.shipmenttracker to javafx.graphics;
    opens com.svenfulenchek.shipmenttracker.Controllers to javafx.fxml;

    exports com.svenfulenchek.shipmenttracker.Controllers;
    // exports com.svenfulenchek.shipmenttracker.Assets;
    exports com.svenfulenchek.shipmenttracker.Database;
    exports com.svenfulenchek.shipmenttracker.Models;
    // exports com.svenfulenchek.shipmenttracker.Utils;
}