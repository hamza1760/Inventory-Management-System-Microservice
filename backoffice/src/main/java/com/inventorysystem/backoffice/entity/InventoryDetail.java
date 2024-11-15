package com.inventorysystem.backoffice.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Proxy(lazy = false)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
public class InventoryDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int inventoryId;
    private String itemSize;
    private int inStock;
    private int avlQty;
    private int inTransit;
    private int minOrderQuantity;
    private int quantityPerBox;
    private int reorderPoint;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "itemTypeId")
    private ItemType itemType;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

}
