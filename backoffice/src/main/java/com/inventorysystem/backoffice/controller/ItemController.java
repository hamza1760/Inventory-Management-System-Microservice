package com.inventorysystem.backoffice.controller;

import static com.inventorysystem.common.utilities.ApiEndPointConstants.API;
import static com.inventorysystem.common.utilities.ApiEndPointConstants.ITEM;
import com.inventorysystem.backoffice.dto.ItemDTO;
import com.inventorysystem.backoffice.dto.ItemSizeDTO;
import com.inventorysystem.backoffice.service.ItemService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Item Controller.
 */
@RestController
@CrossOrigin("*")
@RequestMapping(API + ITEM)
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * To add item in database.
     *
     * @param itemDTO Object of ItemDTO.
     * @return The item that is added in database.
     */
    @PostMapping()
    public ItemDTO addItem(@Valid @RequestBody ItemDTO itemDTO) {
         return itemService.addItem(itemDTO);
    }

    /**
     * Get the list of items available in database.
     *
     * @return list of items.
     */
    @GetMapping()
    public List<ItemDTO> getItem() {
        return itemService.getItem();
    }

    /**
     * To get single item based on the id of item.
     *
     * @param itemId The id of the item to search item in database.
     * @return Single item that matches the itemId.
     */
    @GetMapping("/{itemId}")
    public ResponseEntity<?> getItemById(@PathVariable int itemId) {
        ItemDTO item = itemService.getItemById(itemId);
        return new ResponseEntity<>(item, HttpStatus.FOUND);
    }

    /**
     * To get the size of all items available in database.
     *
     * @return The list of sizes of all items.
     */
    @GetMapping("/size")
    public List<ItemSizeDTO> getAllItemSize() {
        return itemService.getAllItemSize();
    }

    /**
     * To get the size of the single item.
     *
     * @param itemId The id of the item to search item in database.
     * @return The list of all sizes of the particular item.
     */
    @GetMapping("/size/{itemId}")
    public List<ItemSizeDTO> getItemSizeById(@PathVariable int itemId) {
         return itemService.getItemSizeById(itemId);
    }

    /**
     * Delete the particular item from the database.
     *
     * @param itemId The id of the item to be deleted.
     */
    @DeleteMapping("/{itemId}")
    public void deleteItemById(@PathVariable int itemId) {
         itemService.deleteItemById(itemId);
    }
}