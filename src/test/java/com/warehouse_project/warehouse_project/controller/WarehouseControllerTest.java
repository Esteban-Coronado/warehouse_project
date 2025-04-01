package com.warehouse_project.warehouse_project.controller;

import com.warehouse_project.warehouse_project.dto.WarehouseCreationDto;
import com.warehouse_project.warehouse_project.model.Warehouse;
import com.warehouse_project.warehouse_project.service.WarehouseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WarehouseControllerTest {

    @Mock
    private WarehouseService warehouseService;

    @InjectMocks
    private WarehouseController warehouseController;

    @Test
    void createWarehouse_ShouldReturnCreatedWarehouse() throws IOException {
        WarehouseCreationDto creationDto = new WarehouseCreationDto();
        creationDto.setName("Test");
        creationDto.setLocation("Location");

        Warehouse expectedWarehouse = new Warehouse();
        expectedWarehouse.setName("Test");

        when(warehouseService.createWarehouse(creationDto)).thenReturn(expectedWarehouse);

        ResponseEntity<Warehouse> response = warehouseController.createWarehouse(creationDto);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Test", response.getBody().getName());
    }

    @Test
    void getAllWarehouses_ShouldReturnList() throws IOException {
        when(warehouseService.getAllWarehouses()).thenReturn(List.of(new Warehouse()));

        ResponseEntity<List<Warehouse>> response = warehouseController.getAllWarehouses();
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertFalse(response.getBody().isEmpty());
    }
}