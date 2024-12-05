package com.inventorysystem.backoffice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventorysystem.common.enums.ItemSize;
import com.inventorysystem.common.utilities.Constants;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDetailDTO {

    private int inventoryId;
    private ItemSize itemSize;
    private int inStock;
    private int avlQty;
    private int inTransit;
    private int minOrderQuantity;
    private int quantityPerBox;
    private int reorderPoint;
    private ItemDTO item;
    private ItemTypeDTO itemType;
    private int warehouseId;
}
