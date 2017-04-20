/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.stock;

import Setup.SupplierSetup;
import Gateway.SupplierGateway;
import custom.History;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dataBase.SQL;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import List.ListSupplier;
import Data.Supplier;
import java.util.Optional;
import media.userNameMedia;

/**
 *
 * @author GanitGenius
 */
public class ViewSupplierController implements Initializable {

    @FXML
    public AnchorPane acContent;
    SQL sql = new SQL();
    Supplier supplier = new Supplier();
    SupplierGateway supplierGateway = new SupplierGateway();
    SupplierSetup supplierSetup = new SupplierSetup();
    History history = new History();

    private String usrId;
    private String creatorName;
    private String creatorId;
    private String supplierId;
    private String userName;

    private userNameMedia media;

    @FXML
    private TableView<ListSupplier> tblSupplier;
    @FXML
    private TableColumn<Object, Object> clmSUpplyerId;
    @FXML
    private TableColumn<Object, Object> clmSupplierName;
    @FXML
    private TableColumn<Object, Object> clmSupplierPhoneNumber;
    @FXML
    private TableColumn<Object, Object> clmSupplierAddress;
    @FXML
    private TableColumn<Object, Object> clmSupplierJoining;
    @FXML
    private TableColumn<Object, Object> clmSupplierDescription;

    private final static int dataSize = 10_023;
    private final static int rowsPerPage = 1000;
    @FXML
    private Button btnAdditems;
    @FXML
    private Button btnUpdate;
    @FXML
    private TextField tfSearch;
    private Text text;
    @FXML
    private MenuItem mbSearch;
    @FXML
    private Button btnRefresh;

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
    private void tblSupplierOnClick(MouseEvent event) {
        int click = event.getClickCount();
        if (click == 2) {
            viewDetails();
        }

    }

    @FXML
    private void tblSupplierOnKeyPress(KeyEvent event) {

    }

    @FXML
    public void tfSearchOnType(Event event) {

        supplier.supplierDetails.removeAll();
        supplier.supplierName = tfSearch.getText().trim();
        tblSupplier.setItems(supplier.supplierDetails);
        clmSUpplyerId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        clmSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        clmSupplierPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("supplierPhoneNumber"));
        clmSupplierAddress.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        clmSupplierDescription.setCellValueFactory(new PropertyValueFactory<>("supplierDescription"));
        clmSupplierJoining.setCellValueFactory(new PropertyValueFactory<>("dataOfjoining"));
        supplierGateway.searchView(supplier);

    }

    public void showDetails() {

        tblSupplier.setItems(supplier.supplierDetails);
        clmSUpplyerId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        clmSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        clmSupplierPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("supplierPhoneNumber"));
        clmSupplierAddress.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        clmSupplierDescription.setCellValueFactory(new PropertyValueFactory<>("supplierDescription"));
        clmSupplierJoining.setCellValueFactory(new PropertyValueFactory<>("dataOfjoining"));
        supplierGateway.view(supplier);

    }

    @FXML
    private void btnAdditemsOnAction(ActionEvent event) {

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
        tfSearchOnType(event);

    }

    @FXML
    private void btnUpdateOnAction(Event event) {
        if (tblSupplier.getSelectionModel().getSelectedItem() != null) {
            viewDetails();
        } else {
            System.out.println("EMPTY SELECTION");
        }

    }

    private void viewDetails() {

        if (!tblSupplier.getSelectionModel().isEmpty()) {
            ListSupplier selectedSupplier = tblSupplier.getSelectionModel().getSelectedItem();
            System.out.println("ID is");
            System.out.println(selectedSupplier.getSupplierId());
            String items = selectedSupplier.getSupplierId();
            if (!items.equals(null)) {
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
                    supplierController.lblCaption.setText("Supplier Details");
                    supplierController.btnUpdate.setVisible(true);
                    supplierController.btnSave.setVisible(false);
                    supplierController.supplierId = selectedSupplier.getSupplierId();
                    supplierController.showDetails();
                    Stage nStage = new Stage();
                    nStage.setScene(scene);
                    nStage.initModality(Modality.APPLICATION_MODAL);
                    nStage.initStyle(StageStyle.TRANSPARENT);
                    nStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Empty Selection");
        }

    }

    @FXML
    private void mbView(ActionEvent event) {
        btnUpdateOnAction(event);
    }

    @FXML
    private void mbViewHistory(ActionEvent event) {
    }

    @FXML
    private void mbAddNew(ActionEvent event) {
        btnAdditemsOnAction(event);
    }

    @FXML
    private void mbDeleteItem(ActionEvent event) {

        System.out.println("Clicked to delete");
        acContent.setOpacity(0.5);
        ListSupplier selectedSupplier = tblSupplier.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Login Now");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure you want to delete this item?\nTo confirm click OK");
        alert.initStyle(StageStyle.UNDECORATED);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            supplier.id = selectedSupplier.getSupplierId();
            System.out.println(supplier.id + "On here");
            supplierSetup.delete(supplier);
            tblSupplier.getItems().clear();
            showDetails();
        }

    }

    @FXML
    private void mbEdit(ActionEvent event) {
        btnUpdateOnAction(event);
        tfSearchOnType(event);
    }

    @FXML
    private void mbSearch(ActionEvent event) {
        tblSupplier.getSelectionModel().clearSelection();
        tfSearch.requestFocus();

    }

    @FXML
    public void btnDeleteOnAction(ActionEvent event) {
        mbDeleteItem(event);
    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
        supplier.supplierDetails.clear();
        showDetails();
    }

}
