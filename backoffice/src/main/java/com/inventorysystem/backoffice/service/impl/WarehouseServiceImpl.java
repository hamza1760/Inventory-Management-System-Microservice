package com.inventorysystem.backoffice.service.impl;

import com.inventorysystem.backoffice.dto.InventoryDetailDTO;
import com.inventorysystem.backoffice.dto.ItemQuantityDTO;
import com.inventorysystem.backoffice.dto.WarehouseDTO;
import com.inventorysystem.backoffice.entity.InventoryDetail;
import com.inventorysystem.backoffice.entity.Warehouse;
import com.inventorysystem.backoffice.repository.AddressRepository;
import com.inventorysystem.backoffice.repository.InventoryDetailRepository;
import com.inventorysystem.backoffice.repository.WarehouseRepository;
import com.inventorysystem.backoffice.service.WarehouseService;
import com.inventorysystem.common.customexception.BusinessException;
import com.inventorysystem.common.utilities.Constants;
import com.inventorysystem.common.utilities.ModelConverter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Warehouse service interface implementation.
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {


    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private InventoryDetailRepository inventoryDetailRepository;

    /**
     * To add warehouse in database.
     *
     * @param warehouseDTO Object of WarehouseDTO.
     * @return The warehouse that is added in database.
     */
    @Override
    public WarehouseDTO addWarehouse(WarehouseDTO warehouseDTO) {

        String warehouseName = warehouseDTO.getWarehouseName();
        Warehouse warehouse = warehouseRepository.findByWarehouseName(warehouseName);
        if (warehouse != null) {
            throw new BusinessException("Warehouse already present");
        }
        warehouseDTO.setAddress(warehouseDTO.getAddress());
        warehouseRepository.save(ModelConverter.dtoToEntity(warehouseDTO, Warehouse.class));
        return warehouseDTO;
    }

    /**
     * Get the list of warehouses available in database.
     *
     * @return list of warehouses.
     */
    @Override
    public List<WarehouseDTO> getWarehouse() {

        List<Warehouse> warehouses = warehouseRepository.findAll();
        return ModelConverter.listEntityToDto(warehouses, WarehouseDTO.class);
    }

    /**
     * To get single warehouse based on the id of warehouse.
     *
     * @param warehouseId The id of the warehouse to search warehouse in database.
     * @return Single warehouse that matches the warehouseId.
     */
    @Override
    public WarehouseDTO getWarehouseById(int warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
            .orElseThrow(() -> new BusinessException("Warehouse not found"));
        return ModelConverter.entityToDto(warehouse, WarehouseDTO.class);
    }

    /**
     * Adding inventory in warehouse
     *
     * @param inventoryDetailDTO Object of InventoryDetailDTO to get inventoryId of the inventory to be added in warehouse.
     * @param warehouseId        The id of the warehouse to search warehouse in database.
     * @return Warehouse in which the inventory is added.
     */
    public WarehouseDTO placeInventoryInWarehouse(InventoryDetailDTO inventoryDetailDTO, int warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
            .orElseThrow(() -> new BusinessException("Warehouse not found"));

        int inventoryId = inventoryDetailDTO.getInventoryId();
        InventoryDetail inventoryDetail = inventoryDetailRepository.findById(inventoryId)
            .orElseThrow(() -> new BusinessException(Constants.INVENTORY_NOT_FOUND));

        Warehouse checkWarehouse = inventoryDetail.getWarehouse();
        if (null != checkWarehouse && !checkWarehouse.isDeleted()) {
            throw new BusinessException("Inventory is already present in other warehouse");


        } else {
            inventoryDetail.setWarehouse(warehouse);
            inventoryDetailRepository.save(inventoryDetail);
        }
        return ModelConverter.entityToDto(warehouse, WarehouseDTO.class);
    }

    /**
     * Get the item quantity in all warehouses present in database.
     *
     * @return List of inventories of the item present in all warehouses.
     */
    @Override
    public List<ItemQuantityDTO> getItemQuantityInAllWarehouses() {
        List<ItemQuantityDTO> itemQuantityDTO = warehouseRepository.getItemQuantityAllWarehouses();
        return itemQuantityDTO;
    }

    /**
     * Get the item quantity in single warehouse.
     *
     * @param warehouseId The id of the warehouse to search warehouse in database.
     * @return List of inventories of the item present in particular warehouse.
     */
    @Override
    public List<ItemQuantityDTO> getItemQuantityInSingleWarehouse(int warehouseId) {
        warehouseRepository.findById(warehouseId)
            .orElseThrow(() -> new BusinessException("Warehouse not found"));
        List<ItemQuantityDTO> itemQuantityDTO = warehouseRepository.getItemQuantityInSingleWarehouse(warehouseId);
        return itemQuantityDTO;
    }


}

