/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.stock;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import Setup.CurrentProductSetup;
import Data.CurrentProduct;
import Gateway.CurrentProductGateway;
import dataBase.DBConnection;
import dataBase.DBProperties;
import dataBase.SQL;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import media.userNameMedia;

/**
 *
 * @author GanitGenius
 */
public class AddProductController implements Initializable {

    public ComboBox<String> cmbBrand;
    public ComboBox cmbCategory;
    public Button btnAddSupplier;
    public Button btnAddBrand;
    public Button btnAddCategory;
    public Button btnAddUnit;
    public Button btnAddRma;
    @FXML
    private RadioButton rbStatic;
    @FXML
    private RadioButton rbSeq;

    private String usrId;
    private userNameMedia nameMedia;

    CurrentProduct currentProduct = new CurrentProduct();
    CurrentProductSetup currentProductSetup = new CurrentProductSetup();
    CurrentProductGateway currentProductGateway = new CurrentProductGateway();
    SQL sql = new SQL();

    DBConnection dbCon = new DBConnection();
    Connection con = dbCon.geConnection();
    PreparedStatement pst;
    ResultSet rs;

    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();

    @FXML
    private Button btnClose;
    @FXML
    private TextField tfProductId;
    @FXML
    private TextField tfProductName;
    @FXML
    private TextField tfProductQuantity;
    @FXML
    private TextField tfProductPurchasePrice;
    @FXML
    private TextField tfProductSellPrice;
    @FXML
    private TextArea taProductDescription;
    @FXML
    private ComboBox<String> cbUnit;
    @FXML
    private ComboBox<String> cbRMA;
    @FXML
    private DatePicker dpDate;
    @FXML
    public Button btnSave;
    @FXML
    private TextField tfValue;
    @FXML
    private TextField tfWidth;
    @FXML
    public TextField tfHeight;
    @FXML
    private TextField tfDepth;

    public String id;
    private String supplierName;
    private String supplierId;
    private String brandName;
    private String brandId;
    private String categoryName;
    private String categoryId;
    private String unitId;
    private String rmaId;

    @FXML
    private ComboBox<String> cbSupplier;
    @FXML
    public Button btnUpdate;
    @FXML
    public Label lblHeader;

    public userNameMedia getNameMedia() {
        return nameMedia;
    }

    public void setNameMedia(userNameMedia nameMedia) {
        usrId = nameMedia.getId();
        this.nameMedia = nameMedia;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ToggleGroup firstRedioBtn = new ToggleGroup();
        rbSeq.setToggleGroup(firstRedioBtn);
        rbStatic.setSelected(true);
        rbStatic.setToggleGroup(firstRedioBtn);
        tfValue.setVisible(false);

    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) {

        System.out.println("Presesd");
        if (isNotNull()) {
            unitId = sql.getIdNo(cbUnit.getSelectionModel().getSelectedItem(), unitId, "Unit", "UnitName");
            rmaId = sql.getIdNo(cbRMA.getSelectionModel().getSelectedItem(), rmaId, "RMA", "RMAName");

            if (!tfValue.getText().trim().isEmpty()) {
                boolean r = false;
                String value = tfValue.getText();
                int foo = Integer.parseInt(value);
                for (int i = 1; i <= foo; ++i) {
                    currentProduct.productId = tfProductId.getText().trim() + i;
                    currentProduct.productName = tfProductName.getText().trim();
                    currentProduct.quantity = tfProductQuantity.getText().trim();
                    currentProduct.purchasePrice = tfProductPurchasePrice.getText().trim();
                    currentProduct.sellPrice = tfProductSellPrice.getText().trim();
                    currentProduct.description = taProductDescription.getText().trim();
                    currentProduct.width = tfWidth.getText().trim();
                    currentProduct.height = tfHeight.getText().trim();
                    currentProduct.depth = tfDepth.getText().trim();
                    currentProduct.supplierId = supplierId;
                    currentProduct.brandId = brandId;
                    currentProduct.categoryId = categoryId;
                    currentProduct.unitId = unitId;
                    currentProduct.rmaId = rmaId;
                    currentProduct.userId = usrId;
                    currentProduct.date = dpDate.getValue().toString();
                    r = currentProductSetup.save(currentProduct);
                }
                if (r) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successfull");
                    alert.setHeaderText("Successfull");
                    alert.setContentText("Product added successfully");
                    alert.initStyle(StageStyle.UNDECORATED);
                    alert.showAndWait();
                }
            } else {
                currentProduct.productId = tfProductId.getText().trim();
                currentProduct.productName = tfProductName.getText().trim();
                currentProduct.quantity = tfProductQuantity.getText().trim();
                currentProduct.purchasePrice = tfProductPurchasePrice.getText().trim();
                currentProduct.sellPrice = tfProductSellPrice.getText().trim();
                currentProduct.description = taProductDescription.getText().trim();
                currentProduct.width = tfWidth.getText().trim();
                currentProduct.height = tfHeight.getText().trim();
                currentProduct.depth = tfDepth.getText().trim();
                currentProduct.supplierId = supplierId;
                currentProduct.brandId = brandId;
                currentProduct.categoryId = categoryId;
                currentProduct.unitId = unitId;
                currentProduct.rmaId = rmaId;
                currentProduct.userId = usrId;
                currentProduct.date = dpDate.getValue().toString();
                boolean r = currentProductSetup.save(currentProduct);
                if (r) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successfull");
                    alert.setHeaderText("Successfull");
                    alert.setContentText("Product added successfully");
                    alert.initStyle(StageStyle.UNDECORATED);
                    alert.showAndWait();
                }

            }
        }

    }

    @FXML
    private void rbSeqOnClick(MouseEvent event) {

        if (rbSeq.isSelected()) {
            tfValue.setVisible(true);
        } else if (!rbSeq.isSelected()) {
            tfValue.setVisible(false);
        }

    }

    @FXML
    private void rbSeqOnAction(ActionEvent event) {

    }

    @FXML
    private void rbStaticOnClicked(MouseEvent event) {

        if (rbStatic.isSelected()) {
            tfValue.setVisible(false);
            tfValue.clear();
        } else if (!rbStatic.isSelected()) {
            tfValue.setVisible(true);
        }

    }

    @FXML
    private void rbStaticOnAction(ActionEvent event) {

    }

    @FXML
    private void cbSupplierOnClicked(MouseEvent event) {

        cbSupplier.getSelectionModel().clearSelection();
        cbSupplier.getItems().clear();
        cmbBrand.getSelectionModel().clearSelection();
        cmbBrand.getItems().clear();
        cmbBrand.getItems().removeAll();
        try {
            pst = con.prepareStatement("select * from " + db + ".Supplier");
            rs = pst.executeQuery();
            while (rs.next()) {
                cbSupplier.getItems().addAll(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void cbSupplierOnAction(ActionEvent event) {

        cbSupplier.getSelectionModel().getSelectedItem();

        try {
            pst = con.prepareStatement("select * from " + db + ".Supplier where SupplierName=?");
            pst.setString(1, cbSupplier.getSelectionModel().getSelectedItem());
            rs = pst.executeQuery();
            while (rs.next()) {
                supplierId = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void cmbBrandOnClick(Event event) {

        cmbBrand.getItems().clear();
        cmbCategory.getItems().clear();
        cmbCategory.getItems().removeAll();
        cmbCategory.setPromptText(null);
        try {
            pst = con.prepareStatement("select * from " + db + ".Brands where SupplierId=?");
            pst.setString(1, supplierId);
            rs = pst.executeQuery();
            while (rs.next()) {
                cmbBrand.getItems().addAll(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void cmbCategoryOnClick(Event event) {

        cmbCategory.getItems().clear();
        try {
            pst = con.prepareStatement("select * from " + db + ".Category where SupplierId=? and BrandId=?");
            pst.setString(1, supplierId);
            pst.setString(2, brandId);
            rs = pst.executeQuery();
            while (rs.next()) {
                cmbCategory.getItems().addAll(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void cmbBrandOnAction(ActionEvent event) {

        cmbBrand.getSelectionModel().getSelectedItem();
        try {
            pst = con.prepareStatement("select * from " + db + ".Brands where BrandName=? and SupplierId=?");
            pst.setString(1, cmbBrand.getSelectionModel().getSelectedItem());
            pst.setString(2, supplierId);
            rs = pst.executeQuery();
            while (rs.next()) {
                brandId = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void cmbCategoryOnAction(ActionEvent actionEvent) {

        cmbCategory.getSelectionModel().getSelectedItem();
        try {
            pst = con.prepareStatement("select * from " + db + ".Category where SupplierId=? and BrandId=?");
            pst.setString(1, supplierId);
            pst.setString(2, brandId);
            rs = pst.executeQuery();
            while (rs.next()) {
                categoryId = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void cbUnitOnClick(MouseEvent event) {

        cbUnit.getItems().clear();
        try {
            pst = con.prepareStatement("select * from " + db + ".Unit");
            rs = pst.executeQuery();
            while (rs.next()) {
                cbUnit.getItems().addAll(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void cbRMAOnClick(MouseEvent event) {

        cbRMA.getItems().clear();
        try {
            pst = con.prepareStatement("select * from " + db + ".RMA");
            rs = pst.executeQuery();
            while (rs.next()) {
                cbRMA.getItems().addAll(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean isNotNull() {

        boolean insNotNull = false;
        if (cbSupplier.getSelectionModel().getSelectedItem() == null
                && cbSupplier.getPromptText().isEmpty()
                || cmbBrand.getSelectionModel().getSelectedItem() == null
                && cmbBrand.getPromptText().isEmpty()
                || cmbCategory.getSelectionModel().isEmpty()
                && cmbCategory.getPromptText().isEmpty()
                || tfProductId.getText().isEmpty()
                || tfProductName.getText().isEmpty()
                || tfProductQuantity.getText().isEmpty()
                || tfProductPurchasePrice.getText().isEmpty()
                || tfWidth.getText().isEmpty()
                || tfHeight.getText().isEmpty()
                || tfDepth.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("ERROR : NULL FOUND");
            alert.setContentText("Please fill all the required fields");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            insNotNull = false;
        } else {
            insNotNull = true;
        }
        return insNotNull;

    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {

        if (isNotNull()) {
            System.out.println(supplierId + brandId + brandId + unitId + rmaId + usrId);
            currentProduct.productId = tfProductId.getText();
            currentProduct.productName = tfProductName.getText();
            currentProduct.quantity = tfProductQuantity.getText();
            currentProduct.purchasePrice = tfProductPurchasePrice.getText();
            currentProduct.sellPrice = tfProductSellPrice.getText();
            currentProduct.supplierId = supplierId;
            currentProduct.brandId = brandId;
            currentProduct.categoryId = categoryId;
            currentProduct.unitId = unitId;
            currentProduct.rmaId = rmaId;
            currentProduct.id = id;
            currentProductSetup.update(currentProduct);
            refreshProductList();
        }

    }

    public void viewSelected() {

        currentProduct.id = id;
        currentProductGateway.selectedView(currentProduct);
        tfProductId.setText(currentProduct.productId);
        tfProductName.setText(currentProduct.productName);
        tfProductQuantity.setText(currentProduct.quantity);
        tfProductPurchasePrice.setText(currentProduct.purchasePrice);
        tfProductSellPrice.setText(currentProduct.sellPrice);
        taProductDescription.setText(currentProduct.description);
        dpDate.setValue(LocalDate.parse(currentProduct.date));
        supplierId = currentProduct.supplierId;
        brandId = currentProduct.brandId;
        categoryId = currentProduct.categoryId;
        unitId = currentProduct.unitId;
        rmaId = currentProduct.rmaId;
        cbSupplier.setPromptText(currentProduct.supplierName);
        cmbBrand.setPromptText(currentProduct.brandName);
        cmbCategory.setPromptText(currentProduct.categoryName);
        cbUnit.setPromptText(currentProduct.unitName);
        cbRMA.setPromptText(currentProduct.rmaName);

    }

    @FXML
    private void cbUnitOnAction(ActionEvent event) {

        try {
            pst = con.prepareStatement("select * from " + db + ".Unit where UnitName=?");
            pst.setString(1, cbUnit.getSelectionModel().getSelectedItem());
            rs = pst.executeQuery();
            while (rs.next()) {
                unitId = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void cbRMAOnAction(ActionEvent event) {

        try {
            pst = con.prepareStatement("select * from " + db + ".RMA where RMAName=?");
            pst.setString(1, cbRMA.getSelectionModel().getSelectedItem());
            rs = pst.executeQuery();
            while (rs.next()) {
                rmaId = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void btnAddSupplierOnAction(ActionEvent actionEvent) {

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
            media.setId(usrId);
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

    public void btnAddBrandOnAction(ActionEvent actionEvent) {

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
            media.setId(usrId);
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

    public void btnAddCategoryOnAction(ActionEvent actionEvent) {

        AddCategoryController addCategoryController = new AddCategoryController();
        userNameMedia media = new userNameMedia();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/application/stock/AddCategory.fxml"));
        try {
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            AddCategoryController categoryController = fxmlLoader.getController();
            media.setId(usrId);
            categoryController.setMedia(media);
            categoryController.lblHeaderContent.setText("Add Item");
            categoryController.btnUpdate.setVisible(false);
            Stage nStage = new Stage();
            nStage.setScene(scene);
            nStage.initModality(Modality.APPLICATION_MODAL);
            nStage.initStyle(StageStyle.TRANSPARENT);
            nStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void btnAddUnitOnAction(ActionEvent actionEvent) {

        AddUnitController addUnitController = new AddUnitController();
        userNameMedia media = new userNameMedia();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/application/stock/AddUnit.fxml"));
        try {
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            AddUnitController unitController = fxmlLoader.getController();
            media.setId(usrId);
            unitController.setNameMedia(media);
            unitController.lblContent.setText("ADD UNIT");
            unitController.btnUpdate.setVisible(false);
            Stage nStage = new Stage();
            nStage.setScene(scene);
            nStage.initModality(Modality.APPLICATION_MODAL);
            nStage.initStyle(StageStyle.TRANSPARENT);
            nStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void btnAddRmaOnAction(ActionEvent actionEvent) {

        AddRMAController addRMAController = new AddRMAController();
        userNameMedia media = new userNameMedia();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/application/stock/AddRMA.fxml"));
        try {
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            AddRMAController rmaController = fxmlLoader.getController();
            media.setId(usrId);
            rmaController.setMedia(media);
            rmaController.lblContent.setText("ADD RMA");
            rmaController.btnUpdate.setVisible(false);
            Stage nStage = new Stage();
            nStage.setScene(scene);
            nStage.initModality(Modality.APPLICATION_MODAL);
            nStage.initStyle(StageStyle.TRANSPARENT);
            nStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void refreshProductList() {

        try {
            CurrentStoreController asc = new CurrentStoreController();
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.load(getClass().getResource("/view/application/stock/CurrentStore.fxml").openStream());
            CurrentStoreController currentStoreController = fXMLLoader.getController();
            currentStoreController.viewDetails();
        } catch (IOException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
