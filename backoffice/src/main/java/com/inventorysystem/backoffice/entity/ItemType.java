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
    private int id;
    @NotEmpty
    private String itemType;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "itemType")
    private final Set<InventoryDetail> inventory = new HashSet<>();
}
