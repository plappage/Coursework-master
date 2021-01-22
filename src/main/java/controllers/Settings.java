package controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("settings/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Settings {
    @GET
    @Path("get")
    public String settingsGet() {
        System.out.println("Invoked Settings.settingsGet()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT settingsID, settingsStatus FROM Settings");
            ResultSet results = ps.executeQuery();
            while (results.next() == true) {
                JSONObject row = new JSONObject();
                row.put("settingsID", results.getInt(1));
                row.put("settingsStatus", results.getBoolean(2));
                response.add(row);
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }

    @POST
    @Path("update")
    public String inventoryUpdate(@FormDataParam("settingsID") Integer settingsID, @FormDataParam("settingsStatus") Boolean settingsStatus) {
        try {
            System.out.println("Invoked Inventory.inventory/update settingsStatus=" + settingsStatus);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Settings SET settingsStatus = ? WHERE settingsID = ?");
            ps.setBoolean(1, settingsStatus);
            ps.setInt(2, settingsID);
            ps.execute();
            return "{\"OK\": \"Settings updated\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to update item, please see server console for more info.\"}";
        }

    }
}
