package com.example.fillmycart;

public class PendingProduct {
    private String Price;
    private String ProductName;

    public PendingProduct(String price, String productName) {
        this.Price = price;
        ProductName = productName;
    }

    public PendingProduct() {
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }
}
