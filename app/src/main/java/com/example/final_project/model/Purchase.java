package com.example.final_project.model;

public  class Purchase {

    int id;
    int product_id;
    String date ,name;
    int quantity;

    public Purchase(int id, int product_id, String date, int quantity) {
        this.id = id;
        this.product_id = product_id;
        this.date = date;
        this.quantity = quantity;
    }

    public Purchase(int product_id, String date, String name, int quantity) {
        this.product_id = product_id;
        this.date = date;
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Purchase(int product_id, String date, int quantity) {
        this.product_id = product_id;
        this.date = date;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
