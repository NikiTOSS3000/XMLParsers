package com.epam.xml.model;

import java.util.ArrayList;

public final class Subcategory {
    private String name;
    private ArrayList<Product> products;

    public Subcategory() {
        products = new ArrayList<Product>();
    }

    public Subcategory(String name, ArrayList<Product> products) {
        this.name = name;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
