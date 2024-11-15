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
public class CityDetailDTO {

    private int cityId;
    private String cityCode;
    private String cityName;
    private CountryDetailDTO country;
    @JsonIgnore
    private Set<AddressDTO> address = new HashSet<>();


}
