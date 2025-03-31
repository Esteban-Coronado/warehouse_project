package com.warehouse_project.warehouse_project.model;

import lombok.Data;
import java.util.List;

@Data
public class Warehouse {
    private String id;
    private String name;
    private String location;
    private List<Product> products;
}