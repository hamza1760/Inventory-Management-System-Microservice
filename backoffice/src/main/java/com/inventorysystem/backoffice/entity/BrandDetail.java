package com.inventorysystem.backoffice.entity;

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
public class BrandDetail {

    @Id
    private int brandId;
    private String brandName;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "brand")
    private Item item;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandDetail that = (BrandDetail) o;
        return brandId == that.brandId && Objects.equals(brandName, that.brandName) && Objects.equals(item, that.item);
    }
}
