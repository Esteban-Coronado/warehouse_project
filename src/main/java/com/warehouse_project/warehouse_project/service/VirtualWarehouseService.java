package com.warehouse_project.warehouse_project.service;

import com.warehouse_project.warehouse_project.dto.VirtualWarehouseConfigDto;
import com.warehouse_project.warehouse_project.model.Product;
import com.warehouse_project.warehouse_project.model.VirtualWarehouse;
import com.warehouse_project.warehouse_project.model.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VirtualWarehouseService {

    private static final Logger logger = LoggerFactory.getLogger(VirtualWarehouseService.class);

    private final WarehouseService warehouseService;
    private final JsonMasterDataService jsonMasterDataService;

    public VirtualWarehouseService(WarehouseService warehouseService, JsonMasterDataService jsonMasterDataService) {
        this.warehouseService = warehouseService;
        this.jsonMasterDataService = jsonMasterDataService;
        logger.info("Servicio de Almacén Virtual inicializado");
    }

    public VirtualWarehouse calculateVirtualWarehouse() {
        try {
            logger.debug("Calculando stock para almacén virtual");
            
            // Obtener configuración
            VirtualWarehouseConfigDto config = jsonMasterDataService.getVirtualWarehouseConfig();
            int percentage = config.getPercentage();
            
            // Obtener todos los almacenes físicos
            List<Warehouse> warehouses = warehouseService.getAllWarehouses();
            
            // Mapa para acumular los stocks virtuales
            Map<Long, Product> virtualProductsMap = new HashMap<>();
            
            for (Warehouse warehouse : warehouses) {
                for (Product physicalProduct : warehouse.getProducts()) {
                    Product virtualProduct = virtualProductsMap.computeIfAbsent(
                        physicalProduct.getId(),
                        id -> createVirtualProductTemplate(physicalProduct)
                    );
                    
                    // Calcular y sumar el porcentaje del stock físico
                    int virtualStock = (int) Math.round(physicalProduct.getStock() * (percentage / 100.0));
                    virtualProduct.setStock(virtualProduct.getStock() + virtualStock);
                }
            }
            
            // Crear el almacén virtual
            VirtualWarehouse virtualWarehouse = new VirtualWarehouse();
            virtualWarehouse.setProducts(new ArrayList<>(virtualProductsMap.values()));
            
            logger.info("Almacén virtual calculado con porcentaje: {}%", percentage);
            return virtualWarehouse;
            
        } catch (Exception e) {
            logger.error("Error al calcular almacén virtual", e);
            throw new RuntimeException("Error al calcular almacén virtual", e);
        }
    }

    private Product createVirtualProductTemplate(Product physicalProduct) {
        Product virtualProduct = new Product();
        virtualProduct.setId(physicalProduct.getId());
        virtualProduct.setName(physicalProduct.getName());
        virtualProduct.setCategory(physicalProduct.getCategory());
        virtualProduct.setPrice(physicalProduct.getPrice());
        virtualProduct.setStock(0); // Inicializar en 0
        return virtualProduct;
    }
}