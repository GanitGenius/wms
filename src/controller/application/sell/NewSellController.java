/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.sell;

import Setup.SellCartSetup;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import Data.CurrentProduct;
import Gateway.CurrentProductGateway;
import Gateway.CustomerGateway;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import media.userNameMedia;
import Data.Customer;
import Data.SellCart;
import Gateway.SellCartGateway;
import List.ListCustomer;
import List.ListPreSell;
import custom.CustomTf;
import custom.RandomIdGenarator;
import dataBase.DBConnection;
import dataBase.DBProperties;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import printInv.GenerateInvoice;

/**
 *
 * @author GanitGenius
 */
public class NewSellController implements Initializable {

    public Button btnAddCustomer;
    userNameMedia nameMedia;

    String userId;
    @FXML
    private MenuButton mbtnCustomer;
    @FXML
    private TableView<ListCustomer> tblCustomerSortView;
    @FXML
    private TableColumn<Object, Object> tblClmCustomerName;
    @FXML
    private TableColumn<Object, Object> tblClmCustomerPhoneNo;
    @FXML
    private TextField tfCustomerSearch;
    @FXML
    private Button btnClose;

    String customerId;
    @FXML
    public TextField tfProductId;
    @FXML
    private TableView<ListPreSell> tblSellPreList;
    @FXML
    private TableColumn<Object, Object> tblClmSellId;
    @FXML
    private TableColumn<Object, Object> tblClmProductId;
    @FXML
    private TableColumn<Object, Object> tblClmSellPrice;
    @FXML
    private TableColumn<Object, Object> tblClmCustomer;
    @FXML
    private TableColumn<Object, Object> tblClmSolledBy;
    @FXML
    private TableColumn<Object, Object> tblClmWarrentyVoidDate;
    @FXML
    private TableColumn<Object, Object> tblClmQuantity;
    @FXML
    private TableColumn<Object, Object> tblClmTotalPrice;
    @FXML
    private TextField tfQuantity;
    @FXML
    private Label lblCurrentQuantity;
    @FXML
    private TextField tfSellPrice;
    @FXML
    private Label lblPurchasePrice;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblNetCost;
    private Label lblDiscount;
    @FXML
    private Label lblUnit;
    @FXML
    private TextField tfSupplier;
    @FXML
    private TextField tfBrand;
    @FXML
    private TextField tfCategory;
    @FXML
    private TextField tfWarrentVoidDate;
    @FXML
    private Button btnAddToChart;
    @FXML
    private Button btnSell;
    @FXML
    private Label lblPurchaseDate;
    int quantity;
    @FXML
    private Label lblTotalItems;
    @FXML
    private TextField tfProductName;
    @FXML
    private Button btnClearAll;
    @FXML
    private Button btnClearSelected;
    @FXML
    private Label lblSellId;

    public void setNameMedia(userNameMedia nameMedia) {
        userId = nameMedia.getId();
        this.nameMedia = nameMedia;
    }

    Customer customer = new Customer();
    CustomerGateway customerGateway = new CustomerGateway();
    CurrentProduct currrentProduct = new CurrentProduct();
    CurrentProductGateway currentProductGateway = new CurrentProductGateway();
    SellCart sellCart = new SellCart();
    SellCartGateway sellCartGateway = new SellCartGateway();
    SellCartSetup scbll = new SellCartSetup();
    CustomTf ctf = new CustomTf();

    ObservableList<ListPreSell> preList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clearAll();

    }

    @FXML
    private void tblCustomerOnClick(MouseEvent event) {

        mbtnCustomer.setText(tblCustomerSortView.getSelectionModel().getSelectedItem().getCustomerName());
        customerId = tblCustomerSortView.getSelectionModel().getSelectedItem().getId();
        System.out.println(customerId);

    }

    @FXML
    private void mbtnCustomerOnClicked(MouseEvent event) {

        customer.customerName = tfCustomerSearch.getText().trim();
        tblCustomerSortView.setItems(customer.customerList);
        tblClmCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblClmCustomerPhoneNo.setCellValueFactory(new PropertyValueFactory<>("customerContNo"));
        customerGateway.searchView(customer);

    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {

        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void tfCustomerSearchOnKeyReleased(KeyEvent event) {

        customer.customerName = tfCustomerSearch.getText().trim();
        tblCustomerSortView.setItems(customer.customerList);
        tblClmCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblClmCustomerPhoneNo.setCellValueFactory(new PropertyValueFactory<>("customerContNo"));
        customerGateway.searchView(customer);

    }

    @FXML
    public void tfProductIdOnAction(ActionEvent event) {

        if (tfProductId.getText().isEmpty()) {
            clearAll();
        } else {
            currrentProduct.productId = tfProductId.getText().trim();
            currentProductGateway.sView(currrentProduct);
            lblUnit.setText(currrentProduct.unitName);
            lblCurrentQuantity.setText(currrentProduct.quantity);
            lblPurchasePrice.setText(currrentProduct.purchasePrice);
            tfBrand.setText(currrentProduct.brandName);
            tfSupplier.setText(currrentProduct.supplierName);
            tfCategory.setText(currrentProduct.categoryName);
            tfWarrentVoidDate.setText(currrentProduct.warrentyVoidDate);
            lblPurchaseDate.setText(currrentProduct.date);
            tfProductName.setText(currrentProduct.productName);
            tfSellPrice.setText(currrentProduct.sellPrice);

        }

    }

    @FXML
    private void btnAddToChartOnAction(ActionEvent event) {

        if (inNotNull()) {
            preList.add(new ListPreSell(currrentProduct.id, currrentProduct.productId, customerId, currrentProduct.purchasePrice, tfSellPrice.getText(), lblCurrentQuantity.getText(), tfQuantity.getText(), lblNetCost.getText(), currrentProduct.date, tfWarrentVoidDate.getText(), userId, LocalDateTime.now().toString()));
            viewAll();
            sumTotalCost();
            clearAll();

        }

    }

    private void sumTotalCost() {

        tblSellPreList.getSelectionModel().selectFirst();
        float sum = 0;
        int items = tblSellPreList.getItems().size();
        for (int i = 0; i < items; i++) {
            tblSellPreList.getSelectionModel().select(i);
            String selectedItem = tblSellPreList.getSelectionModel().getSelectedItem().getTotalPrice();
            float newFloat = Float.parseFloat(selectedItem);
            sum = sum + newFloat;
        }
        String totalCost = String.valueOf(sum);
        lblTotal.setText(totalCost);
        System.out.println("Total:" + sum);
        String totalItem = String.valueOf(items);
        lblTotalItems.setText(totalItem);

    }

    public void viewAll() {

        tblSellPreList.setItems(preList);
        tblClmProductId.setCellValueFactory(new PropertyValueFactory<>("productID"));
        tblClmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblClmSellPrice.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        tblClmTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        tblClmWarrentyVoidDate.setCellValueFactory(new PropertyValueFactory<>("warrentyVoidDate"));

    }

    @FXML
    private void btnSellOnAction(ActionEvent event) {

        if (!tblSellPreList.getItems().isEmpty()) {
            System.out.println(lblSellId.getText());
            int indexs = tblSellPreList.getItems().size();
            GenerateInvoice generateInvoice = new GenerateInvoice();

            generateInvoice.setN(indexs);
            generateInvoice.setSellId(lblSellId.getText());
            generateInvoice.setCustName(mbtnCustomer.getText());
            generateInvoice.setTotal(lblTotal.getText());
            System.out.println("indexs ====" + indexs);
            for (int i = 0; i < indexs; i++) {
                tblSellPreList.getSelectionModel().select(i);
                sellCart.Id = tblSellPreList.getSelectionModel().getSelectedItem().getId();
                sellCart.productID = tblSellPreList.getSelectionModel().getSelectedItem().getProductID();
                sellCart.sellID = lblSellId.getText();
                sellCart.customerID = customerId;
                sellCart.purchasePrice = tblSellPreList.getSelectionModel().getSelectedItem().getPurchasePrice();
                System.out.println("Purchase price = " + sellCart.purchasePrice);
                sellCart.sellPrice = tblSellPreList.getSelectionModel().getSelectedItem().getSellPrice();
                sellCart.quantity = tblSellPreList.getSelectionModel().getSelectedItem().getQuantity();
                sellCart.oldQuentity = tblSellPreList.getSelectionModel().getSelectedItem().getOldQuantity();
                sellCart.totalPrice = tblSellPreList.getSelectionModel().getSelectedItem().getTotalPrice();
                sellCart.warrentyVoidDate = tblSellPreList.getSelectionModel().getSelectedItem().getWarrentyVoidDate();
                sellCart.sellerID = userId;
                scbll.sell(sellCart);
                generateInvoice.sets1(sellCart.productID, i);
                generateInvoice.sets2(sellCart.quantity, i);
                generateInvoice.sets3(sellCart.sellPrice, i);
                generateInvoice.sets4(sellCart.totalPrice, i);
                System.out.println("Old Quantity:" + tblSellPreList.getSelectionModel().getSelectedItem().getOldQuantity());
            }

            try {
                generateInvoice.invoice();
            } catch (PrinterException ex) {
                Logger.getLogger(NewSellController.class.getName()).log(Level.SEVERE, null, ex);
            }
            String emailId[] = {customer.customerConNo};

            DBConnection dbCon = new DBConnection();
            Connection con;
            PreparedStatement pst;
            ResultSet rs;
            DBProperties dBProperties = new DBProperties();
            String db = dBProperties.loadPropertiesFile();

            con = dbCon.geConnection();
            String orgEmail = "";
            String orgPass = "";
            String orgHost = "";
            try {
                pst = con.prepareStatement("select * from " + db + ".Organize where Id=1");
                rs = pst.executeQuery();
                while (rs.next()) {
                    orgEmail = rs.getString(5);
                    orgPass = rs.getString(6);
                    orgHost = rs.getString(7);
                    System.out.println(orgEmail + "\t" + orgPass + "\t" + orgHost);

                }
            } catch (SQLException ex) {
                Logger.getLogger(NewSellController.class.getName()).log(Level.SEVERE, null, ex);
            }

            sendFromGMail(orgEmail, orgPass, emailId, orgHost);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Sold");
            alert.setContentText("Sold Successfully");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            tblSellPreList.getItems().clear();
            lblTotal.setText(null);
            System.out.println("Customer ID: " + customerId);
        } else {
            System.out.println("EMPTY");
        }

    }

    public void clearAll() {

        tfBrand.clear();
        tfProductId.clear();
        tfCategory.clear();
        tfSellPrice.clear();
        tfSupplier.clear();
        tfWarrentVoidDate.clear();
        tfQuantity.clear();
        tfProductName.clear();
        lblCurrentQuantity.setText(null);
        lblNetCost.setText(null);
        lblPurchasePrice.setText(null);
        lblUnit.setText(null);
        lblPurchaseDate.setText(null);

    }

    @FXML
    private void tfQuantityOnAction(KeyEvent event) {

        if (!tfQuantity.getText().isEmpty()) {
            String givenQuentity = tfQuantity.getText();
            int givenQinInt = Integer.parseInt(givenQuentity);
            String currentQuentity = lblCurrentQuantity.getText();
            int currentQuentiInt = Integer.parseInt(currentQuentity);
            if (givenQinInt > currentQuentiInt) {
                System.out.println("BIG");
                tfQuantity.clear();
                lblNetCost.setText("0.0");
            } else {
                quantity = Integer.parseInt(tfQuantity.getText());
                float sellPrice = 0;
                if (tfSellPrice.getText().compareTo("") != 0) {
                    sellPrice = Float.parseFloat(tfSellPrice.getText());
                }
                String netPrice = String.valueOf(sellPrice * quantity);
                lblNetCost.setText(netPrice);
            }
        } else {
            lblNetCost.setText("0.0");
        }
    }

    @FXML
    private void tfSellPriceOnAction(KeyEvent event) {

        System.out.println("PRESSED");
        if (!tfSellPrice.getText().isEmpty()) {
            String quentity = tfQuantity.getText();
            int intQuentity = Integer.parseInt(quentity);
            String sellPrice = tfSellPrice.getText();
            float fSellPrice = Float.parseFloat(sellPrice);
            String sTotalPrice = String.valueOf(intQuentity * fSellPrice);
            lblNetCost.setText(sTotalPrice);
            System.out.println(sTotalPrice);
        } else {
            lblNetCost.setText("0.0");
        }

    }

    @FXML
    public void btnAddCustomerOnAction(ActionEvent actionEvent) {

        System.out.println(userId);
        AddCustomerController acc = new AddCustomerController();
        userNameMedia media = new userNameMedia();
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("/view/application/sell/AddCustomer.fxml"));
        try {
            fXMLLoader.load();
            Parent parent = fXMLLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            AddCustomerController addCustomerController = fXMLLoader.getController();
            media.setId(userId);
            addCustomerController.setNameMedia(nameMedia);
            addCustomerController.lblCustomerContent.setText("ADD CUSTOMER");
            addCustomerController.btnUpdate.setVisible(false);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ViewCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void btnClearAllOnAction(ActionEvent event) {

    }

    @FXML
    private void btnClearSelectedOnAction(ActionEvent event) {

        if (tblSellPreList.getItems().size() != 0) {
            System.out.println("Clicked");
            tblSellPreList.getItems().removeAll(tblSellPreList.getSelectionModel().getSelectedItems());
            sumTotalCost();
        } else {
            System.out.println("EMPTY");
        }

    }

    public void genarateSellID() {

        String id = RandomIdGenarator.randomstring();
        if (id.matches("001215")) {
            String nId = RandomIdGenarator.randomstring();
            lblSellId.setText(nId);
        } else {
            lblSellId.setText(id);
        }

    }

    public boolean inNotNull() {

        boolean isNotNull = false;
        if (mbtnCustomer.getText().matches("Select Customer") || tfSellPrice.getText() == null || tfQuantity.getText().trim().matches("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR");
            alert.setContentText("Please fill all required fields");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            return isNotNull;
        } else {
            isNotNull = true;
        }
        return isNotNull;

    }

    private static void sendFromGMail(String from, String pass, String[] to, String host) {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
//        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject("Invoice");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText("Your Invoice is ready.");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "Invoice.pdf";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }

    }

}
