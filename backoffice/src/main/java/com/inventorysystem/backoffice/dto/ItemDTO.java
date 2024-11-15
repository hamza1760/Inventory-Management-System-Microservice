package com.inventorysystem.backoffice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventorysystem.common.utilities.Constants;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
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
public class ItemDTO {

    private int itemId;
    @NotEmpty
    private String itemName;
    private ProductTypeDTO productType;
    private BrandDetailDTO brand;
    @JsonIgnore
    private Set<InventoryDetailDTO> inventory = new HashSet<>();

}


