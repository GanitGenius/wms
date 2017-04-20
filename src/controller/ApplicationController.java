/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Data.Users;
import Gateway.UsersGateway;
import controller.application.EmployeController;
import controller.application.SellController;
import controller.application.SettingsController;
import controller.application.StockController;
import controller.application.home.HomeController;
import dataBase.DBConnection;
import dataBase.DBProperties;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import media.userNameMedia;

/**
 *
 * @author GanitGenius
 */
public class ApplicationController implements Initializable {

    @FXML
    private StackPane acContent;
    @FXML
    private ScrollPane leftSideBarScroolPan;
    @FXML
    private ToggleButton sideMenuToogleBtn;
    @FXML
    private ImageView imgMenuBtn;
    @FXML
    private BorderPane appContent;
    @FXML
    private Button btnLogOut;
    @FXML
    private MenuItem miPopOver;
    @FXML
    private AnchorPane acDashBord;
    @FXML
    private AnchorPane acHead;
    @FXML
    private AnchorPane acMain;
    @FXML
    private MenuButton mbtnUsrInfoBox;
    @FXML
    private Button btnHome;
    @FXML
    private ImageView imgHomeBtn;
    @FXML
    private Button btnStore;
    @FXML
    private ImageView imgStoreBtn;
    @FXML
    private Button btnEmplopye;
    @FXML
    private ImageView imgEmployeBtn;
    @FXML
    private Button btnSell;
    @FXML
    private ImageView imgSellBtn;
    @FXML
    private Button btnSettings;
    @FXML
    private ImageView imgSettingsBtn;
    @FXML
    private Button btnAbout;
    @FXML
    private ImageView imgAboutBtn;
    @FXML
    private Label lblUsrName;
    @FXML
    private Label lblUsrNamePopOver;
    @FXML
    private Label lblFullName;
    @FXML
    private Label lblRoleAs;
    @FXML
    private Hyperlink hlEditUpdateAccount;
    @FXML
    private Circle imgUsrTop;
    @FXML
    private Circle circleImgUsr;
    @FXML
    private Label lblUserId;

    String usrName;
    String id;

    DBConnection dbCon = new DBConnection();
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();

    Users users = new Users();
    UsersGateway usersGateway = new UsersGateway();

    private userNameMedia usrNameMedia;

    public userNameMedia getUsrNameMedia() {
        return usrNameMedia;
    }

    public void setUsrNameMedia(userNameMedia usrNameMedia) {

        lblUserId.setText(usrNameMedia.getId());
        lblUsrName.setText(usrNameMedia.getUsrName());
        id = usrNameMedia.getId();
        usrName = usrNameMedia.getUsrName();
        this.usrNameMedia = usrNameMedia;
    }

    Image menuImage = new Image("/icon/menu.png");
    Image menuImageRed = new Image("/icon/menuRed.png");
    Image image;

    String defultStyle = "-fx-border-width: 0px 0px 0px 5px;"
            + "-fx-border-color:none";

    String activeStyle = "-fx-border-width: 0px 0px 0px 5px;"
            + "-fx-border-color:#FF4E3C";

    Image home = new Image("/icon/home.png");
    Image homeSelected = new Image("/icon/homeSelected.png");
    Image stock = new Image("/icon/stock.png");
    Image stockSelected = new Image("/icon/stockSelected.png");
    Image sell = new Image("/icon/sell.png");
    Image sellSelected = new Image("/icon/sellSelected.png");
    Image employee = new Image("/icon/employee.png");
    Image employeeSelected = new Image("/icon/employeeSelected.png");
    Image setting = new Image("/icon/settings.png");
    Image settingsSelected = new Image("/icon/settingsSelected.png");
    Image about = new Image("/icon/about.png");
    Image aboutSelected = new Image("/icon/aboutSelected.png");

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        imgMenuBtn.setImage(menuImage);
        Image usrImg = new Image("/image/user.png");
        imgUsrTop.setFill(new ImagePattern(usrImg));
        circleImgUsr.setFill(new ImagePattern(usrImg));

    }

    @FXML
    private void sideMenuToogleBtnOnCLick(ActionEvent event) throws IOException {

        if (sideMenuToogleBtn.isSelected()) {
            imgMenuBtn.setImage(menuImageRed);
            TranslateTransition sideMenu = new TranslateTransition(Duration.millis(200.0), acDashBord);
            sideMenu.setByX(-130);
            sideMenu.play();
            acDashBord.getChildren().clear();
        } else {
            imgMenuBtn.setImage(menuImage);
            TranslateTransition sideMenu = new TranslateTransition(Duration.millis(200.0), acDashBord);
            sideMenu.setByX(130);
            sideMenu.play();
            acDashBord.getChildren().add(leftSideBarScroolPan);
        }

    }

    @FXML
    private void btnLogOut(ActionEvent event) throws IOException {

        acContent.getChildren().clear();
        acContent.getChildren().add(FXMLLoader.load(getClass().getResource("/view/Login.fxml")));
        acDashBord.getChildren().clear();
        acHead.getChildren().clear();
        acHead.setMaxHeight(0.0);

    }

    @FXML
    private void acMain(KeyEvent event) {

        if (event.getCode() == KeyCode.F11) {
            Stage stage = (Stage) acMain.getScene().getWindow();
            stage.setFullScreen(true);
        }

    }

    @FXML
    public void btnHomeOnClick(ActionEvent event) {

        homeActive();
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource("/view/application/home/Home.fxml").openStream());
        } catch (IOException e) {

        }
        AnchorPane root = fxmlLoader.getRoot();
        acContent.getChildren().clear();
        acContent.getChildren().add(root);

        System.out.println(lblUsrName.getText());
        System.out.println(lblUserId.getText());

    }

    @FXML
    private void btnStoreOnClick(ActionEvent event) throws IOException {

        sotreActive();
        StockController sc = new StockController();
        userNameMedia nm = new userNameMedia();
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.load(getClass().getResource("/view/application/Stock.fxml").openStream());
        nm.setId(id);
        StockController stockController = fXMLLoader.getController();
        stockController.bpStore.getStylesheets().add("/style/MainStyle.css");
        stockController.setUserId(usrNameMedia);
        stockController.btnStockOnAction(event);
        stockController.settingPermission();
        AnchorPane acPane = fXMLLoader.getRoot();
        acContent.getChildren().clear();
        acContent.getChildren().add(acPane);

    }

    @FXML
    private void btnEmplopyeOnClick(ActionEvent event) throws IOException {

        employeeActive();
        EmployeController ec = new EmployeController();
        userNameMedia nm = new userNameMedia();
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.load(getClass().getResource("/view/application/Employe.fxml").openStream());
        nm.setId(id);
        EmployeController employeController = fXMLLoader.getController();
        employeController.bpContent.getStylesheets().add("/style/MainStyle.css");
        employeController.setNameMedia(usrNameMedia);
        employeController.btnViewEmployeeOnAction(event);
        AnchorPane acPane = fXMLLoader.getRoot();
        acContent.getChildren().clear();
        acContent.getChildren().add(acPane);

    }

    @FXML
    private void btnSettingsOnClick(ActionEvent event) throws IOException {

        settingsActive();
        SettingsController settingController = new SettingsController();
        userNameMedia usrMedia = new userNameMedia();
        FXMLLoader settingLoader = new FXMLLoader();
        settingLoader.load(getClass().getResource("/view/application/Settings.fxml").openStream());
        usrMedia.setId(id);
        SettingsController settingControl = settingLoader.getController();
        settingControl.bpSettings.getStylesheets().add("/style/MainStyle.css");
        settingControl.setUsrMedia(usrMedia);
        settingControl.miMyASccountOnClick(event);
        settingControl.settingPermission();
        AnchorPane acPane = settingLoader.getRoot();
        acContent.getChildren().clear();
        acContent.getChildren().add(acPane);

    }

    @FXML
    private void btnAboutOnClick(ActionEvent event) {

        try {
            aboutActive();
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.load(getClass().getResource("/view/application/about/AboutMe.fxml").openStream());
            AnchorPane anchorPane = fXMLLoader.getRoot();
            acContent.getChildren().clear();
            acContent.getChildren().add(anchorPane);
        } catch (IOException ex) {
            Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void btnSellOnClick(ActionEvent event) {

        sellActive();
        SellController controller = new SellController();
        userNameMedia nm = new userNameMedia();
        try {
            FXMLLoader fXMLLoader = new FXMLLoader();
            fXMLLoader.load(getClass().getResource("/view/application/Sell.fxml").openStream());
            nm.setId(id);
            SellController sellController = fXMLLoader.getController();
            sellController.setNameMedia(usrNameMedia);
            sellController.acMainSells.getStylesheets().add("/style/MainStyle.css");
            sellController.tbtnSellOnAction(event);
            AnchorPane anchorPane = fXMLLoader.getRoot();
            acContent.getChildren().clear();
            acContent.getChildren().add(anchorPane);
        } catch (IOException ex) {
            Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void hlUpdateAccount(ActionEvent event) {

    }

    @FXML
    private void mbtnOnClick(ActionEvent event) {

    }

    @FXML
    private void acMainOnMouseMove(MouseEvent event) {

    }

    public void permission() {

        con = dbCon.geConnection();
        try {
            pst = con.prepareStatement("select * from " + db + ".UserPermission where UserId=?");
            pst.setString(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getInt(17) == 0) {
                    btnEmplopye.setDisable(true);
                }
                if (rs.getInt(15) == 0) {
                    btnSell.setDisable(true);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApplicationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void homeActive() {

        imgHomeBtn.setImage(homeSelected);
        imgStoreBtn.setImage(stock);
        imgSellBtn.setImage(sell);
        imgEmployeBtn.setImage(employee);
        imgSettingsBtn.setImage(setting);
        imgAboutBtn.setImage(about);
        btnHome.setStyle(activeStyle);
        btnStore.setStyle(defultStyle);
        btnSell.setStyle(defultStyle);
        btnEmplopye.setStyle(defultStyle);
        btnSettings.setStyle(defultStyle);
        btnAbout.setStyle(defultStyle);

    }

    private void sotreActive() {

        imgHomeBtn.setImage(home);
        imgStoreBtn.setImage(stockSelected);
        imgSellBtn.setImage(sell);
        imgEmployeBtn.setImage(employee);
        imgSettingsBtn.setImage(setting);
        imgAboutBtn.setImage(about);
        btnHome.setStyle(defultStyle);
        btnStore.setStyle(activeStyle);
        btnSell.setStyle(defultStyle);
        btnEmplopye.setStyle(defultStyle);
        btnSettings.setStyle(defultStyle);
        btnAbout.setStyle(defultStyle);

    }

    private void sellActive() {

        imgHomeBtn.setImage(home);
        imgStoreBtn.setImage(stock);
        imgSellBtn.setImage(sellSelected);
        imgEmployeBtn.setImage(employee);
        imgSettingsBtn.setImage(setting);
        imgAboutBtn.setImage(about);
        btnHome.setStyle(defultStyle);
        btnStore.setStyle(defultStyle);
        btnSell.setStyle(activeStyle);
        btnEmplopye.setStyle(defultStyle);
        btnSettings.setStyle(defultStyle);
        btnAbout.setStyle(defultStyle);

    }

    private void employeeActive() {

        imgHomeBtn.setImage(home);
        imgStoreBtn.setImage(stock);
        imgSellBtn.setImage(sell);
        imgEmployeBtn.setImage(employeeSelected);
        imgSettingsBtn.setImage(setting);
        imgAboutBtn.setImage(about);
        btnHome.setStyle(defultStyle);
        btnStore.setStyle(defultStyle);
        btnSell.setStyle(defultStyle);
        btnEmplopye.setStyle(activeStyle);
        btnSettings.setStyle(defultStyle);
        btnAbout.setStyle(defultStyle);

    }

    private void settingsActive() {

        imgHomeBtn.setImage(home);
        imgStoreBtn.setImage(stock);
        imgSellBtn.setImage(sell);
        imgEmployeBtn.setImage(employee);
        imgSettingsBtn.setImage(settingsSelected);
        imgAboutBtn.setImage(about);
        btnHome.setStyle(defultStyle);
        btnStore.setStyle(defultStyle);
        btnSell.setStyle(defultStyle);
        btnEmplopye.setStyle(defultStyle);
        btnSettings.setStyle(activeStyle);
        btnAbout.setStyle(defultStyle);

    }

    private void aboutActive() {

        imgHomeBtn.setImage(home);
        imgStoreBtn.setImage(stock);
        imgSellBtn.setImage(sell);
        imgEmployeBtn.setImage(employee);
        imgSettingsBtn.setImage(setting);
        imgAboutBtn.setImage(aboutSelected);
        btnHome.setStyle(defultStyle);
        btnStore.setStyle(defultStyle);
        btnSell.setStyle(defultStyle);
        btnEmplopye.setStyle(defultStyle);
        btnSettings.setStyle(defultStyle);
        btnAbout.setStyle(activeStyle);

    }

    public void viewDetails() {

        users.id = id;
        usersGateway.selectedView(users);
        image = users.image;
        circleImgUsr.setFill(new ImagePattern(image));
        imgUsrTop.setFill(new ImagePattern(image));
        lblFullName.setText(users.fullName);
        lblUsrNamePopOver.setText(users.userName);

    }

}
