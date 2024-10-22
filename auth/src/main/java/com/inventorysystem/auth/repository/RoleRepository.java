package com.inventorysystem.auth.repository;

import com.inventorysystem.auth.entity.Role;
import com.inventorysystem.common.enums.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRoleName(RolesEnum rolesEnum);

}
