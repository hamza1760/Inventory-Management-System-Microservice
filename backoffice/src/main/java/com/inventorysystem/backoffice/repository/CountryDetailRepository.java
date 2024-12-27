package com.inventorysystem.backoffice.repository;

import com.inventorysystem.backoffice.entity.CountryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of the country detail to perform database operation.
 */
public interface CountryDetailRepository extends JpaRepository<CountryDetail, Integer> {

}


