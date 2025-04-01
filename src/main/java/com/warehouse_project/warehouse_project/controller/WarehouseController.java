package com.warehouse_project.warehouse_project.controller;

import com.warehouse_project.warehouse_project.dto.WarehouseCreationDto;
import com.warehouse_project.warehouse_project.model.Warehouse;
import com.warehouse_project.warehouse_project.service.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import com.warehouse_project.warehouse_project.model.Product;

@RestController
@RequestMapping("/api/almacenes")
public class WarehouseController {

    private static final Logger logger = LoggerFactory.getLogger(WarehouseController.class);

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody WarehouseCreationDto creationDto) {
        logger.info("Recibida solicitud para crear nuevo almacén: {}", creationDto.getName());
        try {
            Warehouse newWarehouse = warehouseService.createWarehouse(creationDto);
            return ResponseEntity.ok(newWarehouse);
        } catch (IOException e) {
            logger.error("Error al crear almacén", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        logger.debug("Solicitando todos los almacenes");
        try {
            return ResponseEntity.ok(warehouseService.getAllWarehouses());
        } catch (IOException e) {
            logger.error("Error al obtener almacenes", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable String id) {
        logger.debug("Solicitando almacén con ID: {}", id);
        try {
            return ResponseEntity.ok(warehouseService.getWarehouseById(id));
        } catch (Exception e) {
            logger.error("Error al obtener almacén con ID: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/productos")
    public ResponseEntity<List<Product>> getWarehouseProducts(@PathVariable String id) {
        logger.debug("Solicitando productos del almacén con ID: {}", id);
        try {
            Warehouse warehouse = warehouseService.getWarehouseById(id);
            return ResponseEntity.ok(warehouse.getProducts());
        } catch (Exception e) {
            logger.error("Error al obtener productos del almacén {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable String id) {
        logger.info("Recibida solicitud para eliminar almacén con ID: {}", id);
        try {
            warehouseService.deleteWarehouse(id);
            return ResponseEntity.noContent().build();
        } catch (IOException e) {
            logger.error("Error al eliminar almacén con ID: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/nombre/{name}")
    public ResponseEntity<Void> deleteWarehouseByName(@PathVariable String name) {
        logger.info("Recibida solicitud para eliminar almacén con nombre: {}", name);
        try {
            warehouseService.deleteWarehouseByName(name);
            return ResponseEntity.noContent().build();
        } catch (IOException e) {
            logger.error("Error al eliminar almacén con nombre: {}", name, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}