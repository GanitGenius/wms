/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gateway;

import Data.CurrentProduct;
import List.ListProduct;
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
public class CurrentProductGateway {

    DBConnection dbCon = new DBConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();

    SQL sql = new SQL();

    public void save(CurrentProduct currentProduct) {

        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("insert into " + db + ".Products values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, null);
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
            pst.setString(13, currentProduct.userId);
            pst.setString(14, currentProduct.date);
            pst.executeUpdate();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProduct.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void view(CurrentProduct currentProduct) {

        currentProduct.currentProductList.clear();
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("SELECT SQL_NO_CACHE * FROM " + db + ".Products");
            rs = pst.executeQuery();
            while (rs.next()) {
                currentProduct.id = rs.getString(1);
                currentProduct.productId = rs.getString(2);
                currentProduct.productName = rs.getString(3);
                currentProduct.quantity = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.supplierId = rs.getString(6);
                currentProduct.brandId = rs.getString(7);
                currentProduct.categoryId = rs.getString(8);
                currentProduct.unitId = rs.getString(9);
                currentProduct.purchasePrice = rs.getString(10);
                currentProduct.sellPrice = rs.getString(11);
                currentProduct.rmaId = rs.getString(12);
                currentProduct.userId = rs.getString(13);
                currentProduct.date = rs.getString(14);
                currentProduct.supplierName = sql.getName(currentProduct.supplierId, currentProduct.supplierName, "Supplier");
                currentProduct.brandName = sql.getName(currentProduct.brandId, currentProduct.brandName, "Brands");
                currentProduct.categoryName = sql.getName(currentProduct.categoryId, currentProduct.categoryName, "Category");
                currentProduct.unitName = sql.getName(currentProduct.unitId, currentProduct.unitName, "Unit");
                currentProduct.rmaName = sql.getName(currentProduct.rmaId, currentProduct.rmaName, "RMA");
                currentProduct.userName = sql.getName(currentProduct.userId, currentProduct.userName, "User");
                currentProduct.currentProductList.addAll(new ListProduct(currentProduct.id, currentProduct.productId, currentProduct.productName, currentProduct.quantity, currentProduct.description, currentProduct.supplierName, currentProduct.brandName, currentProduct.categoryName, currentProduct.unitName, currentProduct.purchasePrice, currentProduct.sellPrice, currentProduct.rmaName, currentProduct.userName, currentProduct.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void selectedView(CurrentProduct currentProduct) {

        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from " + db + ".Products where id=?");
            pst.setString(1, currentProduct.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                currentProduct.productId = rs.getString(2);
                currentProduct.productName = rs.getString(3);
                currentProduct.quantity = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.supplierId = rs.getString(6);
                currentProduct.brandId = rs.getString(7);
                currentProduct.categoryId = rs.getString(8);
                currentProduct.unitId = rs.getString(9);
                currentProduct.purchasePrice = rs.getString(10);
                currentProduct.sellPrice = rs.getString(11);
                currentProduct.rmaId = rs.getString(12);
                currentProduct.userId = rs.getString(13);
                currentProduct.date = rs.getString(14);
                currentProduct.supplierName = sql.getName(currentProduct.supplierId, currentProduct.supplierName, "Supplier");
                currentProduct.brandName = sql.getName(currentProduct.brandId, currentProduct.brandName, "Brands");
                currentProduct.categoryName = sql.getName(currentProduct.categoryId, currentProduct.categoryName, "Category");
                currentProduct.unitName = sql.getName(currentProduct.unitId, currentProduct.unitName, "Unit");
                currentProduct.rmaName = sql.getName(currentProduct.rmaId, currentProduct.rmaName, "RMA");
                currentProduct.userName = sql.getName(currentProduct.userId, currentProduct.userName, "User");
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void viewFistTen(CurrentProduct currentProduct) {

        con = dbCon.geConnection();
        currentProduct.currentProductList.clear();
        try {
            pst = con.prepareStatement("select * from " + db + ".Products limit 0,15");
            rs = pst.executeQuery();
            while (rs.next()) {
                currentProduct.id = rs.getString(1);
                currentProduct.productId = rs.getString(2);
                currentProduct.productName = rs.getString(3);
                currentProduct.quantity = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.supplierId = rs.getString(6);
                currentProduct.brandId = rs.getString(7);
                currentProduct.categoryId = rs.getString(8);
                currentProduct.unitId = rs.getString(9);
                currentProduct.purchasePrice = rs.getString(10);
                currentProduct.sellPrice = rs.getString(11);
                currentProduct.rmaId = rs.getString(12);
                currentProduct.userId = rs.getString(13);
                currentProduct.date = rs.getString(14);
                currentProduct.supplierName = sql.getName(currentProduct.supplierId, currentProduct.supplierName, "Supplier");
                currentProduct.brandName = sql.getName(currentProduct.brandId, currentProduct.brandName, "Brands");
                currentProduct.categoryName = sql.getName(currentProduct.categoryId, currentProduct.categoryName, "Category");
                currentProduct.unitName = sql.getName(currentProduct.unitId, currentProduct.unitName, "Unit");
                currentProduct.rmaName = sql.getName(currentProduct.rmaId, currentProduct.rmaName, "RMA");
                currentProduct.userName = sql.getName(currentProduct.userId, currentProduct.userName, "User");
                currentProduct.currentProductList.addAll(new ListProduct(currentProduct.id, currentProduct.productId, currentProduct.productName, currentProduct.quantity, currentProduct.description, currentProduct.supplierName, currentProduct.brandName, currentProduct.categoryName, currentProduct.unitName, currentProduct.purchasePrice, currentProduct.sellPrice, currentProduct.rmaName, currentProduct.userName, currentProduct.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void searchView(CurrentProduct currentProduct) {

        con = dbCon.geConnection();
        currentProduct.currentProductList.clear();
        try {
            pst = con.prepareStatement("select * from " + db + ".Products where ProductId like ? or ProductName like ?");
            pst.setString(1, "%" + currentProduct.productId + "%");
            pst.setString(2, "%" + currentProduct.productId + "%");
            rs = pst.executeQuery();
            while (rs.next()) {
                currentProduct.id = rs.getString(1);
                currentProduct.productId = rs.getString(2);
                currentProduct.productName = rs.getString(3);
                currentProduct.quantity = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.supplierId = rs.getString(6);
                currentProduct.brandId = rs.getString(7);
                currentProduct.categoryId = rs.getString(8);
                currentProduct.unitId = rs.getString(9);
                currentProduct.purchasePrice = rs.getString(10);
                currentProduct.sellPrice = rs.getString(11);
                currentProduct.rmaId = rs.getString(12);
                currentProduct.userId = rs.getString(13);
                currentProduct.date = rs.getString(14);
                currentProduct.supplierName = sql.getName(currentProduct.supplierId, currentProduct.supplierName, "Supplier");
                currentProduct.brandName = sql.getName(currentProduct.brandId, currentProduct.brandName, "Brands");
                currentProduct.categoryName = sql.getName(currentProduct.categoryId, currentProduct.categoryName, "Category");
                currentProduct.unitName = sql.getName(currentProduct.unitId, currentProduct.unitName, "Unit");
                currentProduct.rmaName = sql.getName(currentProduct.rmaId, currentProduct.rmaName, "RMA");
                currentProduct.userName = sql.getName(currentProduct.userId, currentProduct.userName, "User");
                currentProduct.currentProductList.addAll(new ListProduct(currentProduct.id, currentProduct.productId, currentProduct.productName, currentProduct.quantity, currentProduct.description, currentProduct.supplierName, currentProduct.brandName, currentProduct.categoryName, currentProduct.unitName, currentProduct.purchasePrice, currentProduct.sellPrice, currentProduct.rmaName, currentProduct.userName, currentProduct.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void searchBySupplier(CurrentProduct currentProduct) {

        con = dbCon.geConnection();
        currentProduct.currentProductList.clear();
        currentProduct.supplierId = sql.getIdNo(currentProduct.supplierName, currentProduct.supplierId, "Supplier", "SupplierName");
        try {
            pst = con.prepareStatement("select * from " + db + ".Products where SupplierId=?");
            pst.setString(1, currentProduct.supplierId);
            rs = pst.executeQuery();
            while (rs.next()) {
                currentProduct.id = rs.getString(1);
                currentProduct.productId = rs.getString(2);
                currentProduct.productName = rs.getString(3);
                currentProduct.quantity = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.supplierId = rs.getString(6);
                currentProduct.brandId = rs.getString(7);
                currentProduct.categoryId = rs.getString(8);
                currentProduct.unitId = rs.getString(9);
                currentProduct.purchasePrice = rs.getString(10);
                currentProduct.sellPrice = rs.getString(11);
                currentProduct.rmaId = rs.getString(12);
                currentProduct.userId = rs.getString(13);
                currentProduct.date = rs.getString(14);
                currentProduct.supplierName = sql.getName(currentProduct.supplierId, currentProduct.supplierName, "Supplier");
                currentProduct.brandName = sql.getName(currentProduct.brandId, currentProduct.brandName, "Brands");
                currentProduct.categoryName = sql.getName(currentProduct.categoryId, currentProduct.categoryName, "Category");
                currentProduct.unitName = sql.getName(currentProduct.unitId, currentProduct.unitName, "Unit");
                currentProduct.rmaName = sql.getName(currentProduct.rmaId, currentProduct.rmaName, "RMA");
                currentProduct.userName = sql.getName(currentProduct.userId, currentProduct.userName, "User");
                currentProduct.currentProductList.addAll(new ListProduct(currentProduct.id, currentProduct.productId, currentProduct.productName, currentProduct.quantity, currentProduct.description, currentProduct.supplierName, currentProduct.brandName, currentProduct.categoryName, currentProduct.unitName, currentProduct.purchasePrice, currentProduct.sellPrice, currentProduct.rmaName, currentProduct.userName, currentProduct.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProductGateway.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void searchByBrand(CurrentProduct currentProduct) {

        con = dbCon.geConnection();
        currentProduct.currentProductList.clear();
        currentProduct.supplierId = sql.getIdNo(currentProduct.supplierName, currentProduct.supplierId, "Supplier", "SupplierName");
        currentProduct.brandId = sql.getBrandID(currentProduct.supplierId, currentProduct.brandId, currentProduct.brandName);
        System.out.println("Brand ID: " + currentProduct.brandId);
        try {
            pst = con.prepareStatement("select * from " + db + ".Products where BrandId=?");
            pst.setString(1, currentProduct.brandId);
            rs = pst.executeQuery();
            while (rs.next()) {
                currentProduct.id = rs.getString(1);
                currentProduct.productId = rs.getString(2);
                currentProduct.productName = rs.getString(3);
                currentProduct.quantity = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.supplierId = rs.getString(6);
                currentProduct.brandId = rs.getString(7);
                currentProduct.categoryId = rs.getString(8);
                currentProduct.unitId = rs.getString(9);
                currentProduct.purchasePrice = rs.getString(10);
                currentProduct.sellPrice = rs.getString(11);
                currentProduct.rmaId = rs.getString(12);
                currentProduct.userId = rs.getString(13);
                currentProduct.date = rs.getString(14);
                currentProduct.supplierName = sql.getName(currentProduct.supplierId, currentProduct.supplierName, "Supplier");
                currentProduct.brandName = sql.getName(currentProduct.brandId, currentProduct.brandName, "Brands");
                currentProduct.categoryName = sql.getName(currentProduct.categoryId, currentProduct.categoryName, "Category");
                currentProduct.unitName = sql.getName(currentProduct.unitId, currentProduct.unitName, "Unit");
                currentProduct.rmaName = sql.getName(currentProduct.rmaId, currentProduct.rmaName, "RMA");
                currentProduct.userName = sql.getName(currentProduct.userId, currentProduct.userName, "User");
                currentProduct.currentProductList.addAll(new ListProduct(currentProduct.id, currentProduct.productId, currentProduct.productName, currentProduct.quantity, currentProduct.description, currentProduct.supplierName, currentProduct.brandName, currentProduct.categoryName, currentProduct.unitName, currentProduct.purchasePrice, currentProduct.sellPrice, currentProduct.rmaName, currentProduct.userName, currentProduct.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProductGateway.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void searchByCategory(CurrentProduct currentProduct) {
        
        con = dbCon.geConnection();
        currentProduct.currentProductList.clear();
        currentProduct.supplierId = sql.getIdNo(currentProduct.supplierName, currentProduct.supplierId, "Supplier", "SupplierName");
        currentProduct.brandId = sql.getBrandID(currentProduct.supplierId, currentProduct.brandId, currentProduct.brandName);
        currentProduct.categoryId = sql.getCategoryId(currentProduct.supplierId, currentProduct.brandId, currentProduct.categoryId, currentProduct.categoryName);
        try {
            pst = con.prepareStatement("select * from " + db + ".Products where CategoryId=?");
            pst.setString(1, currentProduct.categoryId);
            rs = pst.executeQuery();
            while (rs.next()) {
                currentProduct.id = rs.getString(1);
                currentProduct.productId = rs.getString(2);
                currentProduct.productName = rs.getString(3);
                currentProduct.quantity = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.supplierId = rs.getString(6);
                currentProduct.brandId = rs.getString(7);
                currentProduct.categoryId = rs.getString(8);
                currentProduct.unitId = rs.getString(9);
                currentProduct.purchasePrice = rs.getString(10);
                currentProduct.sellPrice = rs.getString(11);
                currentProduct.rmaId = rs.getString(12);
                currentProduct.userId = rs.getString(13);
                currentProduct.date = rs.getString(14);
                currentProduct.supplierName = sql.getName(currentProduct.supplierId, currentProduct.supplierName, "Supplier");
                currentProduct.brandName = sql.getName(currentProduct.brandId, currentProduct.brandName, "Brands");
                currentProduct.categoryName = sql.getName(currentProduct.categoryId, currentProduct.categoryName, "Category");
                currentProduct.unitName = sql.getName(currentProduct.unitId, currentProduct.unitName, "Unit");
                currentProduct.rmaName = sql.getName(currentProduct.rmaId, currentProduct.rmaName, "RMA");
                currentProduct.userName = sql.getName(currentProduct.userId, currentProduct.userName, "User");
                currentProduct.currentProductList.addAll(new ListProduct(currentProduct.id, currentProduct.productId, currentProduct.productName, currentProduct.quantity, currentProduct.description, currentProduct.supplierName, currentProduct.brandName, currentProduct.categoryName, currentProduct.unitName, currentProduct.purchasePrice, currentProduct.sellPrice, currentProduct.rmaName, currentProduct.userName, currentProduct.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProductGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void searchByRMA(CurrentProduct currentProduct) {
        
        con = dbCon.geConnection();
        currentProduct.currentProductList.clear();
        try {
            pst = con.prepareStatement("select * from " + db + ".Products where RMAId=?");
            pst.setString(1, currentProduct.rmaId);
            rs = pst.executeQuery();
            while (rs.next()) {
                currentProduct.id = rs.getString(1);
                currentProduct.productId = rs.getString(2);
                currentProduct.productName = rs.getString(3);
                currentProduct.quantity = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.supplierId = rs.getString(6);
                currentProduct.brandId = rs.getString(7);
                currentProduct.categoryId = rs.getString(8);
                currentProduct.unitId = rs.getString(9);
                currentProduct.purchasePrice = rs.getString(10);
                currentProduct.sellPrice = rs.getString(11);
                currentProduct.rmaId = rs.getString(12);
                currentProduct.userId = rs.getString(13);
                currentProduct.date = rs.getString(14);
                currentProduct.supplierName = sql.getName(currentProduct.supplierId, currentProduct.supplierName, "Supplier");
                currentProduct.brandName = sql.getName(currentProduct.brandId, currentProduct.brandName, "Brands");
                currentProduct.categoryName = sql.getName(currentProduct.categoryId, currentProduct.categoryName, "Category");
                currentProduct.unitName = sql.getName(currentProduct.unitId, currentProduct.unitName, "Unit");
                currentProduct.rmaName = sql.getName(currentProduct.rmaId, currentProduct.rmaName, "RMA");
                currentProduct.userName = sql.getName(currentProduct.userId, currentProduct.userName, "User");
                currentProduct.currentProductList.addAll(new ListProduct(currentProduct.id, currentProduct.productId, currentProduct.productName, currentProduct.quantity, currentProduct.description, currentProduct.supplierName, currentProduct.brandName, currentProduct.categoryName, currentProduct.unitName, currentProduct.purchasePrice, currentProduct.sellPrice, currentProduct.rmaName, currentProduct.userName, currentProduct.date));
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProductGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void sView(CurrentProduct currentProduct) {
        
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from " + db + ".Products where ProductId=?");
            pst.setString(1, currentProduct.productId);
            rs = pst.executeQuery();
            while (rs.next()) {
                currentProduct.id = rs.getString(1);
                currentProduct.productId = rs.getString(2);
                currentProduct.productName = rs.getString(3);
                currentProduct.quantity = rs.getString(4);
                currentProduct.description = rs.getString(5);
                currentProduct.supplierId = rs.getString(6);
                currentProduct.brandId = rs.getString(7);
                currentProduct.categoryId = rs.getString(8);
                currentProduct.unitId = rs.getString(9);
                currentProduct.purchasePrice = rs.getString(10);
                currentProduct.sellPrice = rs.getString(11);
                currentProduct.rmaId = rs.getString(12);
                currentProduct.userId = rs.getString(13);
                currentProduct.date = rs.getString(14);
                currentProduct.supplierName = sql.getName(currentProduct.supplierId, currentProduct.supplierName, "Supplier");
                currentProduct.brandName = sql.getName(currentProduct.brandId, currentProduct.brandName, "Brands");
                currentProduct.categoryName = sql.getName(currentProduct.categoryId, currentProduct.categoryName, "Category");
                currentProduct.unitName = sql.getName(currentProduct.unitId, currentProduct.unitName, "Unit");
                currentProduct.rmaName = sql.getName(currentProduct.rmaId, currentProduct.rmaName, "RMA");
                currentProduct.userName = sql.getName(currentProduct.userId, currentProduct.userName, "User");
                currentProduct.rmaDayesss = sql.getDayes(currentProduct.rmaDayesss, currentProduct.rmaId);
                long dateRMA = Long.parseLong(currentProduct.rmaDayesss);
                currentProduct.warrentyVoidDate = LocalDate.now().plusDays(dateRMA).toString();
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    public void cbSupplier(CurrentProduct currentProduct) {
        
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from " + db + ".Supplier");
            rs = pst.executeQuery();
            while (rs.next()) {
                currentProduct.supplierList = rs.getString(2);
            }
            pst.close();
            con.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    public void update(CurrentProduct currentProduct) {
        
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("update " + db + ".Products set ProductId=?, ProductName=?, Quantity=?, Description=?, "
                    + "SupplierId=?, BrandId=?, CategoryId=?,"
                    + " UnitId=?, PurchasePrice=?, SellPrice=?, RMAId=?, Date=?  where Id=?");
            pst.setString(1, currentProduct.productId);
            pst.setString(2, currentProduct.productName);
            pst.setString(3, currentProduct.quantity);
            pst.setString(4, currentProduct.description);
            pst.setString(5, currentProduct.supplierId);
            pst.setString(6, currentProduct.brandId);
            pst.setString(7, currentProduct.categoryId);
            pst.setString(8, currentProduct.unitId);
            pst.setString(9, currentProduct.purchasePrice);
            pst.setString(10, currentProduct.sellPrice);
            pst.setString(11, currentProduct.rmaId);
            pst.setString(12, currentProduct.date);
            pst.setString(13, currentProduct.id);
            pst.executeUpdate();
            pst.close();
            con.close();
            rs.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setHeaderText("Updated");
            alert.setContentText("Category" + "  '" + currentProduct.productId + "' " + "Update Sucessfuly");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void delete(CurrentProduct currentProduct) {
        
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("delete from " + db + ".Products where id=?");
            pst.setString(1, currentProduct.id);
            pst.executeUpdate();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public boolean isNotSold(CurrentProduct currentProduct) {
        
        con = dbCon.geConnection();
        boolean isNotSold = false;
        try {
            pst = con.prepareStatement("select * from " + db + ".Sell where ProductId=?");
            pst.setString(1, currentProduct.id);
            rs = pst.executeQuery();
            while (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("WARNING : ");
                alert.setContentText("This product has been sold, you can't delete it");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.showAndWait();
                return isNotSold;
            }
            rs.close();
            pst.close();
            con.close();
            isNotSold = true;
        } catch (SQLException ex) {
            Logger.getLogger(CurrentProductGateway.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isNotSold;
        
    }

}
