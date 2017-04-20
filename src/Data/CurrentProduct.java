/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import List.ListProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author GanitGenius
 */
public class CurrentProduct {

    public String id;
    public String productId;
    public String productName;
    public String quantity;
    public String description;
    public String supplierId;
    public String brandId;
    public String categoryId;
    public String unitId;
    public String purchasePrice;
    public String sellPrice;
    public String rmaId;
    public String userId;
    public String date;
    public String warrentyVoidDate;
    public String supplierName;
    public String brandName;
    public String categoryName;
    public String unitName;
    public String rmaName;
    public String userName;
    public String rmaDayesss;
    public String rmaDayes;
    public String supplierList;
    public String width;
    public String height;
    public String depth;

    public ObservableList<ListProduct> currentProductList = FXCollections.observableArrayList();
}
