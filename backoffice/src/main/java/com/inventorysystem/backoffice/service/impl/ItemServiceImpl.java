package com.inventorysystem.backoffice.service.impl;


import com.inventorysystem.backoffice.dto.ItemDTO;
import com.inventorysystem.backoffice.dto.ItemSizeDTO;
import com.inventorysystem.backoffice.entity.BrandDetail;
import com.inventorysystem.backoffice.entity.InventoryDetail;
import com.inventorysystem.backoffice.entity.Item;
import com.inventorysystem.backoffice.entity.ProductType;
import com.inventorysystem.backoffice.repository.BrandDetailRepository;
import com.inventorysystem.backoffice.repository.InventoryDetailRepository;
import com.inventorysystem.backoffice.repository.ItemRepository;
import com.inventorysystem.backoffice.repository.ProductTypeRepository;
import com.inventorysystem.backoffice.service.ItemService;
import com.inventorysystem.common.customexception.BusinessException;
import com.inventorysystem.common.customexception.Exception;
import com.inventorysystem.common.utilities.Constants;
import com.inventorysystem.common.utilities.ModelConverter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Item service interface implementation.
 */
@Service
@Slf4j
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BrandDetailRepository brandDetailRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private InventoryDetailRepository inventoryDetailRepository;

    /**
     * To add item in database.
     *
     * @param itemDTO Object of ItemDTO.
     * @return The item that is added in database.
     */
    public ItemDTO addItem(ItemDTO itemDTO) {
        int productTypeId = itemDTO.getProductType().getProductTypeId();
        productTypeRepository.findById(productTypeId).orElseThrow(() -> {
            throw new BusinessException("Product Type Not Found");
        });
        int brandId = itemDTO.getBrand().getBrandId();
        brandDetailRepository.findById(brandId).orElseThrow(() -> {
            throw new BusinessException("Brand detail Not Found ");
        });
        String itemName = itemDTO.getItemName();
        Item item = itemRepository.findByItemName(itemName);
        if (item != null) {
            throw new BusinessException("Item already present");
        }
            log.info("Saving item in database with itemName: " + itemName + " productTypeId: " + productTypeId + " brandId: " + brandId);
            item = itemRepository.save(ModelConverter.dtoToEntity(itemDTO,Item.class));
            return ModelConverter.entityToDto(item, ItemDTO.class);
    }

    /**
     * Get the list of items available in database.
     *
     * @return list of items.
     */
    @Override
    public List<ItemDTO> getItem() {
        log.info("Getting list of item from database");
        List<Item> items = itemRepository.findAll();
        return ModelConverter.listEntityToDto(items, ItemDTO.class);
    }

    /**
     * To get single item based on the id of item.
     *
     * @param itemId The id of the item to search item in database.
     * @return Single item that matches the itemId.
     */
    @Override
    public ItemDTO getItemById(int itemId) {
        log.info("Start - getItemById in itemServiceImpl");
        Item item = itemRepository.findById(itemId)
            .orElseThrow(()-> new BusinessException("Item not found"));
        log.info("End - getItemById in itemServiceImpl");
        return ModelConverter.entityToDto(item, ItemDTO.class);
    }

    /**
     * To get the size of all items available in database.
     *
     * @return The list of sizes of all items.
     */
    @Override
    public List<ItemSizeDTO> getAllItemSize() {
        log.info("Start - getAllItemSize in itemServiceImpl");
        List<ItemSizeDTO> itemSizeDTOs = itemRepository.getAllItemSize();
        log.info("End - getAllItemSize in itemServiceImpl");
        return itemSizeDTOs;
    }

    /**
     * To get the size of the single item.
     *
     * @param itemId The id of the item to search item in database.
     * @return The list of all sizes of the particular item.
     */
    @Override
    public List<ItemSizeDTO> getItemSizeById(int itemId) {
        log.info("Start - getAllItemSizeById in itemServiceImpl");
        getItemById(itemId);
        log.info("Getting size of the items from database");
        List<ItemSizeDTO> itemSizeDTOs = itemRepository.getItemSizeById(itemId);
        log.info("End - getAllItemSizeById in itemServiceImpl");
        return itemSizeDTOs;
    }

    /**
     * Delete the particular item from the database.
     *
     * @param itemId The id of the item to be deleted.
     */
    @Override
    public void deleteItemById(int itemId) {
        log.info("Start - deleteItemById in itemServiceImpl");
        ItemDTO itemDTO = getItemById(itemId);
        Item item = ModelConverter.entityToDto(itemDTO, Item.class);
        Set<InventoryDetail> inventoryDetail = item.getInventory();
        for (InventoryDetail inventory : inventoryDetail) {
            inventory.setDeleted(true);
            inventoryDetailRepository.save(inventory);
        }
        log.info("End - deleteItemById in itemServiceImpl");
        itemRepository.delete(item);
    }
}
