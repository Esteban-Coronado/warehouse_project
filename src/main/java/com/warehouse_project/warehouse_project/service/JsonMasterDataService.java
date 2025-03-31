package com.warehouse_project.warehouse_project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehouse_project.warehouse_project.dto.JsonMasterDataDto;
import com.warehouse_project.warehouse_project.dto.ProductDto;
import com.warehouse_project.warehouse_project.dto.VirtualWarehouseConfigDto;
import com.warehouse_project.warehouse_project.model.Product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class JsonMasterDataService {

    private static final Logger logger = LoggerFactory.getLogger(JsonMasterDataService.class);

    @Value("classpath:static/master-products.json")
    private Resource jsonResource;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private JsonMasterDataDto loadMasterData() throws IOException {
        logger.debug("Cargando datos maestros desde JSON");
        return objectMapper.readValue(jsonResource.getFile(), JsonMasterDataDto.class);
    }

    private void saveMasterData(JsonMasterDataDto data) throws IOException {
        logger.debug("Guardando datos maestros en JSON");
        String updatedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        Path path = Paths.get(jsonResource.getURI());
        Files.write(path, updatedJson.getBytes());
        logger.info("Datos maestros actualizados correctamente");
    }

    // Config methods
    public VirtualWarehouseConfigDto getVirtualWarehouseConfig() throws IOException {
        logger.debug("Obteniendo configuración del almacén virtual");
        return loadMasterData().getVirtualWarehouseConfig();
    }

    public VirtualWarehouseConfigDto updatePercentage(int newPercentage) throws IOException {
        logger.info("Actualizando porcentaje del almacén virtual a: {}", newPercentage);
        JsonMasterDataDto masterData = loadMasterData();
        masterData.getVirtualWarehouseConfig().setPercentage(newPercentage);
        saveMasterData(masterData);
        return masterData.getVirtualWarehouseConfig();
    }

    public VirtualWarehouseConfigDto updateMinDistance(int newMinDistance) throws IOException {
        logger.info("Actualizando distancia mínima a: {}", newMinDistance);
        JsonMasterDataDto masterData = loadMasterData();
        masterData.getVirtualWarehouseConfig().setMinDistance(newMinDistance);
        saveMasterData(masterData);
        return masterData.getVirtualWarehouseConfig();
    }

    // Product methods
    public List<ProductDto> getAllProducts() throws IOException {
        logger.debug("Obteniendo lista completa de productos");
        return loadMasterData().getProducts();
    }

    public ProductDto updateProductStock(Long productId, int newStock) throws IOException {
        logger.info("Actualizando stock del producto ID: {} a: {}", productId, newStock);
        JsonMasterDataDto masterData = loadMasterData();
        ProductDto product = masterData.getProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Producto no encontrado con ID: {}", productId);
                    return new RuntimeException("Producto no encontrado");
                });
        
        product.setStock(newStock);
        saveMasterData(masterData);
        return product;
    }

    public ProductDto updateProductPrice(Long productId, double newPrice) throws IOException {
        logger.info("Actualizando precio del producto ID: {} a: {}", productId, newPrice);
        JsonMasterDataDto masterData = loadMasterData();
        ProductDto product = masterData.getProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Producto no encontrado con ID: {}", productId);
                    return new RuntimeException("Producto no encontrado");
                });

        product.setPrice(newPrice);
        saveMasterData(masterData);
        return product;
    }

    public ProductDto updateProductCategory(Long productId, String newCategory) throws IOException {
        logger.info("Actualizando categoría del producto ID: {} a: {}", productId, newCategory);
        JsonMasterDataDto masterData = loadMasterData();
        ProductDto product = masterData.getProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Producto no encontrado con ID: {}", productId);
                    return new RuntimeException("Producto no encontrado");
                });

        product.setCategory(newCategory);
        saveMasterData(masterData);
        return product;
    }

    public ProductDto updateProductName(Long productId, String newName) throws IOException {
        logger.info("Actualizando nombre del producto ID: {} a: {}", productId, newName);
        JsonMasterDataDto masterData = loadMasterData();
        ProductDto product = masterData.getProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Producto no encontrado con ID: {}", productId);
                    return new RuntimeException("Producto no encontrado");
                });

        product.setName(newName);
        saveMasterData(masterData);
        return product;
    }

    public ProductDto addProduct(ProductDto newProduct) throws IOException {
        logger.info("Añadiendo nuevo producto: {}", newProduct);
        JsonMasterDataDto masterData = loadMasterData();
        masterData.getProducts().add(newProduct);
        saveMasterData(masterData);
        return newProduct;
    }

    public void deleteProduct(Long productId) throws IOException {
        logger.info("Eliminando producto con ID: {}", productId);
        JsonMasterDataDto masterData = loadMasterData();
        boolean removed = masterData.getProducts().removeIf(p -> p.getId().equals(productId));
        if (!removed) {
            logger.error("No se encontró producto con ID: {} para eliminar", productId);
            throw new RuntimeException("Producto no encontrado");
        }
        saveMasterData(masterData);
    }

    public List<Product> getProductsFromMasterData() throws IOException {
    logger.debug("Obteniendo productos desde JSON maestro");
    JsonMasterDataDto masterData = loadMasterData();
    return masterData.getProducts().stream()
            .map(dto -> new Product(dto.getId(), dto.getName(), dto.getStock(), dto.getCategory(), dto.getPrice()))
            .toList();
}
}