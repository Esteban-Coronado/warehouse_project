package com.warehouse_project.warehouse_project.model;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private int stock;
    
    public Product() {}
    
    public Product(Long id, String name, int stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }
}