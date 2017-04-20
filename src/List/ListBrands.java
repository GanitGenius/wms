/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package List;

/**
 *
 * @author GanitGenius
 */
public class ListBrands {
    public String id;
    public String brandName;
    public String brandComment;
    public String supplierName;
    public String creatorId;
    public String date;

    public ListBrands(String id, String brandName, String brandComment, String supplierName, String creatorId, String date) {
        this.id = id;
        this.brandName = brandName;
        this.brandComment = brandComment;
        this.supplierName = supplierName;
        this.creatorId = creatorId;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandComment() {
        return brandComment;
    }

    public void setBrandComment(String brandComment) {
        this.brandComment = brandComment;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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
