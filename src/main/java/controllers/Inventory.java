package controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("inventory/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Inventory {
    @GET
    @Path("load")
    public String inventoryLoad() {
        System.out.println("Invoked Inventory.inventoryLoad()");
        JSONArray inventory = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT * FROM Inventory");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject row = new JSONObject();
                row.put("itemID", results.getInt(1));
                row.put("price", results.getInt(2));
                row.put("quantity", results.getInt(3));
                row.put("name", results.getString(4));
                inventory.add(row);
            }
            JSONObject response = new JSONObject();
            response.put("inventory", inventory);
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }

    @POST
    @Path("update")
    public String inventoryUpdate(@FormDataParam("itemID") Integer itemID, @FormDataParam("quantity") Integer quantity) {
        try {
            System.out.println("Invoked Inventory.inventory/update quantity=" + quantity);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Inventory SET quantity = ? WHERE itemID = ?");
            ps.setInt(1, quantity);
            ps.setInt(2, itemID);
            ps.execute();
            return "{\"OK\": \"Inventory updated\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to update item, please see server console for more info.\"}";
        }

    }
}
