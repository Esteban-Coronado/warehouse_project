package com.warehouse_project.warehouse_project.controller;

import com.warehouse_project.warehouse_project.model.VirtualWarehouse;
import com.warehouse_project.warehouse_project.service.VirtualWarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/almacen-virtual")
public class VirtualWarehouseController {

    private static final Logger logger = LoggerFactory.getLogger(VirtualWarehouseController.class);

    private final VirtualWarehouseService virtualWarehouseService;

    public VirtualWarehouseController(VirtualWarehouseService virtualWarehouseService) {
        this.virtualWarehouseService = virtualWarehouseService;
    }

    @GetMapping
    public ResponseEntity<VirtualWarehouse> getVirtualWarehouse() {
        logger.debug("Solicitando información del almacén virtual");
        try {
            VirtualWarehouse virtualWarehouse = virtualWarehouseService.calculateVirtualWarehouse();
            return ResponseEntity.ok(virtualWarehouse);
        } catch (Exception e) {
            logger.error("Error al obtener almacén virtual", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}