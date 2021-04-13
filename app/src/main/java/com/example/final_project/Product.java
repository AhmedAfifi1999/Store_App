package com.example.final_project;

public class Product {

    private int id;
    private String title;
    private String description;
    private double price;
    private String image;
    private boolean cash;

    public Product(int id, String title, String description, double price, String image, boolean cash) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.cash = cash;
    }

    public Product(int id, String title, String description, double price, boolean cash) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.cash = cash;
    }

    public Product(String title, String description, double price, boolean cash) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.cash = cash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isCash() {
        return cash;
    }

    public void setCash(boolean cash) {
        this.cash = cash;
    }
}
