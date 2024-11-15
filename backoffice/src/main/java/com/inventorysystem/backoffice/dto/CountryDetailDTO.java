package com.inventorysystem.backoffice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
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
public class CountryDetailDTO {

    @JsonIgnore
    private final Set<CityDetailDTO> cityDetailDTOS = new HashSet<>();
    private int countryId;
    private String countryCode;
    private String countryName;

}
