package com.inventorysystem.gateway.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDetailDto implements Serializable {

    private String realm;
    private String clientSecret;
    private String publicKey;
}
