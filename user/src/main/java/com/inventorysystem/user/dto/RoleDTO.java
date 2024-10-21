package com.inventorysystem.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventorysystem.user.entity.User;
import java.util.Set;

public class RoleDTO {

    private int roleId;
    private String roleName;
    @JsonIgnore
    private Set<User> user;

    public RoleDTO() {
    }

    public RoleDTO(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }
}
