/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.stock;

import Gateway.SupplierGateway;
import custom.EffectUtility;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import media.userNameMedia;
import Data.Supplier;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author GanitGenius
 */
public class AddSupplierController implements Initializable {

    private String usrId;

    public String supplierId;

    private userNameMedia media;
    @FXML
    private TextField tfSupplierName;
    @FXML
    private TextArea taSupplierAddress;
    @FXML
    private TextArea taSupplierDescription;
    @FXML
    public Button btnSave;
    @FXML
    private TextArea taContactNumbers;
    @FXML
    public Button btnUpdate;
    @FXML
    public Button btnClose;
    @FXML
    public Label lblCaption;

    private Stage primaryStage;
    @FXML
    private AnchorPane apContent;

    public userNameMedia getMedia() {
        return media;
    }

    public void setMedia(userNameMedia media) {
        usrId = media.getId();
        this.media = media;
    }

    Supplier oSupplier = new Supplier();
    SupplierGateway supplierGateway = new SupplierGateway();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
        
        if (isNotNull()) {
            oSupplier.supplierName = tfSupplierName.getText();
            oSupplier.supplierContactNumber = taContactNumbers.getText();
            oSupplier.supplierAddress = taSupplierAddress.getText();
            oSupplier.supplierDescription = taSupplierDescription.getText();
            oSupplier.creatorId = usrId;
            supplierGateway.save(oSupplier);
            clrAll();
        }
        
    }

    public boolean isNotNull() {
        
        boolean isNotNull;
        if (tfSupplierName.getText().trim().isEmpty()
                || tfSupplierName.getText().trim().isEmpty()
                || taSupplierAddress.getText().trim().isEmpty()
                || taSupplierAddress.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("ERROR : NULL FOUND");
            alert.setContentText("Please fill all the required fields");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            isNotNull = false;
        } else {
            isNotNull = true;
        }
        return isNotNull;
        
    }

    private void clrAll() {
        
        tfSupplierName.clear();
        taContactNumbers.clear();
        taSupplierAddress.clear();
        taSupplierDescription.clear();
        
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        
        if (isNotNull()) {
            oSupplier.id = supplierId;
            oSupplier.supplierName = tfSupplierName.getText().trim();
            oSupplier.supplierContactNumber = taContactNumbers.getText().trim();
            oSupplier.supplierAddress = taSupplierAddress.getText().trim();
            oSupplier.supplierDescription = taSupplierDescription.getText().trim();
            supplierGateway.update(oSupplier);
        }
        
    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        
        Stage stage = (Stage) btnUpdate.getScene().getWindow();
        stage.close();
        
    }

    public void showDetails() {
        
        oSupplier.id = supplierId;
        supplierGateway.selectedView(oSupplier);
        tfSupplierName.setText(oSupplier.supplierName);
        taContactNumbers.setText(oSupplier.supplierContactNumber);
        taSupplierAddress.setText(oSupplier.supplierAddress);
        taSupplierDescription.setText(oSupplier.supplierDescription);
        
    }

    @FXML
    private void apOnMouseDragged(MouseEvent event) {

    }

    @FXML
    private void apOnMousePressed(MouseEvent event) {

    }

    public void addSupplierStage(Stage stage) {
        EffectUtility.makeDraggable(stage, apContent);
    }

}
