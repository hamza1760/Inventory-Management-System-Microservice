package com.inventorysystem.common.enums;

import java.util.Arrays;

public enum RolesEnum {

    Admin("Admin");

    private String role;

    RolesEnum(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
    public static boolean isExists(String name) {
        return Arrays.stream(RolesEnum.values())
            .anyMatch(userType -> userType.getRole().equals(name));
    }
}
