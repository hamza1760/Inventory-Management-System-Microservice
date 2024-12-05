package com.inventorysystem.backoffice.dto;

import com.inventorysystem.common.enums.ItemSize;
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
    private ItemSize itemSize;
    private String itemName;
    private String itemType;
    private String productType;
    private String brandName;

}
