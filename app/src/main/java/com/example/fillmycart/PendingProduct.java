package com.example.fillmycart;

public class PendingProduct {
    private String Price;
    private String name;
    private String category;
    private String amount;
    private  int totalPrice;
    private  boolean isSelected;

    public String getCategory() {
        return category;
    }

    public PendingProduct(String price, String name, String category,String amount) {
        Price = price;
        this.name = name;
        this.category = category;
        this.amount= amount;

    }

    public void setCategory(String category) {
        this.category = category;
    }

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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
