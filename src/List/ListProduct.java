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
public class ListProduct {
    public String id;
    public String productId;
    public String productName;
    public String quantity;
    public String description;
    public String suppliedBy;
    public String brand;
    public String category;
    public String unit;
    public String purchasePrice;
    public String sellPrice;
    public String discountInCash;
    public String discountInPersent;
    public String rma;
    public String user;
    public String date;

    public ListProduct(String id, String productId, String productName, String quantity, String description, String suppliedBy, String brand, String category, String unit, String purchasePrice, String sellPrice, String rma, String user, String date) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.description = description;
        this.suppliedBy = suppliedBy;
        this.brand = brand;
        this.category = category;
        this.unit = unit;
        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;
        this.rma = rma;
        this.user = user;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuppliedBy() {
        return suppliedBy;
    }

    public void setSuppliedBy(String suppliedBy) {
        this.suppliedBy = suppliedBy;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getDiscountInCash() {
        return discountInCash;
    }

    public void setDiscountInCash(String discountInCash) {
        this.discountInCash = discountInCash;
    }

    public String getDiscountInPersent() {
        return discountInPersent;
    }

    public void setDiscountInPersent(String discountInPersent) {
        this.discountInPersent = discountInPersent;
    }

    public String getRma() {
        return rma;
    }

    public void setRma(String rma) {
        this.rma = rma;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
