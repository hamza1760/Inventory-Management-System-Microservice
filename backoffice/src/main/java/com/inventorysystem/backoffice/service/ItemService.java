package com.inventorysystem.backoffice.service;

import com.inventorysystem.backoffice.dto.ItemDTO;
import com.inventorysystem.backoffice.dto.ItemSizeDTO;
import java.util.List;

/**
 * Item service inteface.
 */
public interface ItemService {

    List<ItemDTO> getItem();

    ItemDTO addItem(ItemDTO itemDTO);

    ItemDTO getItemById(int itemId);

    List<ItemSizeDTO> getAllItemSize();

    List<ItemSizeDTO> getItemSizeById(int itemId);

    void deleteItemById(int itemId);
}
