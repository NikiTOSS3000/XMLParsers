package com.epam.xml.model;

import java.util.ArrayList;

public final class Products {
    private ArrayList<Category> categories;

    public Products() {
        categories = new ArrayList<Category>();
    }

    public Products(ArrayList<Category> categories) {
        this.categories = categories;
    }

    
    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
