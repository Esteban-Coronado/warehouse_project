package com.warehouse_project.warehouse_project.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private int stock;
    private String category;
    private double price;
}
