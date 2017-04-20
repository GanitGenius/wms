/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import List.ListPreSell;
import List.ListSold;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author GanitGenius
 */
public class SellCart {

    public String Id;
    public String sellID;
    public String customerID;
    public String productID;
    public String purchasePrice;
    public String sellPrice;
    public String quantity;
    public String totalPrice;
    public String pursrsDate;
    public String warrentyVoidDate;
    public String sellerID;
    public String sellDate;
    public String oldQuentity;
    public String customerName;
    public String sellerName;
    public String givenProductID;

    public ObservableList<ListPreSell> carts = FXCollections.observableArrayList();
    public ObservableList<ListSold> soldList = FXCollections.observableArrayList();

}
