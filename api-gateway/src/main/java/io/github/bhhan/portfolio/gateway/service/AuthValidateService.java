package io.github.bhhan.portfolio.gateway.service;

import io.github.bhhan.portfolio.gateway.common.OAuth2Properties;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthValidateService {
    private final RestTemplate restTemplate;
    private final OAuth2Properties oAuth2Properties;
    private Map<String, String> authMap = new HashMap<>();

    public String getAccessToken(String basicAuth) {
        if(authMap.containsKey(basicAuth)){
            final String accessToken = authMap.get(basicAuth);

            if(verifyJWT(accessToken)){
                return accessToken;
            }
        }

        final String accessTokenFromOAuthServer = getAccessTokenFromOAuthServer(basicAuth);
        authMap.put(basicAuth, accessTokenFromOAuthServer);

        return accessTokenFromOAuthServer;
    }

    String getAccessTokenFromOAuthServer(String basicAuth) {
        HttpHeaders headers = new HttpHeaders();
        final String[] usernameAndPassword = AuthValidateService.decodeBase64(basicAuth);
        headers.add(HttpHeaders.AUTHORIZATION, String.format("%s %s", "Basic", AuthValidateService.encodingBase64(usernameAndPassword[0], usernameAndPassword[1])));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("username", oAuth2Properties.getUsername());
        map.add("password", oAuth2Properties.getPassword());
        map.add("grant_type", oAuth2Properties.getGrantType());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        final ResponseEntity<OAuthToken> oAuthTokenResponseEntity = restTemplate.postForEntity(String.format("%s%s", oAuth2Properties.getUrl(), "/oauth/token"), request, OAuthToken.class);
        return oAuthTokenResponseEntity.getBody().getAccess_token();
    }

    public static String[] decodeBase64(String base64Token) {
        byte[] decoded;

        try {
            decoded = Base64.decodeBase64(base64Token.getBytes());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, StandardCharsets.UTF_8);
        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new IllegalArgumentException("Invalid basic authentication token");
        } else {
            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
        }
    }

    public static String encodingBase64(String arg1, String arg2){
        try {
            return new String(Base64.encodeBase64(String.format("%s:%s", arg1, arg2).getBytes()));
        }catch(Exception e){
            throw new IllegalArgumentException("encodingBase64: Invalid argument");
        }
    }

    boolean verifyJWT(String jwt){
        try{
            Jwts.parser()
                    .setSigningKey(oAuth2Properties.getJwtSigningKey().getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return false;
        }

        return true;
    }

    @Getter
    @Setter
    static class OAuthToken {
        private String access_token;
        private String token_type;
        private long expires_in;
        private String scope;

        @Override
        public String toString() {
            return "OAuthToken{" +
                    "access_token='" + access_token + '\'' +
                    ", token_type='" + token_type + '\'' +
                    ", expires_in=" + expires_in +
                    ", scope='" + scope + '\'' +
                    '}';
        }
    }
}
