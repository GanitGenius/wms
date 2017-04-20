/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.settings;

import dataBase.DBConnection;
import dataBase.DBProperties;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import media.userNameMedia;

/**
 *
 * @author GanitGenius
 */
public class OrgSettingController implements Initializable {

    @FXML
    private TextField tfOrganizeName;
    @FXML
    private Rectangle retOrgLogo;
    @FXML
    private Button btnAttechLogo;
    @FXML
    private Button btnSaveOrganize;
    @FXML
    private TextField tfEmailId;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private TextField tfSmtpServer;

    private File file;

    private BufferedImage bufferedImage;

    private Image image;

    private String userId;

    private String imagePath;

    private userNameMedia usrIdMedia;

    DBConnection dbCon = new DBConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    @FXML
    private TextField tfWebSite;
    @FXML
    private TextField tfContactNumber;
    @FXML
    private TextArea taAdddress;

    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();

    public userNameMedia getUsrIdMedia() {
        return usrIdMedia;
    }

    public void setUsrIdMedia(userNameMedia usrIdMedia) {
        userId = usrIdMedia.getId();
        this.usrIdMedia = usrIdMedia;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        BooleanBinding boolenBind = tfOrganizeName.textProperty().isEmpty()
                .or(tfWebSite.textProperty().isEmpty()
                        .or(tfContactNumber.textProperty().isEmpty())
                        .or(taAdddress.textProperty().isEmpty()));
        btnSaveOrganize.disableProperty().bind(boolenBind);

    }

    @FXML
    private void btnSaveOrganizeOnClick(ActionEvent event) {

        if (isFoundData()) {
            if (imagePath != null) {
                updateOrganizeWithImage();
            } else {
                updateOrganizeWithOutImage();
            }
        } else {
            insertOrganizeWithImage();
        }

    }

    @FXML
    private void btnAttechLogoOnAction(ActionEvent event) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG (Joint Photographic Group)", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG (Joint Photographic Experts Group)", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG (Portable Network Graphics)", "*.png")
        );
        fileChooser.setTitle("Choise a Image File");
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            System.out.println(file);
            bufferedImage = ImageIO.read(file);
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            retOrgLogo.setFill(new ImagePattern(image));
            imagePath = file.getAbsolutePath();
        }

    }

    public void showDetails() {

        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from " + db + ".Organize where Id=?");
            pst.setString(1, "1");
            rs = pst.executeQuery();
            while (rs.next()) {
                tfOrganizeName.setText(rs.getString(2));
                tfWebSite.setText(rs.getString(3));
                tfContactNumber.setText(rs.getString(4));
                tfEmailId.setText(rs.getString(5));
                tfPassword.setText(rs.getString(6));
                tfSmtpServer.setText(rs.getString(7));
                taAdddress.setText(rs.getString(8));

                Blob blob = rs.getBlob(9);
                if (blob != null) {
                    ByteArrayInputStream in = new ByteArrayInputStream(blob.getBytes(1, (int) blob.length()));
                    image = new Image(in);
                    retOrgLogo.setFill(new ImagePattern(image));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrgSettingController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean isFoundData() {

        boolean dataFound = true;
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from " + db + ".Organize ORDER BY Id ASC LIMIT 1");
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println("Data Found");
                return dataFound;
            }
            System.out.println("Data not found");
            dataFound = false;

        } catch (SQLException ex) {
            Logger.getLogger(OrgSettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataFound;

    }

    private void updateOrganizeWithImage() {

        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("Update " + db + ".Organize set OrgName=?,OrgWeb=?,OrgContactNumbers=?,OrgEmailId=?, OrgPassword=?, OrgSmtpServer=?, OrgContactAddress=?,OrgLogo=? where Id=1");
            pst.setString(1, tfOrganizeName.getText());
            pst.setString(2, tfWebSite.getText());
            pst.setString(3, tfContactNumber.getText());
            pst.setString(4, tfEmailId.getText());
            pst.setString(5, tfPassword.getText());
            pst.setString(6, tfSmtpServer.getText());
            pst.setString(7, taAdddress.getText());
            if (imagePath != null) {
                try {
                    InputStream is = new FileInputStream(new File(imagePath));
                    pst.setBlob(8, is);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(OrgSettingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                pst.setBlob(8, (Blob) null);
            }
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Updated");
            alert.setHeaderText("Updated");
            alert.setContentText("Updated Successfully");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(OrgSettingController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void insertOrganizeWithImage() {

        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("insert into " + db + ".Organize values(?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, "1");
            pst.setString(2, tfOrganizeName.getText());
            pst.setString(3, tfWebSite.getText());
            pst.setString(4, tfContactNumber.getText());
            pst.setString(5, tfEmailId.getText());
            pst.setString(6, tfPassword.getText());
            pst.setString(7, tfSmtpServer.getText());
            pst.setString(8, taAdddress.getText());
            if (imagePath != null) {
                try {
                    InputStream is = new FileInputStream(new File(imagePath));
                    pst.setBlob(9, is);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(OrgSettingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                pst.setBlob(9, (Blob) null);
            }
            pst.setString(10, userId);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update");
            alert.setHeaderText("Successful");
            alert.setContentText("Data Inserted Successfully");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(OrgSettingController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    private void updateOrganizeWithOutImage() {
        
        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("Update " + db + ".Organize set OrgName=?,OrgWeb=?,OrgContactNumbers=?,OrgEmailId=?, OrgPassword=?, OrgSmtpServer=?, OrgContactAddress=? where Id=1");

            pst.setString(1, tfOrganizeName.getText());
            pst.setString(2, tfWebSite.getText());
            pst.setString(3, tfContactNumber.getText());
            pst.setString(4, tfEmailId.getText());
            pst.setString(5, tfPassword.getText());
            pst.setString(6, tfSmtpServer.getText());
            pst.setString(7, taAdddress.getText());

            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update");
            alert.setHeaderText("Successful");
            alert.setContentText("Update successfully");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(OrgSettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
