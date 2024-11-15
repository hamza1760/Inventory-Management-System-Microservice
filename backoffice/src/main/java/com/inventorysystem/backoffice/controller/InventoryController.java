package com.inventorysystem.backoffice.controller;

import static com.inventorysystem.common.utilities.ApiEndPointConstants.API;
import static com.inventorysystem.common.utilities.ApiEndPointConstants.INVENTORY;

import com.inventorysystem.backoffice.dto.InventoryDetailDTO;
import com.inventorysystem.backoffice.service.InventoryService;
import com.inventorysystem.backoffice.service.ItemService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Inventory detail Controller.
 */
@RequestMapping(API + INVENTORY)
@RestController
@CrossOrigin(originPatterns = "*", maxAge = 3600)
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

//    @Autowired
//    private ItemService itemService;

    /**
     * To add inventory in database.
     *
     * @param inventoryDetailDTO The object of the InventoryDetailDTO.
     * @return Inventory that is added to database.
     */
    @PostMapping()
    public InventoryDetailDTO addInventory(@Valid @RequestBody InventoryDetailDTO inventoryDetailDTO) {
        return inventoryService.addInventory(inventoryDetailDTO);
    }

    /**
     * Get the list of inventories available in database.
     *
     * @return list of inventories.
     */
    @GetMapping()
    public List<InventoryDetailDTO> getInventory() {
        return inventoryService.getInventory();
    }

    /**
     * To get single inventory based on the id of inventory.
     *
     * @param id The id of the inventory to search inventory in database.
     * @return Single inventory that matches the inventoryId.
     */
    @GetMapping("/{id}")
    public InventoryDetailDTO getInventoryById(@PathVariable int id) {
        return inventoryService.getInventoryById(id);
    }

    /**
     * Delete the particular inventory from database.
     *
     * @param id The id of the inventory to be deleted.
     */
    @DeleteMapping("/{id}")
    public void deleteInventoryById(@PathVariable int id) {
        inventoryService.deleteInventoryById(id);
    }
}
