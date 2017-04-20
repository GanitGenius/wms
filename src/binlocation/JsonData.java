/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binlocation;

import dataBase.DBConnection;
import dataBase.DBProperties;
import dataBase.SQL;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author GanitGenius
 */
public class JsonData {

    /**
     * @param args the command line arguments
     */
    SQL sql = new SQL();

    DBConnection dbCon = new DBConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();

    public boolean check(String s, String id) {

        JsonReader reader = Json.createReader(new StringReader(s));

        JsonObject json = reader.readObject();

        reader.close();
        JsonObject response = json.getJsonObject("response");
        System.out.println("Id   : " + response.getString("id"));
        JsonArray error = response.getJsonArray("errors");
        boolean res = true;
        if (error.size() > 0) {

            return false;
        } else {
            JsonArray bins_packed = response.getJsonArray("bins_packed");

            int bin1_count = 0, bin2_count = 0, bin3_count = 0;

            con = dbCon.geConnection();
            try {
                pst = con.prepareCall("select COUNT(*) AS rowcount FROM " + db + ".BinStatus where Type regexp \"^Type1_\" and ProductNumber = ''");
                rs = pst.executeQuery();
                rs.next();
                bin1_count = rs.getInt("rowcount");

            } catch (SQLException ex) {
                Logger.getLogger(JsonData.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                pst = con.prepareCall("select COUNT(*) AS rowcount FROM " + db + ".BinStatus where Type regexp \"^Type2_\" and ProductNumber = ''");
                rs = pst.executeQuery();
                rs.next();
                bin2_count = rs.getInt("rowcount");

            } catch (SQLException ex) {
                Logger.getLogger(JsonData.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                pst = con.prepareCall("select COUNT(*) AS rowcount FROM " + db + ".BinStatus where Type regexp \"^Type3_\" and ProductNumber = '' ");
                rs = pst.executeQuery();
                rs.next();
                bin3_count = rs.getInt("rowcount");

            } catch (SQLException ex) {
                Logger.getLogger(JsonData.class.getName()).log(Level.SEVERE, null, ex);
            }

            int bin1_r = 0, bin2_r = 0, bin3_r = 0;

            for (int i = 0; i < bins_packed.size(); i++) {
                JsonObject bin_data = bins_packed.getJsonObject(i).getJsonObject("bin_data");
//                System.out.println("Bin id = " + bin_data.getString("id"));
                String tmp_id = bin_data.getString("id");
                System.out.println("id ++++++============================+++++++++" + tmp_id);
                tmp_id = tmp_id.substring(tmp_id.length() - 1);

                if (tmp_id.compareTo("1") == 0) {
                    bin1_r++;
                } else if (tmp_id.compareTo("2") == 0) {
                    bin2_r++;
                } else {
                    bin3_r++;
                }

            }

            System.out.println("R : 1 = " + bin1_r + " 2 = " + bin2_r + " 3 = " + bin3_r);
            System.out.println("Count : 1 = " + bin1_count + " 2 = " + bin2_count + " 3 = " + bin3_count);

            if (bin1_r > bin1_count || bin2_r > bin2_count || bin3_r > bin3_count) {
                return false;
            }

            File file = new File("Bin_Packing\\" + id);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }

            for (int i = 0; i < bins_packed.size(); i++) {

                JsonObject bin_data = bins_packed.getJsonObject(i).getJsonObject("bin_data");
                System.out.println("Bin id = " + bin_data.getString("id"));
                String tmp_id = bin_data.getString("id");
                tmp_id = tmp_id.substring(tmp_id.length() - 1);

                String image_complete = bins_packed.getJsonObject(i).getString("image_complete");
                file = new File("Bin_Packing\\" + id + "\\Container_" + i +"_"+bin_data.getString("id"));
                if (!file.exists()) {
                    if (file.mkdir()) {
                        System.out.println("Directory is created!");
                    } else {
                        System.out.println("Failed to create directory!");
                    }
                }

                try {
                    InputStream in = new URL(image_complete).openStream();
                    Files.copy(in, Paths.get("Bin_Packing\\" + id + "\\Container_" + i +"_"+bin_data.getString("id")+"\\image_complete_" + i + ".jpg"));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(JsonData.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(JsonData.class.getName()).log(Level.SEVERE, null, ex);
                }

                JsonArray items = bins_packed.getJsonObject(i).getJsonArray("items");

                for (int j = 0; j < items.size(); j++) {

                    System.out.println("Item id = " + items.getJsonObject(j).getString("id"));

                    String image_separated = items.getJsonObject(j).getString("image_separated");
                    String image_sbs = items.getJsonObject(j).getString("image_sbs");

                    try {
                        InputStream in = new URL(image_separated).openStream();
                        Files.copy(in, Paths.get("Bin_Packing\\" + id + "\\Container_" + i+"_"+bin_data.getString("id") +"\\image_separated_" + i + "_" + j + ".jpg"));
                        in = new URL(image_sbs).openStream();
                        Files.copy(in, Paths.get("Bin_Packing\\" + id + "\\Container_" + i+"_"+bin_data.getString("id") +"\\image_sbs_" + i + "_" + j + ".jpg"));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(JsonData.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(JsonData.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                con = dbCon.geConnection();
                try {
                    pst = con.prepareStatement("update " + db + ".BinStatus set ProductId=? , ProductNumber=? where Type regexp \"^Type" + tmp_id + "_\" and ProductNumber = '' order by Type limit 1");

                    pst.setString(1, id);
                    pst.setString(2, String.valueOf(items.size()));
                    pst.executeUpdate();
                    con.close();
                    pst.close();

                } catch (SQLException e) {
                    res = false;

                    e.printStackTrace();
                }

            }

        }
        return res;
    }

}
