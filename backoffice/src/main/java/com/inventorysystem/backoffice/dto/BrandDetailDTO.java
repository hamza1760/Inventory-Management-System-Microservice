package com.inventorysystem.backoffice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class BrandDetailDTO {

    private int brandId;
    private String brandName;
    @JsonIgnore
    private ItemDTO item;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandDetailDTO that = (BrandDetailDTO) o;
        return brandId == that.brandId && Objects.equals(brandName, that.brandName) && Objects.equals(item, that.item);
    }
}
