package controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("entry/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Leaderboard {
    @GET
    @Path("list")
    public String entryList() {
        System.out.println("Invoked Leaderboard.entryList()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT * FROM Leaderboard ORDER BY time LIMIT 10");
            ResultSet results = ps.executeQuery();
            while (results.next() == true) {
                JSONObject row = new JSONObject();
                row.put("entryID", results.getInt(1));
                row.put("name", results.getString(2));
                row.put("time", results.getInt(3));
                response.add(row);
            }
            JSONObject jso = new JSONObject();
            jso.put("leaderboard", response);
            return jso.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }

    @POST
    @Path("save")
    public String entrySave(@FormDataParam("name") String name, @FormDataParam("time") Integer time) {
        System.out.println("Invoked Leaderboard.entrySave()");
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Leaderboard (name, time) VALUES (?,?)");
            ps.setString(1, name);
            ps.setInt(2, time);
            ps.execute();
            return "{\"OK\": \"Added entry.\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to create new item, please see server console for more info.\"}";
        }
    }
}
