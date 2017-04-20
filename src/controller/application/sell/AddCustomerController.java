/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.sell;

import Setup.CustomerSetup;
import Gateway.CustomerGateway;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import media.userNameMedia;
import Data.Customer;

/**
 *
 * @author GanitGenius
 */
public class AddCustomerController implements Initializable {
    @FXML
    private TextField tfCustomerName;
    @FXML
    private TextArea taCustomerContact;
    @FXML
    private TextArea taCustomerAddress;
    @FXML
    public Button btnSave;
    @FXML
    public Label lblCustomerContent;
    @FXML
    private Button btnClose;
    @FXML
    public Button btnUpdate;
    
    public String customerId;
    
    private String userId;
    
    userNameMedia nameMedia;
    

    public void setNameMedia(userNameMedia nameMedia) {
        userId = nameMedia.getId();
        this.nameMedia = nameMedia;
    }
    
    Customer customer = new Customer();
    CustomerGateway customerGateway = new CustomerGateway();
    CustomerSetup customerSetup = new CustomerSetup();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void btnSaveOnAction(ActionEvent event) {
        
        customer.customerName = tfCustomerName.getText().trim();
        customer.customerAddress = taCustomerAddress.getText().trim();
        customer.customerConNo = taCustomerContact.getText().trim();
        customer.userId = userId;
        customerSetup.save(customer);
        
    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
        
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        
        customer.id = customerId;
        customer.customerName = tfCustomerName.getText().trim();
        customer.customerAddress = taCustomerAddress.getText().trim();
        customer.customerConNo = taCustomerContact.getText().trim();
        customerSetup.update(customer);
        
    }
    
    public void viewDetails(){
        
        customer.id = customerId;
        customerGateway.selectedView(customer);
        tfCustomerName.setText(customer.customerName);
        taCustomerContact.setText(customer.customerConNo);
        taCustomerAddress.setText(customer.customerAddress);
        
    }
    
}
