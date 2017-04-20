/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartWMS;

import dataBase.DBConnection;
import dataBase.DBModel;
import dataBase.DBProperties;
import dataBase.SQL;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author GanitGenius
 */
public class SmartWMS extends Application {

    SQL sql = new SQL();

    DBConnection dbCon = new DBConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();

    public SmartWMS() {

        DBModel model = new DBModel();
        model.createDataBase();

        con = dbCon.geConnection();
//        brands.supplierId = sql.getIdNo(brands.supplierName, brands.supplierId, "Supplier", "SupplierName");

        try {
            ResultSet resultSet = con.getMetaData().getCatalogs();

            while (resultSet.next()) {
                
                String databaseName = resultSet.getString(1);
//                System.out.println(databaseName);
                if (databaseName.equals("superwms")) {
                    Connection con2 = dbCon.geConnection();
                    for (int i = 1; i <= 3; i++) {
                        for (int j = 1; j < 101; j++) {
                            pst = con2.prepareStatement("insert into " + db + ".BinStatus values(?,'' ,'' )");
                            pst.setString(1, "type" + i+"_"+j );
                            pst.executeUpdate();
                        }
                    }
                    break;
                }
            }
            resultSet.close();

        } catch (SQLException e) {
//            e.printStackTrace();
        }

    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Welcome to SmartWMS Login");
            primaryStage.getIcons().add(new Image("/image/icon.png"));
            primaryStage.setMaximized(false);
            primaryStage.setMinHeight(500.0);
            primaryStage.setMinWidth(850.0);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(SmartWMS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
