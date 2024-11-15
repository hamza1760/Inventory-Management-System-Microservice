package com.inventorysystem.backoffice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
public class ProductType {

    @Id
    private int productTypeId;
    private String productType;
    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "productType")
    private Item item;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductType that = (ProductType) o;
        return productTypeId == that.productTypeId && Objects.equals(productType, that.productType) && Objects.equals(item, that.item);
    }
}
