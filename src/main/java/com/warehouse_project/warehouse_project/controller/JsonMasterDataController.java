package com.warehouse_project.warehouse_project.controller;

import com.warehouse_project.warehouse_project.dto.ProductDto;
import com.warehouse_project.warehouse_project.dto.VirtualWarehouseConfigDto;
import com.warehouse_project.warehouse_project.service.JsonMasterDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/master-data")
public class JsonMasterDataController {

    private static final Logger logger = LoggerFactory.getLogger(JsonMasterDataController.class);

    private final JsonMasterDataService jsonMasterDataService;

    public JsonMasterDataController(JsonMasterDataService jsonMasterDataService) {
        this.jsonMasterDataService = jsonMasterDataService;
    }

    @GetMapping("/config")
    public ResponseEntity<VirtualWarehouseConfigDto> getVirtualWarehouseConfig() {
        logger.debug("Solicitando configuración del almacén virtual");
        try {
            return ResponseEntity.ok(jsonMasterDataService.getVirtualWarehouseConfig());
        } catch (IOException e) {
            logger.error("Error al obtener configuración del almacén virtual", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/config/percentage")
    public ResponseEntity<VirtualWarehouseConfigDto> updatePercentage(@RequestParam int percentage) {
        logger.info("Recibida solicitud para actualizar porcentaje a: {}", percentage);
        try {
            return ResponseEntity.ok(jsonMasterDataService.updatePercentage(percentage));
        } catch (IOException e) {
            logger.error("Error al actualizar porcentaje", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/config/min-distance")
    public ResponseEntity<VirtualWarehouseConfigDto> updateMinDistance(@RequestParam int minDistance) {
        logger.info("Recibida solicitud para actualizar distancia mínima a: {}", minDistance);
        try {
            return ResponseEntity.ok(jsonMasterDataService.updateMinDistance(minDistance));
        } catch (IOException e) {
            logger.error("Error al actualizar distancia mínima", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        logger.debug("Solicitando lista completa de productos");
        try {
            return ResponseEntity.ok(jsonMasterDataService.getAllProducts());
        } catch (IOException e) {
            logger.error("Error al obtener lista de productos", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/products/{productId}/stock")
    public ResponseEntity<ProductDto> updateProductStock(
            @PathVariable Long productId,
            @RequestParam int stock) {
        logger.info("Recibida solicitud para actualizar stock del producto {} a {}", productId, stock);
        try {
            return ResponseEntity.ok(jsonMasterDataService.updateProductStock(productId, stock));
        } catch (Exception e) {
            logger.error("Error al actualizar stock del producto {}", productId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto newProduct) {
        logger.info("Recibida solicitud para añadir nuevo producto: {}", newProduct);
        try {
            return ResponseEntity.ok(jsonMasterDataService.addProduct(newProduct));
        } catch (IOException e) {
            logger.error("Error al añadir nuevo producto", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        logger.info("Recibida solicitud para eliminar producto con ID: {}", productId);
        try {
            jsonMasterDataService.deleteProduct(productId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error al eliminar producto {}", productId, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}