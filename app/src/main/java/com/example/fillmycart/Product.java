package com.example.fillmycart;

public class Product {
    String Category;
    String Name;
    String price;

    public Product(String category, String name, String price) {
        Category = category;
        Name = name;
        this.price = price;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
