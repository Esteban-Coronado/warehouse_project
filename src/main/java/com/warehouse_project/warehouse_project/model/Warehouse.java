package com.warehouse_project.warehouse_project.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class Warehouse {
    private String id;
    private String name;
    private String location;
    private List<Product> products;
    private Coordinates coordinates;




}