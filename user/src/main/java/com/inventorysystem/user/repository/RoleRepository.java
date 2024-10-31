package com.inventorysystem.user.repository;

import com.inventorysystem.common.enums.RolesEnum;
import com.inventorysystem.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRoleName(RolesEnum rolesEnum);

}
