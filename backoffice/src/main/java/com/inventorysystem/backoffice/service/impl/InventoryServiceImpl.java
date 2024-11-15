package com.inventorysystem.backoffice.service.impl;


import com.inventorysystem.backoffice.dto.InventoryDetailDTO;
import com.inventorysystem.backoffice.dto.ItemDTO;
import com.inventorysystem.backoffice.dto.ItemTypeDTO;
import com.inventorysystem.backoffice.entity.InventoryDetail;
import com.inventorysystem.backoffice.entity.Item;
import com.inventorysystem.backoffice.entity.ItemType;
import com.inventorysystem.backoffice.repository.InventoryDetailRepository;
import com.inventorysystem.backoffice.repository.ItemRepository;
import com.inventorysystem.backoffice.repository.ItemTypeRepository;
import com.inventorysystem.backoffice.service.InventoryService;
import com.inventorysystem.common.customexception.BusinessException;
import com.inventorysystem.common.utilities.Constants;
import com.inventorysystem.common.utilities.ModelConverter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Inventory service interface implementation.
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryDetailRepository inventoryDetailRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemTypeRepository itemTypeRepository;

    /**
     * To add inventory in database.
     *
     * @param inventoryDetailDTO The object of the InventoryDetailDTO.
     * @return Inventory that is added to database.
     */
    @Override
    public InventoryDetailDTO addInventory(InventoryDetailDTO inventoryDetailDTO) {
        int itemId = inventoryDetailDTO.getItem().getItemId();
        Item item = itemRepository.findById(itemId)
            .orElseThrow(()->  new BusinessException(Constants.ITEM_NOT_FOUND));

        int itemTypeId = inventoryDetailDTO.getItemType().getItemTypeId();

        ItemType itemType = itemTypeRepository.findById(itemTypeId).orElseThrow(() -> {
            throw new BusinessException(Constants.ITEM_TYPE_NOT_FOUND);
        });
        inventoryDetailDTO.setItem(ModelConverter.entityToDto(item, ItemDTO.class));
        inventoryDetailDTO.setItemType(ModelConverter.entityToDto(itemType, ItemTypeDTO.class));
        InventoryDetail inventoryDetail = inventoryDetailRepository.save(ModelConverter.dtoToEntity(inventoryDetailDTO, InventoryDetail.class));
        return ModelConverter.entityToDto(inventoryDetail, InventoryDetailDTO.class);
    }

    /**
     * Get the list of inventories available in database.
     *
     * @return list of inventories.
     */
    @Override
    public List<InventoryDetailDTO> getInventory() {
        List<InventoryDetail> inventoryDetails = inventoryDetailRepository.findAll();
        return ModelConverter.listEntityToDto(inventoryDetails, InventoryDetailDTO.class);
    }

    /**
     * To get single inventory based on the id of inventory.
     *
     * @param id The id of the inventory to search inventory in database.
     * @return Single inventory that matches the inventoryId.
     */
    @Override
    public InventoryDetailDTO getInventoryById(int id) {
        InventoryDetail inventoryDetail = inventoryDetailRepository.findById(id)
            .orElseThrow(() -> new BusinessException(Constants.INVENTORY_NOT_FOUND));

        return ModelConverter.entityToDto(inventoryDetail, InventoryDetailDTO.class);
    }

    /**
     * Delete the particular inventory from database.
     *
     * @param id The id of the inventory to be deleted.
     */
    @Override
    public void deleteInventoryById(int id) {
        InventoryDetail inventoryDetail = inventoryDetailRepository.findById(id)
            .orElseThrow(() -> new BusinessException(Constants.INVENTORY_NOT_FOUND));

        inventoryDetailRepository.delete(inventoryDetail);
    }
}
