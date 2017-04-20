/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import List.ListBrands;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author GanitGenius
 */
public class Brands {

    public String id;
    public String brandName;
    public String brandComment;
    public String supplierId;
    public String creatorId;
    public String date;
    public String supplierName;
    public String creatorName;

    public ObservableList<ListBrands> brandDitails = FXCollections.observableArrayList();

}
