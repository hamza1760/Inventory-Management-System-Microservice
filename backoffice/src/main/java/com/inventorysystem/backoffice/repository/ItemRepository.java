package com.inventorysystem.backoffice.repository;

import com.inventorysystem.backoffice.dto.ItemSizeDTO;
import com.inventorysystem.backoffice.entity.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository of the item to perform database operation.
 */
@Transactional
public interface ItemRepository extends JpaRepository<Item, Integer> {

    /**
     * Get the size of particular item present in database with given itemId
     *
     * @param itemId The id of item to search item in database.
     * @return List of ItemSize of particular item.
     */
    @Query("Select new com.inventorysystem.backoffice.dto.ItemSizeDTO(A.inventoryId,B.itemId,A.itemSize,B.itemName,C.itemType,D.productType,E.brandName) " +
        "FROM InventoryDetail A " +
        "JOIN A.item B " +
        "JOIN A.itemType C " +
        "JOIN B.productType D " +
        "JOIN B.brand E " +
        "where B.itemId =?1")
    List<ItemSizeDTO> getItemSizeById(int itemId);

    /**
     * Get the size of all items present in database
     *
     * @return List of ItemSize of all items.
     */
    @Query("Select new com.inventorysystem.backoffice.dto.ItemSizeDTO(A.inventoryId,B.itemId,A.itemSize,B.itemName,C.itemType,D.productType,E.brandName) " +
        "FROM InventoryDetail A " +
        "JOIN A.item B " +
        "JOIN A.itemType C " +
        "JOIN B.productType D " +
        "JOIN B.brand E ")
    List<ItemSizeDTO> getAllItemSize();


    /**
     * Get the item from database by its name
     *
     * @param itemName The name of the item search item in database.
     * @return Item if item name is present in database
     */
    Item findByItemName(String itemName);
}
