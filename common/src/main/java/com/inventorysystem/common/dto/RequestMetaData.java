package com.inventorysystem.common.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestMetaData implements Serializable {

    private Long userId;
    private String username;
    private Long companyId;
    private Boolean isCompanySuperAdmin;
    private String subDomain;
    private String userType;
    private List<AuthorityDto> spUserRoles;
    private String userRole;
}
