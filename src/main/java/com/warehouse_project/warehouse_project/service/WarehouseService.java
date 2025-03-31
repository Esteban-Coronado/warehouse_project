package com.warehouse_project.warehouse_project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehouse_project.warehouse_project.dto.WarehouseCreationDto;
import com.warehouse_project.warehouse_project.model.Product;
import com.warehouse_project.warehouse_project.model.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class WarehouseService {

    private static final Logger logger = LoggerFactory.getLogger(WarehouseService.class);

    @Value("classpath:static/warehouses.json")
    private Resource warehousesResource;

    private final ObjectMapper objectMapper;
    private final JsonMasterDataService jsonMasterDataService;

    public WarehouseService(ObjectMapper objectMapper, JsonMasterDataService jsonMasterDataService) {
        this.objectMapper = objectMapper;
        this.jsonMasterDataService = jsonMasterDataService;
    }

    @PostConstruct
    private void init() throws IOException {
        if (!warehousesResource.exists()) {
            logger.info("Archivo warehouses.json no existe, creando uno nuevo");
            Path path = Paths.get("src/main/resources/static/warehouses.json");
            Files.createDirectories(path.getParent());
            Files.write(path, "[]".getBytes());
        }
    }

    private List<Warehouse> loadWarehouses() throws IOException {
        logger.debug("Cargando almacenes desde warehouses.json");
        return objectMapper.readValue(warehousesResource.getFile(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Warehouse.class));
    }

    private void saveWarehouses(List<Warehouse> warehouses) throws IOException {
        logger.debug("Guardando almacenes en warehouses.json");
        String updatedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(warehouses);
        Path path = Paths.get(warehousesResource.getURI());
        Files.write(path, updatedJson.getBytes());
        logger.info("Almacenes guardados correctamente. Total: {}", warehouses.size());
    }

    public Warehouse createWarehouse(WarehouseCreationDto creationDto) throws IOException {
        logger.info("Creando nuevo almacén: {}", creationDto.getName());

        // Obtener productos y configuración del JSON maestro
        List<Product> products = jsonMasterDataService.getProductsFromMasterData();
        int currentPercentage = jsonMasterDataService.getVirtualWarehouseConfig().getPercentage();

        // Crear nuevo almacén
        Warehouse newWarehouse = new Warehouse();
        newWarehouse.setId(UUID.randomUUID().toString());
        newWarehouse.setName(creationDto.getName());
        newWarehouse.setLocation(creationDto.getLocation());
        newWarehouse.setProducts(products);

        // Guardar en la lista de almacenes
        List<Warehouse> warehouses = loadWarehouses();
        warehouses.add(newWarehouse);
        saveWarehouses(warehouses);

        logger.info("Almacén creado exitosamente con ID: {}. Porcentaje virtual: {}",
                newWarehouse.getId(), currentPercentage);
        return newWarehouse;
    }

    public List<Warehouse> getAllWarehouses() throws IOException {
        List<Warehouse> warehouses = loadWarehouses();
        logger.debug("Obteniendo todos los almacenes. Total: {}", warehouses.size());
        return warehouses;
    }

    public Warehouse getWarehouseById(String id) throws IOException {
        logger.debug("Buscando almacén con ID: {}", id);
        return loadWarehouses().stream()
                .filter(w -> w.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Almacén no encontrado con ID: {}", id);
                    return new RuntimeException("Almacén no encontrado");
                });
    }

    public void deleteWarehouse(String id) throws IOException {
        logger.info("Eliminando almacén con ID: {}", id);
        List<Warehouse> warehouses = loadWarehouses();
        boolean removed = warehouses.removeIf(w -> w.getId().equals(id));
        if (removed) {
            saveWarehouses(warehouses);
            logger.info("Almacén eliminado exitosamente con ID: {}", id);
        } else {
            logger.error("No se encontró un almacén con ID: {}", id);
            throw new RuntimeException("Almacén no encontrado");
        }
    }

    public void deleteWarehouseByName(String name) throws IOException {
        logger.info("Eliminando almacén con nombre: {}", name);
        List<Warehouse> warehouses = loadWarehouses();
        boolean removed = warehouses.removeIf(w -> w.getName().equalsIgnoreCase(name));
        if (removed) {
            saveWarehouses(warehouses);
            logger.info("Almacén eliminado exitosamente con nombre: {}", name);
        } else {
            logger.error("No se encontró un almacén con nombre: {}", name);
            throw new RuntimeException("Almacén no encontrado");
        }
    }
}