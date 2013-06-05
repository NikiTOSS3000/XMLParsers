package com.epam.xml.model;

import java.util.ArrayList;

public final class Category {
    private String name;
    private ArrayList<Subcategory> subcategories;

    public Category() {
        subcategories = new ArrayList<Subcategory>();
    }

    public Category(String name, ArrayList<Subcategory> subcategories) {
        this.name = name;
        this.subcategories = subcategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Subcategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(ArrayList<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }
}
