/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import List.ListCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author GanitGenius
 */
public class Customer {

    public String id;
    public String customerName;
    public String customerConNo;
    public String customerAddress;
    public String totalBuy;
    public String date;
    public String userId;
    public String userName;

    public ObservableList<ListCustomer> customerList = FXCollections.observableArrayList();

}
