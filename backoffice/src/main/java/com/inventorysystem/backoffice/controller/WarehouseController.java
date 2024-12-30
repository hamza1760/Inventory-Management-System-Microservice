package com.inventorysystem.backoffice.controller;

import static com.inventorysystem.common.utilities.ApiEndPointConstants.API;
import static com.inventorysystem.common.utilities.ApiEndPointConstants.WAREHOUSE;

import com.inventorysystem.backoffice.dto.InventoryDetailDTO;
import com.inventorysystem.backoffice.dto.ItemQuantityDTO;
import com.inventorysystem.backoffice.dto.WarehouseDTO;
import com.inventorysystem.backoffice.service.InventoryService;
import com.inventorysystem.backoffice.service.WarehouseService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Warehouse Controller
 */
@RestController
@CrossOrigin("*")
@RequestMapping(API + WAREHOUSE)
public class WarehouseController {


    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private InventoryService inventoryService;

    /**
     * To add warehouse in database.
     *
     * @param warehouseDTO Object of WarehouseDTO.
     * @return The warehouse that is added in database.
     */
    @PostMapping()
    public WarehouseDTO addWarehouse(@Valid @RequestBody WarehouseDTO warehouseDTO) {
        return warehouseService.addWarehouse(warehouseDTO);
    }

    /**
     * Get the list of warehouses available in database.
     *
     * @return list of warehouses.
     */
    @GetMapping()
    public List<WarehouseDTO> getWarehouse() {
        return warehouseService.getWarehouse();
    }

    /**
     * To get single warehouse based on the id of warehouse.
     *
     * @param warehouseId The id of the warehouse to search warehouse in database.
     * @return Single warehouse that matches the warehouseId.
     */
    @GetMapping("/{warehouseId}")
    public WarehouseDTO getWarehouseById(@PathVariable int warehouseId) {
        return warehouseService.getWarehouseById(warehouseId);
    }

    /**
     * Adding inventory in warehouse
     *
     * @param inventoryDetailDTO Object of InventoryDetailDTO to get inventoryId of the inventory to be added in warehouse.
     * @param warehouseId        The id of the warehouse to search warehouse in database.
     * @return Warehouse in which the inventory is added.
     */
    @PutMapping("/{warehouseId}")
    public WarehouseDTO placeInventoryInWarehouse(@RequestBody InventoryDetailDTO inventoryDetailDTO, @PathVariable int warehouseId) {
        return warehouseService.placeInventoryInWarehouse(inventoryDetailDTO, warehouseId);
    }

    /**
     * Get the item quantity in all warehouses present in database.
     *
     * @return List of inventories of the item present in all warehouses.
     */
    @GetMapping("/item-quantity")
    public List<ItemQuantityDTO> getItemQuantityInAllWarehouses() {
        return warehouseService.getItemQuantityInAllWarehouses();
    }

    /**
     * Get the item quantity in single warehouse.
     *
     * @param warehouseId The id of the warehouse to search warehouse in database.
     * @return List of inventories of the item present in particular warehouse.
     */
    @GetMapping("/warehouseId}/item-quantity")
    public List<ItemQuantityDTO> getItemQuantityInSingleWarehouse(@PathVariable int warehouseId) {
        return warehouseService.getItemQuantityInSingleWarehouse(warehouseId);
    }


}
