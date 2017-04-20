/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gateway;

import Data.Supplier;
import List.ListSupplier;
import dataBase.DBConnection;
import dataBase.DBProperties;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author GanitGenius
 */
public class SupplierGateway {

    DBConnection dbCon = new DBConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();

    public void save(Supplier supplier) {

        con = dbCon.geConnection();
        if (isUniqSupplierName(supplier)) {
            try {
                con = dbCon.geConnection();
                pst = con.prepareCall("insert into " + db + ".Supplier values(?,?,?,?,?,?,?)");
                pst.setString(1, null);
                pst.setString(2, supplier.supplierName);
                pst.setString(3, supplier.supplierContactNumber);
                pst.setString(4, supplier.supplierAddress);
                pst.setString(5, supplier.supplierDescription);
                pst.setString(6, supplier.creatorId);
                pst.setString(7, LocalDate.now().toString());
                pst.executeUpdate();
                con.close();
                pst.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful");
                alert.setHeaderText("Saved");
                alert.setContentText("Supplier" + "  '" + supplier.supplierName + "' " + "Added successfully");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
            } catch (SQLException ex) {
                Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void view(Supplier supplier) {

        con = dbCon.geConnection();
        try {
            pst = con.prepareCall("select * from " + db + ".Supplier");
            rs = pst.executeQuery();
            while (rs.next()) {
                supplier.id = rs.getString(1);
                supplier.supplierName = rs.getString(2);
                supplier.supplierContactNumber = rs.getString(3);
                supplier.supplierAddress = rs.getString(4);
                supplier.supplierDescription = rs.getString(5);
                supplier.creatorId = rs.getString(6);
                supplier.date = rs.getString(7);
                supplier.supplierDetails.addAll(new ListSupplier(supplier.id, supplier.supplierName, supplier.supplierContactNumber, supplier.supplierAddress, supplier.supplierDescription, supplier.date));
            }
            con.close();
            pst.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void searchView(Supplier supplier) {

        supplier.supplierDetails.clear();
        con = dbCon.geConnection();
        try {
            con = dbCon.geConnection();
            pst = con.prepareCall("select * from " + db + ".Supplier where SupplierName like ? or SupplierPhoneNumber like ? ORDER BY SupplierName");
            pst.setString(1, "%" + supplier.supplierName + "%");
            pst.setString(2, "%" + supplier.supplierName + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                supplier.id = rs.getString(1);
                supplier.supplierName = rs.getString(2);
                supplier.supplierContactNumber = rs.getString(3);
                supplier.supplierAddress = rs.getString(4);
                supplier.supplierDescription = rs.getString(5);
                supplier.creatorId = rs.getString(6);
                supplier.date = rs.getString(7);
                supplier.supplierDetails.addAll(new ListSupplier(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(7)));
            }
            rs.close();
            con.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void selectedView(Supplier supplier) {

        System.out.println("name :" + supplier.supplierName);
        con = dbCon.geConnection();
        try {
            con = dbCon.geConnection();
            pst = con.prepareCall("select * from " + db + ".Supplier where id=?");
            pst.setString(1, supplier.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                supplier.id = rs.getString(1);
                supplier.supplierName = rs.getString(2);
                supplier.supplierContactNumber = rs.getString(3);
                supplier.supplierAddress = rs.getString(4);
                supplier.supplierDescription = rs.getString(5);
                supplier.creatorId = rs.getString(6);
                supplier.date = rs.getString(7);
            }
            rs.close();
            con.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void update(Supplier supplier) {

        System.out.println("We are in update");
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from " + db + ".Supplier where Id=? and SupplierName=?");
            pst.setString(1, supplier.id);
            pst.setString(2, supplier.supplierName);
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("Into the loop");
                updateNow(supplier);
                rs.close();
                pst.close();
                con.close();
                return;
            }
            rs.close();
            con.close();
            pst.close();
            if (isUniqSupplierName(supplier)) {
                System.out.println("Out of the loop");
                updateNow(supplier);
                rs.close();
                con.close();
                pst.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(Supplier supplier) {
        
        con = dbCon.geConnection();
        try {
            con = dbCon.geConnection();
            pst = con.prepareCall("SELECT * FROM " + db + ".Brands WHERE SupplierId=?");
            pst.setString(1, supplier.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sucess");
                alert.setHeaderText("ERROR : Action Denied");
                alert.setContentText("This supplier provides some brands, please delete these brand first! Is it necesssary to delete this supplier ?\nIf not you can update supplier as much you can");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
                return;
            }
            rs.close();
            con.close();
            pst.close();
            deleteParmanently(supplier);
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean isUniqSupplierName(Supplier supplier) {
        
        con = dbCon.geConnection();
        boolean uniqSupplier = false;
        con = dbCon.geConnection();
        try {
            pst = con.prepareCall("select SupplierName from " + db + ".Supplier where SupplierName=?");
            pst.setString(1, supplier.supplierName);
            rs = pst.executeQuery();
            while (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sucess");
                alert.setHeaderText("ERROR : Action Denied");
                alert.setContentText("Supplier" + "  '" + supplier.supplierName + "' " + "Already exist");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
                return uniqSupplier;
            }
            rs.close();
            con.close();
            pst.close();
            uniqSupplier = true;
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return uniqSupplier;
        
    }

    public void updateNow(Supplier supplier) {
        
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("update " + db + ".Supplier set SupplierName=? , SupplierPhoneNumber=?,SupplierAddress=? ,SuplyerDescription=? where Id=?");
            pst.setString(1, supplier.supplierName);
            pst.setString(2, supplier.supplierContactNumber);
            pst.setString(3, supplier.supplierAddress);
            pst.setString(4, supplier.supplierDescription);
            pst.setString(5, supplier.id);
            pst.executeUpdate();
            con.close();
            pst.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setHeaderText("Updated");
            alert.setContentText("Supplier" + "  '" + supplier.supplierName + "' " + "Updated successfully");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteParmanently(Supplier supplier) {
        
        con = dbCon.geConnection();
        try {
            System.out.println("and i am here");
            con = dbCon.geConnection();
            pst = con.prepareCall("delete from " + db + ".Supplier where Id=?");
            pst.setString(1, supplier.id);
            pst.executeUpdate();
            con.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean isUpdate(Supplier supplier) {
        
        con = dbCon.geConnection();
        boolean isUpdate = false;
        try {
            pst = con.prepareStatement("select * from " + db + ".Supplier where Id=?");
            pst.setString(1, supplier.id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdate;
        
    }

    public boolean isNotUse(Supplier supplier) {
        
        con = dbCon.geConnection();
        boolean isNotUse = false;
        try {
            pst = con.prepareStatement("select * from " + db + ".Brands where SupplierId=?");
            pst.setString(1, supplier.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setHeaderText("WARNING : ");
                alert.setContentText("This Supplier supplied  '" + rs.getString(2) + "' brand \n delete brand first");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
                return isNotUse;
            }
            rs.close();
            pst.close();
            con.close();
            isNotUse = true;
        } catch (SQLException ex) {
            Logger.getLogger(SupplierGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isNotUse;
        
    }
    
}
