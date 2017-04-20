/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.settings;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import custom.*;
import dataBase.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import media.userNameMedia;
import Data.Users;
import dataBase.DBProperties;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author GanitGenius
 */
public class PassChangeController implements Initializable {

    Users users = new Users();

    @FXML
    private PasswordField pfCurrentPass;
    @FXML
    private PasswordField pfNewPass;
    @FXML
    private PasswordField pfRePass;
    @FXML
    private Button btnClrCurrentPf;
    @FXML
    private Button btnClrRePass;
    @FXML
    private Button btnClrNewPass;
    @FXML
    private Button btnChangePass;
    @FXML
    private Button btnClose;

    private String userId;
    private String userName;
    private userNameMedia nameMedia;

    @FXML
    private ImageView imgCurrentPassMatch;
    @FXML
    private ImageView imgNewPassMatch;

    public userNameMedia getNameMedia() {
        return nameMedia;
    }

    public void setNameMedia(userNameMedia nameMedia) {
        userId = nameMedia.getId();
        userName = nameMedia.getUsrName();
        this.nameMedia = nameMedia;
    }

    CustomPf customPf = new CustomPf();

    DBConnection dbCon = new DBConnection();
    Connection con;
    ResultSet rs;
    PreparedStatement pst;

    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        customPf.clearPassFieldByButton(pfCurrentPass, btnClrCurrentPf);
        customPf.clearPassFieldByButton(pfNewPass, btnClrNewPass);
        customPf.clearPassFieldByButton(pfRePass, btnClrRePass);
        BooleanBinding binding = pfCurrentPass.textProperty().isEmpty()
                .or(pfNewPass.textProperty().isEmpty()
                        .or(pfRePass.textProperty().isEmpty()));
        btnChangePass.disableProperty().bind(binding);

    }

    @FXML
    private void btnChangePassOnAction(ActionEvent event) {

        if (isCurrentPasswordCheckOk()) {
            if (isPasswordMatch()) {
                updatePassword();
            }
        } else {
            System.out.println("ddd");
        }

    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {

        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void pfOnAction(ActionEvent event) {
        btnChangePassOnAction(event);
    }

    @FXML
    private void pfNewPassWordMatch(KeyEvent event) {

        if (pfNewPass.getText().matches(pfRePass.getText())) {
            System.out.println("Match");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR");
            alert.setContentText("Invalid password");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        }

    }

    private boolean isCurrentPasswordCheckOk() {

        boolean conDitionValid = true;
        con = dbCon.geConnection();
        try {
            String generatedPassword = "";
            try {
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        md.update(pfCurrentPass.getText().getBytes());
                        byte[] bytes = md.digest();
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < bytes.length; i++) {
                            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                        }
                        generatedPassword = sb.toString();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
            pst = con.prepareStatement("select * from "+db+".User where Id=? and Password=?");
            pst.setString(1, userId);
            pst.setString(2, generatedPassword);
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("Old Password Match");
                return conDitionValid;
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR ");
            alert.setContentText("Invalid password");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            conDitionValid = false;
        } catch (SQLException ex) {
            Logger.getLogger(PassChangeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conDitionValid;

    }

    private boolean isPasswordMatch() {

        boolean passMatch;
        if (pfNewPass.getText().matches(pfRePass.getText())) {
            System.out.println("New Password matched");
            passMatch = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR ");
            alert.setContentText("New Password what you enterd did not matched");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            passMatch = false;
        }
        return passMatch;

    }

    private void updatePassword() {

        con = dbCon.geConnection();
        try {
            String generatedPassword = "";
            try {
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        md.update(pfNewPass.getText().getBytes());
                        byte[] bytes = md.digest();
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < bytes.length; i++) {
                            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                        }
                        generatedPassword = sb.toString();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
            pst = con.prepareStatement("Update " + db + ".User set Password=? where Id=?");
            pst.setString(1, generatedPassword);
            pst.setString(2, userId);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Successfull");
            alert.setContentText("Password Updated Successfully");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(PassChangeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
