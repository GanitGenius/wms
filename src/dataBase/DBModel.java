/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author GanitGenius
 */
public class DBModel {

    Properties properties = new Properties();
    InputStream inputStream;
    String db;

    public void loadPropertiesFile() {

        try {
            inputStream = new FileInputStream("database.properties");
            properties.load(inputStream);
            db = properties.getProperty("db");
        } catch (IOException e) {
            System.out.println("DDDD");
        }

    }

    PreparedStatement pst;

    public void createDataBase() {
        
        loadPropertiesFile();
        DBConnection con = new DBConnection();
        try {
            pst = con.mkDataBase().prepareStatement("create database if not exists " + db + " DEFAULT CHARACTER SET utf8 \n"
                    + "  DEFAULT COLLATE utf8_general_ci");
            pst.execute();
            pst = con.mkDataBase().prepareStatement("CREATE TABLE if not exists " + db + ".`User` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `UsrName` VARCHAR(20) NOT NULL,\n"
                    + "  `FullName` VARCHAR(100) ,\n"
                    + "  `EmailAddress` VARCHAR(100) ,\n"
                    + "  `ContactNumber` VARCHAR(100) ,\n"
                    + "  `Salary` double DEFAULT NULL,\n"
                    + "  `Address` text,\n"
                    + "  `Password` VARCHAR(600),\n"
                    + "  `Status` tinyint(1) NOT NULL DEFAULT '0',\n"
                    + "  `UserImage` mediumblob,\n"
                    + "  `Date` date NOT NULL,\n"
                    + "  `CreatorId` int(11),\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC));");
            pst.execute();
            pst = con.mkDataBase().prepareStatement("CREATE TABLE if not exists " + db + ".`UserPermission` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `AddProduct` tinyint(1) DEFAULT NULL,\n"
                    + "  `AddSupplier` tinyint(1) DEFAULT NULL,\n"
                    + "  `AddBrand` tinyint(1) DEFAULT NULL,\n"
                    + "  `AddCategory` tinyint(1) DEFAULT NULL,\n"
                    + "  `AddUnit` tinyint(1) DEFAULT NULL,\n"
                    + "  `AddCustomer` tinyint(1) DEFAULT NULL,\n"
                    + "  `UpdateProduct` tinyint(1) DEFAULT NULL,\n"
                    + "  `UpdateSupplier` tinyint(1) DEFAULT NULL,\n"
                    + "  `UpdateBrand` tinyint(1) DEFAULT NULL,\n"
                    + "  `UpdateCategory` tinyint(1) DEFAULT NULL,\n"
                    + "  `UpdateUnit` tinyint(1) DEFAULT NULL,\n"
                    + "  `UpdateCustomer` tinyint(1) DEFAULT NULL,\n"
                    + "  `RMAManage` tinyint(1) DEFAULT NULL,\n"
                    + "  `SellProduct` tinyint(1) DEFAULT NULL,\n"
                    + "  `ProvideDiscount` tinyint(1) DEFAULT NULL,\n"
                    + "  `EmployeManage` tinyint(1) DEFAULT NULL,\n"
                    + "  `OrgManage` tinyint(1) DEFAULT NULL,\n"
                    + "  `ChangeOwnPass` tinyint(1) DEFAULT NULL,\n"
                    + "  `UserId` int(11) NOT NULL, \n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC));");
            pst.execute();
            pst = con.mkDataBase().prepareStatement("CREATE TABLE if not exists " + db + ".`Organize` (\n"
                    + "  `Id` int(1) NOT NULL ,\n"
                    + "  `OrgName` varchar(100) DEFAULT NULL,\n"
                    + "  `OrgWeb` varchar(100) DEFAULT NULL,\n"
                    + "  `OrgContactNumbers` text DEFAULT NULL,\n"
                    + "  `OrgEmailId` text DEFAULT NULL,\n"
                    + "  `OrgPassword` text DEFAULT NULL,\n"
                    + "  `OrgSmtpServer` text DEFAULT NULL,\n"
                    + "  `OrgContactAddress` text DEFAULT NULL,\n"
                    + "  `OrgLogo` mediumblob,\n"
                    + "  `UserId` int(11) DEFAULT NULL,\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC));");
            pst.execute();

            pst = con.mkDataBase().prepareStatement("CREATE TABLE if not exists " + db + ".`Supplier` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `SupplierName` varchar(100) DEFAULT NULL,\n"
                    + "  `SupplierPhoneNumber` text DEFAULT NULL,\n"
                    + "  `SupplierAddress` text DEFAULT NULL,\n"
                    + "  `SuplyerDescription` text DEFAULT NULL,\n"
                    + "  `CreatorId` int(11) DEFAULT NULL,\n"
                    + "  `Date` date NOT NULL,\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC));");
            pst.execute();

            pst = con.mkDataBase().prepareStatement("CREATE TABLE if not exists " + db + ".`Brands` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `BrandName` varchar(70) DEFAULT NULL,\n"
                    + "  `Description` text DEFAULT NULL,\n"
                    + "  `SupplierId` varchar(20)  DEFAULT NULL,\n"
                    + "  `CreatorId` int DEFAULT NULL,\n"
                    + "  `Date` date,\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC));");

            pst.execute();

            pst = con.mkDataBase().prepareStatement("CREATE TABLE if not exists " + db + ".`Category` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `CategoryName` varchar(70) DEFAULT NULL,\n"
                    + "  `CategoryDescription` text DEFAULT NULL,\n"
                    + "  `BrandId` varchar(20) DEFAULT NULL,\n"
                    + "  `SupplierId` int(11) DEFAULT NULL,\n"
                    + "  `CreatorId` int(11) DEFAULT NULL,\n"
                    + "  `Date` date,\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC));");

            pst.execute();
            pst = con.mkDataBase().prepareStatement("CREATE TABLE if not exists " + db + ".`Unit` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `UnitName` varchar(50) DEFAULT NULL,\n"
                    + "  `UnitDescription` text DEFAULT NULL,\n"
                    + "  `CreatorId` int(11) DEFAULT NULL,\n"
                    + "  `Date` date,\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC));");

            pst.execute();

            pst = con.mkDataBase().prepareStatement("CREATE TABLE if not exists " + db + ".`RMA` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `RMAName` varchar(100) DEFAULT NULL,\n"
                    + "  `RMADays` varchar(11) NOT NULL,\n"
                    + "  `Comment` text DEFAULT NULL,\n"
                    + "  `CreatorId` int(11) DEFAULT NULL,\n"
                    + "  `Date` date,\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC));");

            pst.execute();
            pst = con.mkDataBase().prepareStatement("CREATE TABLE IF NOT EXISTS " + db + ".`Products` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `ProductId` varchar(20) NOT NULL,\n"
                    + "  `ProductName` varchar(150) NOT NULL,\n"
                    + "  `Quantity` varchar(11) NOT NULL DEFAULT '0', \n"
                    + "  `Description` text ,\n"
                    + "  `SupplierId` varchar(11) NOT NULL,\n"
                    + "  `BrandId` varchar(11) NOT NULL,\n"
                    + "  `CategoryId` varchar(11) NOT NULL,\n"
                    + "  `UnitId` varchar(11) NOT NULL,\n"
                    + "  `PurchasePrice` varchar(100) NOT NULL,\n"
                    + "  `SellPrice` varchar(100) NOT NULL,\n"
                    + "  `RMAId` varchar(11) NOT NULL,\n"
                    + "  `UserId` varchar(11) NOT NULL,\n"
                    + "  `Date` date NOT NULL,\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC));");
            pst.execute();

            pst = con.mkDataBase().prepareStatement("CREATE TABLE IF NOT EXISTS " + db + ".`Customer` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `CustomerName` varchar(200) NOT NULL,\n"
                    + "  `CustomerContNo` varchar(200) DEFAULT NULL,\n"
                    + "  `CustomerAddress` text,\n"
                    + "  `TotalBuy` varchar(50) DEFAULT NULL,\n"
                    + "  `CreatorId` varchar(11) DEFAULT NULL,\n"
                    + "  `Date` datetime NOT NULL,\n"
                    + "  PRIMARY KEY (`Id`),\n"
                    + "  UNIQUE INDEX `Id` (`Id` ASC));");
            pst.execute();

            pst = con.mkDataBase().prepareStatement("CREATE TABLE IF NOT EXISTS " + db + ".`Sell` (\n"
                    + "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `SellId` varchar(10) NOT NULL,\n"
                    + "  `CustomerId` varchar(11) NOT NULL,\n"
                    + "  `ProductId` varchar(11) NOT NULL,\n"
                    + "  `PurchasePrice` double NOT NULL,\n"
                    + "  `SellPrice` double NOT NULL,\n"
                    + "  `Quantity` int(10) NOT NULL,\n"
                    + "  `TotalPrice` double NOT NULL,\n"
                    + "  `WarrentyVoidDate` varchar(20) NOT NULL,\n"
                    + "  `SellerId` int(11) NOT NULL,\n"
                    + "  `SellDate` datetime NOT NULL,\n"
                    + "  PRIMARY KEY (`Id`)\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;");
            pst.execute();
            
            pst = con.mkDataBase().prepareStatement("CREATE TABLE IF NOT EXISTS " + db + ".`BinStatus` (\n"
                    //+ "  `Id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `Type` varchar(20) NOT NULL PRIMARY KEY,\n"
                    + "  `ProductId` varchar(11),\n"
                    + "  `ProductNumber` varchar(20)\n"
                    //+ "  `Date` datetime,\n"
                   // + "  PRIMARY KEY (`Id`)\n"
                    + ")");
            pst.execute();
            
            System.out.println("Created Database Successfuly");

        } catch (SQLException ex) {
            System.err.println(ex);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/Server.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Server Configure");
                stage.showAndWait();
            } catch (IOException ex1) {
                Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
    }
    
}
