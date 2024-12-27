package com.inventorysystem.backoffice.service;

import com.inventorysystem.backoffice.dto.InventoryDetailDTO;
import com.inventorysystem.backoffice.dto.ItemQuantityDTO;
import com.inventorysystem.backoffice.dto.WarehouseDTO;
import java.util.List;

/**
 * Warehouse service interface.
 */
public interface WarehouseService {

    WarehouseDTO addWarehouse(WarehouseDTO warehouseDTO);

    List<WarehouseDTO> getWarehouse();

    WarehouseDTO getWarehouseById(int warehouseId);

    WarehouseDTO placeInventoryInWarehouse(InventoryDetailDTO inventoryDetailDTO, int warehouseId);

    List<ItemQuantityDTO> getItemQuantityInAllWarehouses();

    List<ItemQuantityDTO> getItemQuantityInSingleWarehouse(int warehouseId);
}
