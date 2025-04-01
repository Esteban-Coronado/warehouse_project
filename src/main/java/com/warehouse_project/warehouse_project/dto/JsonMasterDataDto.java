package com.warehouse_project.warehouse_project.dto;

import lombok.Data;

import java.util.List;

@Data
public class JsonMasterDataDto {
    private VirtualWarehouseConfigDto virtualWarehouseConfig;
    private List<ProductDto> products;
}