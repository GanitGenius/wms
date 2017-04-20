/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Setup;

import Data.CurrentProduct;
import Gateway.CurrentProductGateway;
import dataBase.DBConnection;
import dataBase.DBProperties;
import dataBase.SQL;
import binlocation.BinChecker;
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
public class CurrentProductSetup {

    DBConnection dbCon = new DBConnection();
    Connection con = dbCon.geConnection();
    PreparedStatement pst;
    ResultSet rs;
    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();

    SQL sql = new SQL();
    CurrentProductGateway currentProductGateway = new CurrentProductGateway();

    public boolean save(CurrentProduct currentProduct) {
        BinChecker binChecker = new BinChecker();
        boolean r = false;
        if (isUniqName(currentProduct)) {
            if (BinChecker.availability(currentProduct.width, currentProduct.height, currentProduct.depth, currentProduct.quantity, currentProduct.productId)) {
                currentProductGateway.save(currentProduct);
                r = true;
            }
        }
        return r;
    }

    public void update(CurrentProduct currentProduct) {

        if (isNotNull(currentProduct)) {
            if (isUpdate(currentProduct)) {
                if (checkUpdateCondition(currentProduct)) {
                    currentProductGateway.update(currentProduct);
                } else if (isUniqName(currentProduct)) {
                    currentProductGateway.update(currentProduct);
                }
            }
        }

    }

    public boolean isUniqName(CurrentProduct currentProduct) {

        System.out.println("WE ARE IN UNIT NAME");
        boolean isUniqname = false;
        try {
            pst = con.prepareStatement("select * from " + db + ".Products where ProductId=?");
            pst.setString(1, currentProduct.productId);
            rs = pst.executeQuery();
            while (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("ERROR : Not Unique");
                alert.setContentText("Product id" + "  '" + currentProduct.productId + "' " + "id not Unique");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
                return isUniqname;
            }
            isUniqname = true;
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProductSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isUniqname;

    }

    public boolean isUpdate(CurrentProduct currentProduct) {

        System.out.println("WE ARE IS IS UPDTE");
        boolean isUpdate = false;
        try {
            pst = con.prepareStatement("select * from " + db + ".Products where Id=? and ProductId=? and ProductName=? and Quantity=? and Description=? and SupplierId=? and BrandId=? "
                    + "and CategoryId=? and UnitId=? and PurchasePrice=? and SellPrice=? and RMAId=? and Date=?");
            pst.setString(1, currentProduct.id);
            pst.setString(2, currentProduct.productId);
            pst.setString(3, currentProduct.productName);
            pst.setString(4, currentProduct.quantity);
            pst.setString(5, currentProduct.description);
            pst.setString(6, currentProduct.supplierId);
            pst.setString(7, currentProduct.brandId);
            pst.setString(8, currentProduct.categoryId);
            pst.setString(9, currentProduct.unitId);
            pst.setString(10, currentProduct.purchasePrice);
            pst.setString(11, currentProduct.sellPrice);
            pst.setString(12, currentProduct.rmaId);
            pst.setString(13, currentProduct.date);
            rs = pst.executeQuery();
            while (rs.next()) {
                return isUpdate;
            }
            isUpdate = true;
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isUpdate;

    }

    public boolean checkUpdateCondition(CurrentProduct currentProduct) {

        boolean isTrueUpdate = false;
        if (isUpdate(currentProduct)) {
            try {
                pst = con.prepareStatement("select * from " + db + ".Products where id=? and ProductId=?");
                pst.setString(1, currentProduct.id);
                pst.setString(2, currentProduct.productId);
                rs = pst.executeQuery();
                while (rs.next()) {

                    return isTrueUpdate = true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(CurrentProduct.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return isTrueUpdate;

    }

    public boolean isNotNull(CurrentProduct currentProduct) {

        boolean isNotNull = false;
        if (currentProduct.productId.isEmpty() || currentProduct.sellPrice.isEmpty() || currentProduct.quantity.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucess");
            alert.setHeaderText("ERROR : Null Found");
            alert.setContentText("Please fill requrer field");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            return isNotNull;
        }
        return isNotNull;

    }

    public Object delete(CurrentProduct currentProduct) {
        if (currentProductGateway.isNotSold(currentProduct)) {
            currentProductGateway.delete(currentProduct);
        }
        return currentProduct;

    }

}
