/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.stock;

import Setup.CategorySetup;
import custom.CellFactories;
import dataBase.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import media.userNameMedia;
import Data.Category;
import Gateway.CategoryGateway;
import List.ListCategory;
import java.util.Optional;

/**
 *
 * @author GanitGenius
 */
public class ViewCategoryController implements Initializable {

    private String usrId;
    private String categoryId;
    private String brandId;
    private String brandName;
    private String creatorId;

    SQL sql = new SQL();
    Category category = new Category();
    CellFactories cellFactories = new CellFactories();
    CategoryGateway categoryGateway = new CategoryGateway();
    CategorySetup categorySetup = new CategorySetup();

    private userNameMedia media;
    @FXML
    private TableView<ListCategory> tblCategory;
    @FXML
    private TableColumn<Object, Object> clmCategoryId;
    @FXML
    private TableColumn<Object, Object> clmCategoryName;
    @FXML
    private TableColumn<Object, Object> clmCategoryBrand;
    @FXML
    private TableColumn<Object, Object> clmCategoryCreator;
    @FXML
    private TableColumn<Object, Object> clmCategoryDate;
    @FXML
    private TableColumn<Object, Object> clmCategoryDescription;
    @FXML
    public TableColumn<Object, Object> clmSupplier;
    @FXML
    public TableColumn tablClmBox;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private MenuItem miSearch;
    @FXML
    private MenuItem miUpdate;
    @FXML
    private MenuItem miAddNew;
    @FXML
    private MenuItem miDelete;
    @FXML
    private MenuItem miView;
    @FXML
    private Button btnRefresh;

    public userNameMedia getMedia() {
        return media;
    }

    public void setMedia(userNameMedia media) {
        usrId = media.getId();
        this.media = media;
    }

    DBConnection dbCon = new DBConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    @FXML
    private TextField tfSearch;

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
    private void tblCategoryOnClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            viewDetails();
        } else {
            System.out.println("CLICKED");
        }
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {

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

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {

        if (tblCategory.getSelectionModel().getSelectedItem() != null) {
            viewDetails();
        } else {
            System.out.println("EMPTY SELECTION");
        }

    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {

        ListCategory selectedCategory = tblCategory.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Login Now");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure to delete this item \n to Confirm click ok");
        alert.initStyle(StageStyle.UNDECORATED);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            category.id = selectedCategory.getId();
            System.out.println(category.id + "On here");
            categorySetup.delete(category);
            tblCategory.getItems().clear();
            showDetails();
        }

    }

    public void showDetails() {

        tblCategory.setItems(category.categoryDetails);
        tablClmBox.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmCategoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmCategoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        clmCategoryBrand.setCellValueFactory(new PropertyValueFactory<>("brandId"));
        clmSupplier.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        clmCategoryDescription.setCellValueFactory(new PropertyValueFactory<>("categoryDescription"));
        clmCategoryCreator.setCellValueFactory(new PropertyValueFactory<>("creatorId"));
        clmCategoryDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        categoryGateway.view(category);
        tablClmBox.setCellFactory(cellFactories.cellFactoryCheckBox);

    }

    @FXML
    public void tfSearchOnType(Event event) {

        category.categoryDetails.clear();
        category.categoryName = tfSearch.getText().trim();
        tblCategory.setItems(category.categoryDetails);
        clmCategoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmCategoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        clmCategoryBrand.setCellValueFactory(new PropertyValueFactory<>("brandId"));
        clmSupplier.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        clmCategoryDescription.setCellValueFactory(new PropertyValueFactory<>("categoryDescription"));
        clmCategoryCreator.setCellValueFactory(new PropertyValueFactory<>("creatorId"));
        clmCategoryDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        categoryGateway.searchView(category);

    }

    private void viewDetails() {

        if (!tblCategory.getSelectionModel().isEmpty()) {
            ListCategory selectedCategory = tblCategory.getSelectionModel().getSelectedItem();
            System.out.println("ID is");
            System.out.println(selectedCategory.getCreatorId());
            String items = selectedCategory.getId();
            if (!items.equals(null)) {
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
                    categoryController.lblHeaderContent.setText("Category Details");
                    categoryController.btnUpdate.setVisible(true);
                    categoryController.btnSave.setVisible(false);
                    categoryController.categoryId = selectedCategory.id;
                    categoryController.showDetails();
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
            System.out.println("empty Selection");
        }

    }

    @FXML
    private void miSearchOnAction(ActionEvent event) {
        tblCategory.getSelectionModel().clearSelection();
        tfSearch.requestFocus();
    }

    @FXML
    private void miUpdateOnAction(ActionEvent event) {
        btnUpdateOnAction(event);
    }

    @FXML
    private void miAddNewOnAction(ActionEvent event) {
        btnAddOnAction(event);
    }

    @FXML
    private void miDeleteOnAction(ActionEvent event) {
        btnDeleteOnAction(event);
    }

    @FXML
    private void miViewOnAction(ActionEvent event) {
        btnUpdateOnAction(event);
    }

    @FXML
    private void btnRefreshOnAction(ActionEvent event) {
        category.categoryDetails.clear();
        showDetails();
    }

}
