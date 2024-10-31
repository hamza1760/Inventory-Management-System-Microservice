package com.inventorysystem.user.dto;

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
    @NotEmpty
    private String email;
    @NotEmpty
    private String username;
    @NotBlank
    private String password;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private long phone;
    @JsonIgnore
    private String status = Constants.ACTIVE;
    private RoleDTO role;

}
