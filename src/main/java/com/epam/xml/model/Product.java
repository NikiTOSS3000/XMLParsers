package com.epam.xml.model;

import java.util.Date;

public final class Product {
    private String name;
    private String producer;
    private String model;
    private Date date;
    private String color;
    private boolean inStock;
    private int price;

    public Product() {
    }

    public Product(String producer, String model, Date date, String color, boolean inStock, int price) {
        this.producer = producer;
        this.model = model;
        this.date = date;
        this.color = color;
        this.inStock = inStock;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
