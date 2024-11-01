package com.inventorysystem.auth.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inventorysystem.common.dto.AuthorityDto;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class LoginResponse implements Serializable {

    private String token;
    private String refreshToken;
    private Long expiresIn;
    private Long refreshExpiresIn;
    private int id;
    private Integer companyId;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date exp;
    private Boolean firstTimeLogin;
    private Boolean isCompanySuperAdmin;
    private String email;
    private String userType;
    private String userRole;
    private List<AuthorityDto> authorities;
}
