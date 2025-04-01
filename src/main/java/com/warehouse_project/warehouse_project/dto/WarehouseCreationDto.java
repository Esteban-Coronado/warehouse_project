package com.warehouse_project.warehouse_project.dto;
import com.warehouse_project.warehouse_project.model.Coordinates;

import lombok.Data;

@Data
public class WarehouseCreationDto {
    private String name;
    private String location;
    private Coordinates coordinates;
}