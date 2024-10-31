package com.inventorysystem.gateway.utilities;

import com.inventorysystem.common.utilities.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Component
public class GatewayUtil {

    private final String SECRET_KEY = "inventorySystem";


    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    public Claims extractAllClaims(Jws<Claims> claimsJws) {
        return claimsJws.getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Jws<Claims> checkAuthenticity(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
        } catch (ExpiredJwtException exception) {
            log.error("Token Expire Exception: {}", exception.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "for username" + extractEmail(token)
                + " expired token is passed. Token: " + token, exception);
        } catch (SignatureException exception) {
            log.error("for username" + extractEmail(token) + " either public key is not configured correctly for token.realmName "
                + "OR token is signed with some other key which is not valid as per the public key for token.realmName");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "for username" + extractEmail(token)
                + " invalid signed token is passed. Token: " + token, exception);
        } catch (UnsupportedJwtException | MalformedJwtException exception) {
            log.error("Unsupported Jwt Exception: {}", exception.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "for username" + extractEmail(token)
                + " bad token is passed. Token: " + token, exception);
        } catch (Exception exception) {
            log.error("Exception While Checking Token Authenticity: {}", exception.getLocalizedMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "for username" + extractEmail(token)
                + " bad token is passed and unexpected exception has occurred. Token: " + token, exception);
        }
    }

    public String getAuthorizationToken(ServerWebExchange exchange) {
        return getAuthorizationToken(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    public String getAuthorizationToken(String authorizationHeaderValue) {
        if (!isBearerTokenValid(authorizationHeaderValue)) {
            log.error("Token is not valid, either its null OR empty OR only contain whitespace OR not having valid format as {Bearer + SPACE + token}");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid format of token: " + authorizationHeaderValue);
        }
        return authorizationHeaderValue.replace(Constants.AUTHORIZATION_TOKEN_PREFIX, "");
    }

    private boolean isBearerTokenValid(String authorizationHeaderValue) {
        return StringUtils.hasText(authorizationHeaderValue)
            && authorizationHeaderValue.startsWith(Constants.AUTHORIZATION_TOKEN_PREFIX);
    }

}
