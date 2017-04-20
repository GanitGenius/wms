/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binlocation;

import binlocation.JsonData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;
import org.json.JSONException;
//;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class BinChecker {

    /**
     *      * @param args the command line arguments      
     */
    public static boolean availability(String w, String h, String d, String q, String id) {//throws MalformedURLException, ProtocolException, IOException, JSONException {
        //  RestTemplate restTemplate = new RestTemplate();
        JSONObject json = new JSONObject();
        System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");

//SET BINS
//List<Map<String , String>> bins  = new ArrayList<Map<String,String>>();
        List<Map<String, String>> bins = new ArrayList<Map<String, String>>();
        Map<String, String> bin1 = new HashMap<String, String>();
        bin1.put("w", "11");
        bin1.put("h", "9");
        bin1.put("d", "7");
        bin1.put("max_wg", "0");
        bin1.put("id", "Bin1");
        bins.add(bin1);
        Map<String, String> bin2 = new HashMap<String, String>();
        bin2.put("w", "9");
        bin2.put("h", "7");
        bin2.put("d", "5");
        bin2.put("max_wg", "0");
        bin2.put("id", "Bin2");
        bins.add(bin2);
        Map<String, String> bin3 = new HashMap<String, String>();
        bin3.put("w", "9");
        bin3.put("h", "7");
        bin3.put("d", "5");
        bin3.put("max_wg", "0");
        bin3.put("id", "Bin2");
        bins.add(bin2);

//SET ITEMS
        List<Map<String, String>> items = new ArrayList<Map<String, String>>();
        Map<String, String> item1 = new HashMap<String, String>();
        item1.put("w", w);
        item1.put("h", h);
        item1.put("d", d);
        item1.put("q", q);
        item1.put("vr", "1");
        item1.put("wg", "0");
        item1.put("id", "Item1");
        items.add(item1);


//SET PARAMETERS
        Map<String, String> params = new HashMap<String, String>();
        params.put("images_background_color", "255,255,255");
        params.put("images_bin_border_color", "59,59,59");
        params.put("images_bin_fill_color", "230,230,230");
        params.put("images_item_border_color", "214,79,79");
        params.put("images_item_fill_color", "177,14,14");
        params.put("images_item_back_border_color", "215,103,103");
        params.put("images_sbs_last_item_fill_color", "99,93,93");
        params.put("images_sbs_last_item_border_color", "145,133,133");
        params.put("images_width", "100");
        params.put("images_height", "100");
        params.put("images_source", "file");
        params.put("images_sbs", "1");
        params.put("stats", "1");
        params.put("item_coordinates", "1");
        params.put("images_complete", "1");
        params.put("images_separated", "1");

        String response_json = "";

        try {
            //ADD ELEMENTS TO JSON
            json.put("username", "trymailingit");
            json.put("api_key", "de446860cdeff572fe0cae809ce99637");
//        json.put("api_key", "de446860cdeff572fe0cae809ce99637");
            json.put("items", items);
            json.put("bins", bins);
            json.put("params", params);
//CALL QUERY
            HttpURLConnection conn;
            URL addr = new URL("http://eu.api.3dbinpacking.com/packer/packIntoMany");
            conn = (HttpURLConnection) addr.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.connect();
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

// prepare POST body 
            String query = "query=" + json.toString();

            osw.write(query);
            osw.flush();
            osw.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            response_json = br.readLine();
            System.out.println(response_json);
        } catch (JSONException ex) {
            Logger.getLogger(BinChecker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(BinChecker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BinChecker.class.getName()).log(Level.SEVERE, null, ex);
        }

//DO SOMETHING COOL WITH THE RESPONSE
        JsonData jsonData = new JsonData();
        boolean ans = jsonData.check(response_json, id);
        if(!ans){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Insufficient storage");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        }
        return ans;
    }
}
