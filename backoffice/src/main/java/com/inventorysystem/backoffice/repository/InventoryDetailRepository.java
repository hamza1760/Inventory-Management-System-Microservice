package com.inventorysystem.backoffice.repository;

import com.inventorysystem.backoffice.entity.InventoryDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository of the inventory detail to perform database operation.
 */
@Transactional
public interface InventoryDetailRepository extends JpaRepository<InventoryDetail, Integer> {


}
