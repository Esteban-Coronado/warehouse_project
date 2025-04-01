package com.warehouse_project.warehouse_project.service;

import com.warehouse_project.warehouse_project.dto.VirtualWarehouseConfigDto;
import com.warehouse_project.warehouse_project.model.Product;
import com.warehouse_project.warehouse_project.model.VirtualWarehouse;
import com.warehouse_project.warehouse_project.model.Warehouse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VirtualWarehouseServiceTest {

    @Mock
    private WarehouseService warehouseService;

    @Mock
    private JsonMasterDataService jsonMasterDataService;

    @InjectMocks
    private VirtualWarehouseService virtualWarehouseService;

    @Test
    void calculateVirtualWarehouse_ShouldCalculateCorrectly() throws IOException {
        // Configurar mocks
        VirtualWarehouseConfigDto config = new VirtualWarehouseConfigDto();
        config.setPercentage(20);
        when(jsonMasterDataService.getVirtualWarehouseConfig()).thenReturn(config);

        // Crear datos de prueba
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Producto A");
        product1.setPrice(100.0);
        product1.setStock(200); // 20% = 40

        Product product2 = new Product();
        product2.setId(1L);
        product2.setName("Producto A");
        product2.setPrice(150.0); // Precio más alto
        product2.setStock(300); // 20% = 60

        Warehouse warehouse1 = new Warehouse();
        warehouse1.setProducts(List.of(product1));

        Warehouse warehouse2 = new Warehouse();
        warehouse2.setProducts(List.of(product2));

        when(warehouseService.getAllWarehouses()).thenReturn(List.of(warehouse1, warehouse2));

        // Ejecutar
        VirtualWarehouse result = virtualWarehouseService.calculateVirtualWarehouse();

        // Verificar
        assertNotNull(result);
        assertEquals(1, result.getProducts().size());
        
        Product virtualProduct = result.getProducts().get(0);
        assertEquals(100, virtualProduct.getStock()); // 40 + 60
        assertEquals(150.0, virtualProduct.getPrice()); // Precio más alto
    }
}