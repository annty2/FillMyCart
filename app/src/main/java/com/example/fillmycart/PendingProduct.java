package com.example.fillmycart;

public class PendingProduct {
    private String Price;
    private String name;

    public PendingProduct() {
    }

    public PendingProduct(String price, String name) {
        Price = price;
        this.name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
