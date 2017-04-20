/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import List.ListSupplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author GanitGenius
 */
public class Supplier {

    public String id;
    public String supplierName;
    public String supplierContactNumber;
    public String supplierAddress;
    public String supplierDescription;
    public String creatorId;
    public String date;

    public ObservableList<ListSupplier> supplierDetails = FXCollections.observableArrayList();

}
