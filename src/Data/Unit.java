/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import List.ListUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author GanitGenius
 */
public class Unit {

    public String id;
    public String unitName;
    public String unitDescription;
    public String creatorId;
    public String creatorName;
    public String date;

    public ObservableList<ListUnit> unitDetails = FXCollections.observableArrayList();

}
