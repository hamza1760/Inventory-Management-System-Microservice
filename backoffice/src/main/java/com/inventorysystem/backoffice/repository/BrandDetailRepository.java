package com.inventorysystem.backoffice.repository;

import com.inventorysystem.backoffice.entity.BrandDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of the brand detail to perform database operation.
 */
public interface BrandDetailRepository extends JpaRepository<BrandDetail, Integer> {

}
