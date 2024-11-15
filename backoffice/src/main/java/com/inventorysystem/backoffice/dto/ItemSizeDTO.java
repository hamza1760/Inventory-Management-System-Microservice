package com.inventorysystem.backoffice.dto;

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
public class ItemSizeDTO {

    private int inventoryId;
    private int itemId;
    private String itemSize;
    private String itemName;
    private String itemType;
    private String productType;
    private String brandName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemSizeDTO that = (ItemSizeDTO) o;
        return inventoryId == that.inventoryId && itemId == that.itemId && Objects.equals(itemSize, that.itemSize) && Objects.equals(itemName, that.itemName) && Objects.equals(itemType, that.itemType) && Objects.equals(productType, that.productType) && Objects.equals(brandName, that.brandName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryId, itemId, itemSize, itemName, itemType, productType, brandName);
    }
}
