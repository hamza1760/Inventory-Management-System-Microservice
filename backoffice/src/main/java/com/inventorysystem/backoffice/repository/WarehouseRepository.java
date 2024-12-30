package com.inventorysystem.backoffice.repository;

import com.inventorysystem.backoffice.dto.ItemQuantityDTO;
import com.inventorysystem.backoffice.entity.Warehouse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository of the warehouse to perform database operation.
 */
@Transactional
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    /**
     * Get the quantity of all items in particular warehouse present in database with given warehouseId and status active.
     *
     * @param warehouseId The id of warehouse to search warehouse in database.
     * @return List of ItemQuantity present in particular warehouse.
     */
    @Query("Select new com.inventorysystem.backoffice.dto.ItemQuantityDTO(A.id,A.warehouseName,B.areaName," +
            "C.cityName,D.countryName,E.inventoryId,E.itemSize,E.inStock,E.avlQty,F.itemName,F.id,G.itemType,H.productType,I.brandName) " +
            "From Warehouse A " +
            "Join A.address B " +
            "Join B.city C " +
            "Join C.country D " +
            "Join A.inventory E " +
            "Join E.item F " +
            "Join E.itemType G " +
            "Join F.productType H " +
            "Join F.brand I " +
            "Where A.id =?1")
    List<ItemQuantityDTO> getItemQuantityInSingleWarehouse(int warehouseId);

    /**
     * Get the quantity of all items in all warehouses present in database with status active.
     *
     * @return List of ItemQuantity present in all warehouses.
     */
    @Query("Select new com.inventorysystem.backoffice.dto.ItemQuantityDTO(A.id,A.warehouseName,B.areaName," +
            "C.cityName,D.countryName,E.inventoryId,E.itemSize,E.inStock,E.avlQty,F.itemName,F.id,G.itemType,H.productType,I.brandName) " +
            "From Warehouse A " +
            "Join A.address B " +
            "Join B.city C " +
            "Join C.country D " +
            "Join A.inventory E " +
            "Join E.item F " +
            "Join E.itemType G " +
            "Join F.productType H " +
            "Join F.brand I ")
    List<ItemQuantityDTO> getItemQuantityAllWarehouses();


    /**
     * Get the warehouse from database by its name.
     *
     * @param warehouseName The name of the warehouse search warehouse in database.
     * @return Warehouse if warehouse name is present in database and status is active.
     */
    Warehouse findByWarehouseName(String warehouseName);
}
