package com.inventorysystem.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDetailDto implements Serializable {

    private String realm;
    private String clientSecret;
    private String publicKey;
}
