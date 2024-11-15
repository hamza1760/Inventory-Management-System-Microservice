package com.inventorysystem.backoffice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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
public class ItemTypeDTO {

    private int itemTypeId;
    private String itemType;
    @JsonIgnore
    private final Set<InventoryDetailDTO> inventory = new HashSet<>();
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemTypeDTO that = (ItemTypeDTO) o;
        return itemTypeId == that.itemTypeId && Objects.equals(itemType, that.itemType) && Objects.equals(inventory, that.inventory);
    }
}
