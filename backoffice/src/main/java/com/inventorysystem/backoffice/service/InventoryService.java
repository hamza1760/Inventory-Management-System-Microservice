package com.inventorysystem.backoffice.service;


import com.inventorysystem.backoffice.dto.InventoryDetailDTO;
import java.util.List;

/**
 * Inventory service interface.
 */
public interface InventoryService {

    InventoryDetailDTO addInventory(InventoryDetailDTO inventoryDetailDTO);

    List<InventoryDetailDTO> getInventory();

    InventoryDetailDTO getInventoryById(int inventoryId);

    void deleteInventoryById(int inventoryId);
}
