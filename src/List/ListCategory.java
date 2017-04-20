/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package List;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author GanitGenius
 */
public class ListCategory {
    public String id;
    public String categoryName;
    public String categoryDescription;
    public String brandId;
    public String supplierId;
    public String creatorId;
    public String date;

    public ListCategory(String id, String categoryName, String categoryDescription, String brandId, String supplierId, String creatorId, String date) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.brandId = brandId;
        this.supplierId = supplierId;
        this.creatorId = creatorId;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}