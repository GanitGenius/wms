/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.stock;

import Setup.CategorySetup;
import dataBase.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dataBase.SQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import media.userNameMedia;
import Data.Category;
import Gateway.CategoryGateway;
import dataBase.DBProperties;
import javafx.scene.control.Alert;

/**
 *
 * @author GanitGenius
 */
public class AddCategoryController implements Initializable {

    private String userId;
    private String brandId;
    private String brnadName;
    public String supplierId;
    public String supplierName;
    public String categoryId;

    Category category = new Category();
    CategoryGateway categoryGateway = new CategoryGateway();
    CategorySetup categorySetup = new CategorySetup();
    SQL sql = new SQL();
    
     DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();
    
    private userNameMedia media;
    @FXML
    private ComboBox<String> cbBrandName;
    @FXML
    private TextField tfCategoryName;
    @FXML
    private TextArea taCategoryDescription;
    @FXML
    public Button btnSave;
    @FXML
    private ComboBox<String> cbSupplierName;
    @FXML
    private Button btnAddSupplier;
    @FXML
    private Button btnAddBrand;
    @FXML
    public Button btnUpdate;
    @FXML
    public Label lblHeaderContent;
    @FXML
    public Button btnClose;

    DBConnection dbCon = new DBConnection();
    Connection con = dbCon.geConnection();
    PreparedStatement pst;
    ResultSet rs;

    public userNameMedia getMedia() {
        return media;
    }

    public void setMedia(userNameMedia media) {
        userId = media.getId();
        this.media = media;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btnSaveCategory(ActionEvent event) {
        
        if (isNotNull()) {
            category.brandName = cbBrandName.getSelectionModel().getSelectedItem();
            category.supplierName = cbSupplierName.getSelectionModel().getSelectedItem();
            category.categoryName = tfCategoryName.getText().trim();
            category.categoryDescription = taCategoryDescription.getText().trim();
            category.creatorId = userId;
            categorySetup.save(category);
        }
        
    }

    @FXML
    private void btnAddSupplierOnAction(ActionEvent event) {
        
        AddSupplierController addSupplierController = new AddSupplierController();
        userNameMedia media = new userNameMedia();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/application/stock/AddSupplier.fxml"));
        try {
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            AddSupplierController supplierController = fxmlLoader.getController();
            media.setId(userId);
            supplierController.setMedia(media);
            supplierController.lblCaption.setText("Add Supplier");
            supplierController.btnUpdate.setVisible(false);
            Stage nStage = new Stage();
            supplierController.addSupplierStage(nStage);
            nStage.setScene(scene);
            nStage.initModality(Modality.APPLICATION_MODAL);
            nStage.initStyle(StageStyle.TRANSPARENT);
            nStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    private void btnAddBrandOnAction(ActionEvent event) {
        
        AddBrandController addSupplierController = new AddBrandController();
        userNameMedia media = new userNameMedia();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/application/stock/AddBrand.fxml"));
        try {
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            AddBrandController supplierController = fxmlLoader.getController();
            media.setId(userId);
            supplierController.setMedia(media);
            supplierController.lblHeader.setText("Add Brand");
            supplierController.btnUpdate.setVisible(false);
            Stage nStage = new Stage();
            nStage.setScene(scene);
            nStage.initModality(Modality.APPLICATION_MODAL);
            nStage.initStyle(StageStyle.TRANSPARENT);
            nStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        
        System.out.println("Clicked");
        if (isNotNull()) {
            category.id = categoryId;
            if (!cbBrandName.getSelectionModel().isEmpty()) {
                category.brandName = cbBrandName.getSelectionModel().getSelectedItem();
            } else if (!cbBrandName.getPromptText().isEmpty()) {
                category.brandName = cbBrandName.getPromptText();
            }
            if (!cbSupplierName.getSelectionModel().isEmpty()) {
                category.supplierName = cbSupplierName.getSelectionModel().getSelectedItem();
            } else if (!cbSupplierName.getPromptText().isEmpty()) {
                category.supplierName = cbSupplierName.getPromptText();
            }
            category.categoryName = tfCategoryName.getText().trim();
            category.categoryDescription = taCategoryDescription.getText().trim();
            categorySetup.update(category);
        }
        
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public boolean isNotNull() {
        
        boolean isNotNull;
        if (tfCategoryName.getText().trim().isEmpty()
                || cbSupplierName.getSelectionModel().isEmpty()
                && cbSupplierName.getPromptText().isEmpty()
                || cbBrandName.getSelectionModel().isEmpty()
                && cbBrandName.getPromptText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error : null found ");
            alert.setContentText("Please fill all the required fields");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            isNotNull = false;
        } else {
            isNotNull = true;
        }
        return isNotNull;
        
    }

    @FXML
    private void cbSupplierNameOnClick(MouseEvent event) {
        
        cbBrandName.getItems().clear();
        cbBrandName.setPromptText(null);
        try {
            pst = con.prepareStatement("select * from "+db+".Supplier");
            rs = pst.executeQuery();
            while (rs.next()) {
                supplierName = rs.getString(2);
                cbSupplierName.getItems().remove(supplierName);
                cbSupplierName.getItems().add(supplierName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    private void cbBrandNameOnClick(MouseEvent event) throws SQLException {
        
        cbBrandName.getItems().clear();
        supplierName = cbSupplierName.getSelectionModel().getSelectedItem();
        supplierId = sql.getIdNo(supplierName, supplierId, "Supplier", "SupplierName");

        pst = con.prepareStatement("select * from "+db+".Brands where SupplierId=?");
        pst.setString(1, supplierId);
        rs = pst.executeQuery();
        while (rs.next()) {
            cbBrandName.getItems().add(rs.getString(2));
        }
        
    }

    public void showDetails() {
        
        category.id = categoryId;
        categoryGateway.selectedView(category);
        tfCategoryName.setText(category.categoryName);
        taCategoryDescription.setText(category.categoryDescription);
        cbBrandName.setPromptText(category.brandName);
        cbSupplierName.setPromptText(category.supplierName);
        
    }
    
}
