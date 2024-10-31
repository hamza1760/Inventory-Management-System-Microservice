package com.inventorysystem.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventorysystem.common.enums.RolesEnum;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private int roleId;
    private RolesEnum roleName;
    @JsonIgnore
    private Set<UserDTO> user;


}
