package com.warehouse_project.warehouse_project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehouse_project.warehouse_project.dto.WarehouseCreationDto;
import com.warehouse_project.warehouse_project.model.Product;
import com.warehouse_project.warehouse_project.model.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceTest {

    @Mock
    private Resource warehousesResource;

    @Mock
    private JsonMasterDataService jsonMasterDataService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private WarehouseService warehouseService;

    @BeforeEach
    void setUp() throws IOException {
        when(warehousesResource.getInputStream())
            .thenReturn(new ByteArrayInputStream("[]".getBytes()));
    }

    @Test
    void createWarehouse_ShouldCreateNewWarehouse() throws IOException {
        WarehouseCreationDto creationDto = new WarehouseCreationDto();
        creationDto.setName("Almacén Test");
        creationDto.setLocation("Ubicación Test");

        Product sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setName("Producto Test");
        sampleProduct.setStock(100);

        when(jsonMasterDataService.getProductsFromMasterData())
            .thenReturn(List.of(sampleProduct));
        when(objectMapper.writeValueAsString(any())).thenReturn("[]");

        Warehouse result = warehouseService.createWarehouse(creationDto);

        assertNotNull(result);
        assertEquals("Almacén Test", result.getName());
        assertEquals(1, result.getProducts().size());
        assertEquals("Producto Test", result.getProducts().get(0).getName());
    }

    @Test
    void getAllWarehouses_ShouldReturnList() throws IOException {
        when(objectMapper.readValue(any(InputStream.class), eq(Warehouse[].class)))
    .thenReturn(new Warehouse[] { new Warehouse() });

        List<Warehouse> result = warehouseService.getAllWarehouses();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}