package com.inventorysystem.backoffice.repository;

import com.inventorysystem.backoffice.entity.CityDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of the city detail to perform database operation.
 */
public interface CityDetailRepository extends JpaRepository<CityDetail, Integer> {

}
