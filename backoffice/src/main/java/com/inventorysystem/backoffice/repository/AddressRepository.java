package com.inventorysystem.backoffice.repository;

import com.inventorysystem.backoffice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository of the address to perform database operation.
 */
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
