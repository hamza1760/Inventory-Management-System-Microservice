package com.inventorysystem.backoffice.repository;

import com.inventorysystem.backoffice.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of the item type to perform database operation.
 */
public interface ItemTypeRepository extends JpaRepository<ItemType, Integer> {

}
