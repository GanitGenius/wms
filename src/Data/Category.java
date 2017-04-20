/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import List.ListCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author GanitGenius
 */
public class Category {

    public String id;
    public String categoryName;
    public String categoryDescription;
    public String brandId;
    public String date;
    public String creatorId;
    public String creatorName;
    public String brandName;
    public String supplierId;
    public String supplierName;

    public ObservableList<ListCategory> categoryDetails = FXCollections.observableArrayList();

}
