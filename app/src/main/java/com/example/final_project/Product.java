package com.example.final_project;

public class Product {

    private int id;
    private String productTitle;
    private String description;
    private double price;
    private String URLImage;
    private boolean cash;

    public Product(int id, String productTitle, String description, double price, String URLImage, boolean cash) {
        this.id = id;
        this.productTitle = productTitle;
        this.description = description;
        this.price = price;
        this.URLImage = URLImage;
        this.cash = cash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getURLImage() {
        return URLImage;
    }

    public void setURLImage(String URLImage) {
        this.URLImage = URLImage;
    }

    public boolean isCash() {
        return cash;
    }

    public void setCash(boolean cash) {
        this.cash = cash;
    }
}
