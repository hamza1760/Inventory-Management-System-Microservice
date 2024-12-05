package com.inventorysystem.backoffice.repository;

import com.inventorysystem.backoffice.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of the product type to perform database operation.
 */
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {

}
