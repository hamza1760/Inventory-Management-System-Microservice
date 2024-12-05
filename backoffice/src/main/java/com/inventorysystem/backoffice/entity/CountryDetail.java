package com.inventorysystem.backoffice.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryDetail {

    @Id
    private int id;
    private String countryCode;
    private String countryName;

}
