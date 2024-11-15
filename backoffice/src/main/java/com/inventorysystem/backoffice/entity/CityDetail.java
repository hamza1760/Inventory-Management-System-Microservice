package com.inventorysystem.backoffice.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityDetail {

    @Id
    private int cityId;
    private String cityCode;
    private String cityName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id_fk")
    private CountryDetail country;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "city")
    private final Set<Address> address = new HashSet<>();

}
