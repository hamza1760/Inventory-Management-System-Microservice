package com.inventorysystem.backoffice.dto;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
public class WarehouseDTO {

    private int warehouseId;
    @NotEmpty
    private String warehouseName;
    private AddressDTO address;
    private Set<InventoryDetailDTO> inventory;

}
