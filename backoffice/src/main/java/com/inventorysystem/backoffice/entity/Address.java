package com.inventorysystem.backoffice.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Address{

    @Id
    private int addressId;
    private long postalCode;
    private String areaName;
    private String street;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id_fk")
    private CityDetail city;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "address")
    private Warehouse warehouse;


}
