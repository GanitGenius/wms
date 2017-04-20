/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.stock;

import Setup.BrandSetup;
import dataBase.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import Data.Brands;
import Gateway.BrandsGateway;
import dataBase.DBProperties;
import javafx.scene.control.Alert;

/**
 *
 * @author GanitGenius
 */
public class AddBrandController implements Initializable {

    public Button btnAddSupplier;
    private userNameMedia media;

    Brands brands = new Brands();
    BrandsGateway brandsGateway = new BrandsGateway();
    BrandSetup brandSetup = new BrandSetup();

    public String brandId;
    private String usrId;
    private String supplierName;
    private String supplierId;

    DBConnection dbCon = new DBConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();

    @FXML
    public Button btnUpdate;
    @FXML
    public Label lblHeader;
    @FXML
    public Button btnClose;

    @FXML
    private ComboBox<String> cbSupplier;
    @FXML
    private TextField tfBrandName;
    @FXML
    private TextArea taDiscription;
    @FXML
    public Button btnAddBrand;

    public userNameMedia getMedia() {
        return media;
    }

    public void setMedia(userNameMedia media) {
        usrId = media.getId();
        this.media = media;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btnAddBrandOnAction(ActionEvent event) {

        System.out.println(usrId);
        if (isNotNull()) {
            brands.creatorId = usrId;
            brands.brandName = tfBrandName.getText();
            brands.brandComment = taDiscription.getText();
            brands.supplierName = cbSupplier.getSelectionModel().getSelectedItem();
            brandSetup.save(brands);
        }

    }

    @FXML
    private void cbSupplierOnAction(ActionEvent event) {

    }

    @FXML
    private void cbSupplierOnClick(MouseEvent event) {

        cbSupplier.getItems().clear();
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from " + db + ".Supplier order by SupplierName");
            rs = pst.executeQuery();
            while (rs.next()) {
                supplierName = rs.getString(2);
                cbSupplier.getItems().add(supplierName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBrandController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {

        System.out.println();
        if (isNotNull()) {
            brands.id = brandId;
            if (!cbSupplier.getSelectionModel().isEmpty()) {
                brands.supplierName = cbSupplier.getSelectionModel().getSelectedItem();
            } else if (!cbSupplier.getPromptText().isEmpty()) {
                brands.supplierName = cbSupplier.getPromptText();
            }

            brands.brandName = tfBrandName.getText().trim();
            brands.brandComment = taDiscription.getText();
            brandSetup.update(brands);
        }

    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {

        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public boolean isNotNull() {
        System.out.println(cbSupplier.getPromptText());
        System.out.println(tfBrandName.getText());
        boolean isNotNull;
        if (tfBrandName.getText().trim().isEmpty()
                || cbSupplier.getSelectionModel().isEmpty()
                && cbSupplier.getPromptText().isEmpty()) {
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

    public void showDetails() {

        brands.id = brandId;
        brandsGateway.selectedView(brands);
        tfBrandName.setText(brands.brandName);
        taDiscription.setText(brands.brandComment);
        cbSupplier.setPromptText(brands.supplierName);

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
            supplierController.lblCaption.setText("Add Item");
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

}
