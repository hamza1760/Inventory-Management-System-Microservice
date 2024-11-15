package com.inventorysystem.gateway.utilities;

import static com.inventorysystem.common.utilities.Constants.PREFERRED_USERNAME;
import static com.inventorysystem.common.utilities.Constants.TOKEN_ALGO;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.inventorysystem.common.utilities.Constants;
import com.inventorysystem.gateway.dto.AuthDetailDto;
import com.inventorysystem.gateway.externalservice.IUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IUserService userService;

    private Map<String, PublicKey> companyPubLicKeyMap = new HashMap<>();


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
        PublicKey publicKey = getPublicKey(token);
        log.debug("Public-Key: {}", publicKey);
        try {
            return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token);
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
    /**
     * Decode token.
     *
     * @param token Token to be decoded.
     * @return DecodedJWT.
     */
    public DecodedJWT getDecodedJWT(String token) {
        try {
            return JWT.decode(token);
        } catch (JWTDecodeException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad token is passed: " + token);
        }
    }

    @SneakyThrows
    private PublicKey getPublicKey(String token) {
        String username = getUsername(token);
        String realmName = Constants.REALM_NAME;

        if (!StringUtils.hasText(realmName)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Realm Name not found in token. Either realm name is not correctly configured in KeyCloak due to which its missing in token"
                    + " OR token is not valid for username " + username + " and token " + token);
        }
        if (companyPubLicKeyMap.containsKey(realmName)) {
            return companyPubLicKeyMap.get(realmName);
        }
        log.debug("No Public Key present for realm {}", realmName);
        AuthDetailDto authDetailDto = getAuthDetail(username);
        String rsaPublicKey = authDetailDto.getPublicKey();
        PublicKey publicKey;
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rsaPublicKey));
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(TOKEN_ALGO);
            publicKey = keyFactory.generatePublic(keySpec);
            companyPubLicKeyMap.put(realmName, publicKey);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InvalidKeySpecException("Error while verifying token signature: " + e.getLocalizedMessage());
        }
        return publicKey;
    }

    private String getUsername(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        log.info("Getting {} from token", PREFERRED_USERNAME);
        String preferredUsername = decodedJWT.getClaim(PREFERRED_USERNAME).asString();
        log.info("{}: {}", PREFERRED_USERNAME, preferredUsername);
        return preferredUsername;
    }
    private AuthDetailDto getAuthDetail(String username) {
        log.info("Getting public key for username {}", username);
        AuthDetailDto authDetail = AuthDetailDto.builder()
            .clientSecret(Constants.CLIENT_SECRET_VALUE)
            .publicKey(Constants.PUBLIC_KEY_VALUE)
            .realm(Constants.REALM_NAME).build();
        log.debug("AuthDetail: {}", authDetail);
        return authDetail;
    }

}
