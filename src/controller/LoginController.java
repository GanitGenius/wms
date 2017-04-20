/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import Data.Users;
import custom.CustomTf;
import custom.CustomPf;
import dataBase.DBProperties;
import java.sql.SQLException;
import java.util.Optional;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import dataBase.DBConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import media.userNameMedia;

/**
 *
 * @author GanitGenius
 */
public class LoginController implements Initializable {

    Users users = new Users();

    @FXML
    private TextField tfUserName;
    @FXML
    private Button btnUserNameTfClear;
    @FXML
    private Button btnPassFieldClear;
    @FXML
    private PasswordField pfUserPassword;

    CustomTf cTF = new CustomTf();
    CustomPf cPF = new CustomPf();

    @FXML
    private Button btnLogin;

    private PreparedStatement pst;
    private Connection con;
    private ResultSet rs;

    @FXML
    private AnchorPane apMother;
    @FXML
    private AnchorPane apDesignPane;
    @FXML
    private Hyperlink hlCrateAccount;

    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();
    @FXML
    private Hyperlink hlDatabase;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cTF.clearTextFieldByButton(tfUserName, btnUserNameTfClear);
        cPF.clearPassFieldByButton(pfUserPassword, btnPassFieldClear);
        BooleanBinding boolenBinding = tfUserName.textProperty().isEmpty()
                .or(pfUserPassword.textProperty().isEmpty());
        btnLogin.disableProperty().bind(boolenBinding);
    }

    @FXML
    private void hlCreateAnAccount(ActionEvent event) {

        DBConnection dbCon = new DBConnection();
        con = dbCon.geConnection();
        if (con != null) {
            try {
                pst = con.prepareStatement("SELECT Id FROM " + db + ".User ORDER BY Id ASC LIMIT 1");
                rs = pst.executeQuery();
                if (rs.next()) {
                    apMother.setOpacity(0.7);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("You can't create an account without admin's permission");
                    alert.initStyle(StageStyle.UNDECORATED);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        apMother.setOpacity(1.0);
                    }
                    return;
                }
                con.close();
                pst.close();
                rs.close();
                loadRegistration();
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error : Server Not Found");
            alert.setContentText("Make sure your MySQLl is Started properly\n");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();

        }

    }

    @FXML
    private void pfUserNameOnHitEnter(ActionEvent event) {

        try {
            btnLogin(event);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void pfUserPassOnHitEnter(ActionEvent event) {

        try {
            btnLogin(event);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void btnLogin(ActionEvent event) throws IOException {

        DBConnection dbCon = new DBConnection();
        con = dbCon.geConnection();
        if (con != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/Application.fxml"));
            loader.load();
            Parent parent = loader.getRoot();
            Scene adminPanelScene = new Scene(parent);
            Stage adminPanelStage = new Stage();
            adminPanelStage.setMaximized(true);

            if (isValidCondition()) {
                try {
                    String generatedPassword = "";
                    try {
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        md.update(pfUserPassword.getText().getBytes());
                        byte[] bytes = md.digest();
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < bytes.length; i++) {
                            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                        }
                        generatedPassword = sb.toString();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    pst = con.prepareStatement("select * from " + db + ".User where UsrName=? and Password=? and Status=1");
                    pst.setString(1, tfUserName.getText());
                    pst.setString(2, generatedPassword);
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        userNameMedia usrNameMedia = new userNameMedia(rs.getString(1), rs.getString(2));
                        ApplicationController apControl = loader.getController();
                        apControl.setUsrNameMedia(usrNameMedia);
                        apControl.btnHomeOnClick(event);
                        apControl.permission();
                        apControl.viewDetails();
                        adminPanelStage.setScene(adminPanelScene);
                        adminPanelStage.getIcons().add(new Image("/image/icon.png"));
                        adminPanelStage.setTitle(rs.getString(3));
                        adminPanelStage.show();
                        Stage stage = (Stage) btnLogin.getScene().getWindow();
                        stage.close();
                    } else {
                        System.out.println("Password Not Match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Password Not Match");
                        alert.setHeaderText("Error : Name or Password not matched");
                        alert.setContentText("User Name or Password not matched\nTry Again");
                        alert.initStyle(StageStyle.UNDECORATED);
                        alert.showAndWait();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error : Server Not Found");
            alert.setContentText("Make sure your MySQL is started properly\n");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        }

    }

    private boolean isValidCondition() {

        boolean validCondition = false;
        if (tfUserName.getText().trim().isEmpty()
                || pfUserPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING :");
            alert.setHeaderText("WARNING !!");
            alert.setContentText("Please Fill Text Field And Password Properly");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            validCondition = false;
        } else {
            validCondition = true;
        }
        return validCondition;

    }

    private void loadRegistration() {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/Registration.fxml"));
            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setScene(scene);
//            nStage.setMaximized(true);
            nStage.setTitle("Registration -SmartWMS");
            nStage.show();
            Stage stage = (Stage) hlCrateAccount.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void hlDbOnAction(ActionEvent event) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/Server.fxml"));
            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setScene(scene);
            nStage.setMaximized(false);
            nStage.setTitle("Server Status -SmartWMS");
            nStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
