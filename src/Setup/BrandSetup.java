/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Setup;

import Data.Brands;
import Data.Supplier;
import Gateway.BrandsGateway;
import dataBase.DBConnection;
import dataBase.DBProperties;
import dataBase.SQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author GanitGenius
 */
public class BrandSetup {

    SQL sql = new SQL();

    DBConnection dbCon = new DBConnection();
    Connection con = dbCon.geConnection();
    PreparedStatement pst;
    ResultSet rs;

    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();

    BrandsGateway brandsGateway = new BrandsGateway();

    public void save(Brands brands) {
        if (isUniqName(brands)) {
            brandsGateway.save(brands);
        }
    }

    public void update(Brands brands) {
        if (isTrueUpdate(brands)) {
            brandsGateway.update(brands);
        } else if (isUniqName(brands)) {
            brandsGateway.update(brands);
        }

    }

    public void delete(Brands brands) {
        if (brandsGateway.isNotUsed(brands)) {
            brandsGateway.delete(brands);
        }
    }

    public boolean isTrueUpdate(Brands brands) {
        
        boolean isTreu = false;
        brands.supplierId = sql.getIdNo(brands.supplierName, brands.supplierId, "Supplier", "SupplierName");
        System.out.println("We are in update");
        try {
            pst = con.prepareStatement("SELECT * FROM " + db + ".Brands WHERE BrandName =? AND SupplierId =? AND Id =?");
            pst.setString(1, brands.brandName);
            pst.setString(2, brands.supplierId);
            pst.setString(3, brands.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                return isTreu;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isTreu;
        
    }

    public boolean isUniqName(Brands brands) {
        
        boolean uniqSupplier = false;
        try {
            pst = con.prepareCall("select * from " + db + ".Brands where BrandName=? and SupplierId=?");
            brands.supplierId = sql.getIdNo(brands.supplierName, brands.supplierId, "Supplier", "SupplierName");
            pst.setString(1, brands.brandName);
            pst.setString(2, brands.supplierId);
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("in not uniq");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucess");
                alert.setHeaderText("ERROR : used");
                alert.setContentText("Brand" + "  '" + brands.brandName + "' " + "Already exist");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
                return uniqSupplier;
            }
            uniqSupplier = true;
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return uniqSupplier;
        
    }

}
