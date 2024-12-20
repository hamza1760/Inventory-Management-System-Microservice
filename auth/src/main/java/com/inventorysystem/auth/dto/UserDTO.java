package com.inventorysystem.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventorysystem.common.utilities.Constants;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private int id;
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private long phone;
    private String status = Constants.ACTIVE;
    private RoleDTO role;

}
