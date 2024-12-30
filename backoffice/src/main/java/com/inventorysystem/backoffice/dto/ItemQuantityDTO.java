package com.inventorysystem.backoffice.dto;

import com.inventorysystem.common.enums.ItemSize;
import lombok.Data;

@Data
public class ItemQuantityDTO {

    private int warehouseId;
    private String warehouseName;
    private String areaName;
    private String cityName;
    private String countryName;
    private int inventoryId;
    private ItemSize itemSize;
    private int inStock;
    private int avlQty;
    private String itemName;
    private int itemId;
    private String itemType;
    private String productType;
    private String brandName;

    public ItemQuantityDTO() {
    }

    public ItemQuantityDTO(int warehouseId, String warehouseName, String areaName, String cityName, String countryName, int inventoryId, ItemSize itemSize, int inStock, int avlQty, String itemName, int itemId, String itemType, String productType, String brandName) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.areaName = areaName;
        this.cityName = cityName;
        this.countryName = countryName;
        this.inventoryId = inventoryId;
        this.itemSize = itemSize;
        this.inStock = inStock;
        this.avlQty = avlQty;
        this.itemName = itemName;
        this.itemId = itemId;
        this.itemType = itemType;
        this.productType = productType;
        this.brandName = brandName;
    }


}
