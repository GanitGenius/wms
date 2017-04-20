/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Setup;

import Data.Category;
import Data.Supplier;
import Gateway.CategoryGateway;
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
public class CategorySetup {

    SQL sql = new SQL();
    CategoryGateway categoryGateway = new CategoryGateway();
    DBConnection dbCon = new DBConnection();
    Connection con = dbCon.geConnection();
    PreparedStatement pst;
    ResultSet rs;

    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();

    public void save(Category category) {
        if (isUniqName(category)) {
            categoryGateway.save(category);
        }
    }

    public void update(Category category) {
        if (checkUpdate(category)) {
            categoryGateway.update(category);
        } else if (isUniqName(category)) {
            categoryGateway.update(category);
        }
    }

    public void delete(Category category) {
        if (categoryGateway.isNotUse(category)) {
            categoryGateway.delete(category);
        }
    }

    public boolean checkUpdate(Category category) {

        boolean isTrueUpdate = false;
        category.brandId = sql.getIdNo(category.brandName, category.brandId, "Brands", "BrandName");
        category.supplierId = sql.getIdNo(category.supplierName, category.supplierId, "Supplier", "SupplierName");

        try {
            pst = con.prepareStatement("select * from " + db + ".Category where CategoryName=? and BrandId=? and SupplierId=? and Id=?");
            pst.setString(1, category.categoryName);
            pst.setString(2, category.brandId);
            pst.setString(3, category.supplierId);
            pst.setString(4, category.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                return isTrueUpdate = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isTrueUpdate;

    }

    public boolean isUniqName(Category category) {

        boolean uniqSupplier = false;
        category.brandId = sql.getIdNo(category.brandName, category.brandId, "Brands", "BrandName");
        category.supplierId = sql.getIdNo(category.supplierName, category.supplierId, "Supplier", "SupplierName");
        try {
            pst = con.prepareCall("select * from " + db + ".Category where CategoryName=? and BrandId=? and SupplierId=?");
            pst.setString(1, category.categoryName);
            pst.setString(2, category.brandId);
            pst.setString(3, category.supplierId);
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("in not uniq");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucess");
                alert.setHeaderText("ERROR : used");
                alert.setContentText("Category" + "  '" + category.categoryName + "' " + "Already exist");
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
