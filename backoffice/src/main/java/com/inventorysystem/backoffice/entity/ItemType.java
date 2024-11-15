package com.inventorysystem.backoffice.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemType {

    @Id
    private int itemTypeId;
    @NotEmpty
    private String itemType;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "itemType")
    private final Set<InventoryDetail> inventory = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemType itemType1 = (ItemType) o;
        return itemTypeId == itemType1.itemTypeId && Objects.equals(itemType, itemType1.itemType) && Objects.equals(inventory, itemType1.inventory);
    }
}
