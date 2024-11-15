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
public class ProductTypeDTO {

    private int productTypeId;
    private String productType;
    @JsonIgnore
    private ItemDTO item;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTypeDTO that = (ProductTypeDTO) o;
        return productTypeId == that.productTypeId && Objects.equals(productType, that.productType) && Objects.equals(item, that.item);
    }
}
