/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import List.ListRma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author GanitGenius
 */
public class RMA {

    public String id;
    public String rmaName;
    public String rmaDays;
    public String rmaComment;
    public String creatorId;
    public String creatorName;
    public String date;

    public ObservableList<ListRma> rmaDetails = FXCollections.observableArrayList();

}
