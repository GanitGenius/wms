/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gateway;

import Data.Category;
import Data.Supplier;
import List.ListCategory;
import dataBase.DBConnection;
import dataBase.DBProperties;
import dataBase.SQL;
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
public class CategoryGateway {

    SQL sql = new SQL();
    DBConnection dbCon = new DBConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    
    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();

    public void save(Category category) {
        
        con = dbCon.geConnection();
        category.brandName = sql.getIdNo(category.brandName, category.brandId, "Brands", "BrandName");
        category.brandId = sql.getIdNo(category.brandName, category.brandId, "Brands", "BrandName");
        category.supplierId = sql.getIdNo(category.supplierName, category.supplierId, "Supplier", "SupplierName");
        try {
            pst = con.prepareStatement("insert into "+db+".Category values(?,?,?,?,?,?,?)");
            pst.setString(1, null);
            pst.setString(2, category.categoryName);
            pst.setString(3, category.categoryDescription);
            pst.setString(4, category.brandId);
            pst.setString(5, category.supplierId);
            pst.setString(6, category.creatorId);
            pst.setString(7, LocalDate.now().toString());
            pst.executeUpdate();
            pst.close();
            con.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucess");
            alert.setHeaderText("Sucess : save sucess");
            alert.setContentText("Category" + "  '" + category.categoryName + "' " + "Added Sucessfuly");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void view(Category category) {
        
        con = dbCon.geConnection();
        try {
            con = dbCon.geConnection();
            pst = con.prepareCall("select * from "+db+".Category");
            rs = pst.executeQuery();
            while (rs.next()) {
                category.id = rs.getString(1);
                category.categoryName = rs.getString(2);
                category.categoryDescription = rs.getString(3);
                category.brandId = rs.getString(4);
                category.supplierId = rs.getString(5);
                category.creatorId = rs.getString(6);
                category.date = rs.getString(7);
                category.brandName = sql.getName(category.brandId, category.brandName, "Brands");
                category.supplierName = sql.getName(category.supplierId, category.supplierName, "Supplier");
                category.creatorName = sql.getName(category.creatorId, category.categoryName, "User");
                category.categoryDetails.addAll(new ListCategory(category.id, category.categoryName, category.categoryDescription, category.brandName, category.supplierName, category.creatorName, category.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void selectedView(Category category) {
        
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from "+db+".Category where Id=?");
            pst.setString(1, category.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                category.id = rs.getString(1);
                category.categoryName = rs.getString(2);
                category.categoryDescription = rs.getString(3);
                category.brandId = rs.getString(4);
                category.supplierId = rs.getString(5);
                category.brandName = sql.getName(category.brandId, category.brandName, "Brands");
                category.supplierName = sql.getName(category.supplierId, category.supplierName, "Supplier");
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void brandView(Category category) {
        
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from "+db+".Brands where SupplierId=?");
            pst.setString(1, category.supplierId);
            rs = pst.executeQuery();
            while (rs.next()) {
                category.brandName = rs.getString(2);
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void searchView(Category category) {
        
        con = dbCon.geConnection();
        category.categoryDetails.clear();
        try {
            pst = con.prepareStatement("select * from "+db+".Category where CategoryName like ? ORDER BY CategoryName");

            pst.setString(1, "%" + category.categoryName + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                category.id = rs.getString(1);
                category.categoryName = rs.getString(2);
                category.categoryDescription = rs.getString(3);
                category.brandId = rs.getString(4);
                category.supplierId = rs.getString(5);
                category.creatorId = rs.getString(6);
                category.date = rs.getString(7);
                category.brandName = sql.getName(category.brandId, category.brandName, "Brands");
                category.supplierName = sql.getName(category.supplierId, category.supplierName, "Supplier");
                category.creatorName = sql.getName(category.creatorId, category.categoryName, "User");
                category.categoryDetails.addAll(new ListCategory(category.id, category.categoryName, category.categoryDescription, category.brandName, category.supplierName, category.creatorName, category.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    public void update(Category category) {
        
        con = dbCon.geConnection();
        category.brandId = sql.getIdNo(category.brandName, category.brandId, "Brands", "BrandName");
        category.supplierId = sql.getIdNo(category.supplierName, category.supplierId, "Supplier", "SupplierName");
        try {
            pst = con.prepareStatement("update "+db+".Category set CategoryName=? , CategoryDescription=?,BrandId=?,SupplierId=? where Id=?");
            pst.setString(1, category.categoryName);
            pst.setString(2, category.categoryDescription);
            pst.setString(3, category.brandId);
            pst.setString(4, category.supplierId);
            pst.setString(5, category.id);
            pst.executeUpdate();
            pst.close();
            con.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setHeaderText("Updated");
            alert.setContentText("Category" + "  '" + category.categoryName + "' " + "Update Successfully");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(Category category) {
        
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("delete from "+db+".Category where Id=?");
            pst.setString(1, category.id);
            pst.executeUpdate();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    public boolean isNotUse(Category category) {
        
        con = dbCon.geConnection();
        boolean isNotUse = false;
        try {
            pst = con.prepareCall("select * from "+db+".Products where CategoryId=?");
            pst.setString(1, category.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("ERROE : Already exist ");
                alert.setContentText("Category" + "  '" + rs.getString(2) + "' " + "Already exist");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
                return isNotUse;
            }
            pst.close();
            rs.close();
            con.close();
            isNotUse = true;
        } catch (SQLException ex) {
            Logger.getLogger(CategoryGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isNotUse;
        
    }

}
