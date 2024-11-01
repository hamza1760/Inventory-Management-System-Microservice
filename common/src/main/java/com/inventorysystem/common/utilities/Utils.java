package com.inventorysystem.common.utilities;

import static com.inventorysystem.common.utilities.Constants.USER_ID;

import com.inventorysystem.common.dto.AuthorityDto;
import com.inventorysystem.common.dto.RequestMetaData;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Utils {

    /**
     * Process KeyCloak Service Provider Realm's user attributes seperated by SPACE to get List of AuthorityDto.
     *
     * @param authoritiesDto KeyCloak Realm's user attributes seperated by SPACE.
     * @return List of AuthorityDto having userAttributes transformed from  KeyCloak Realm's user attributes seperated by SPACE.
     */
    public List<AuthorityDto> getAuthorities(String authoritiesDto) {
        List<AuthorityDto> authorities = new ArrayList<>();
        if (authoritiesDto != null && !authoritiesDto.isEmpty()) {
            String[] authoritiesArray = authoritiesDto.split(" ");
            for (String authority : authoritiesArray) {
                authorities.add(AuthorityDto.builder().authority(authority.toUpperCase()).build());
            }
        }
        return authorities;
    }

    public RequestMetaData getRequestDetail(HttpServletRequest request) {

        Long userId = request.getHeader(USER_ID) == null
            ? null : Long.parseLong(request.getHeader(USER_ID));

        return RequestMetaData.builder()
            .userId(userId)
            .username("")
            .build();
    }
}
