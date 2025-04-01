package com.warehouse_project.warehouse_project.model;

import lombok.Data;
import java.util.List;

@Data
public class VirtualWarehouse {
    public static final String VIRTUAL_WAREHOUSE_ID = "VIRTUAL-001";
    
    private String id = VIRTUAL_WAREHOUSE_ID;
    private String name = "Almacén Virtual";
    private List<Product> products;
}