package com.warehouse_project.warehouse_project.model;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private String category;
    private double price;
    private int stock;
    
    public Product() {}
    
    public Product(Long id, String name, int stock, String category, double price) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.category = category;
        this.price = price;
    }
}