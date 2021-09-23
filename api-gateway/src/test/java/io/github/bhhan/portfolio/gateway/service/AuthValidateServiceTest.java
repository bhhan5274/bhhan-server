package io.github.bhhan.portfolio.gateway.service;

import io.github.bhhan.portfolio.gateway.common.OAuth2Properties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AuthValidateServiceTest {
    private static final int EXPIRE_MILLISECONDS_SUCCESS = 20000;
    private static final int EXPIRE_MILLISECONDS_FAIL = 0;
    private static final String BASIC_AUTH = "basicAuth";
    private static final String key = "Hello World";

    private AuthValidateService authValidateService;
    private AuthValidateService spyAuthValidatorService;

    @BeforeEach
    void init(){
        final OAuth2Properties oAuth2Properties = new OAuth2Properties();
        oAuth2Properties.setJwtSigningKey(key);
        authValidateService = new AuthValidateService(new RestTemplate(), oAuth2Properties);
        spyAuthValidatorService = Mockito.spy(authValidateService);
    }

    @Test
    @DisplayName("getAccessToken")
    void getAccessToken(){
        final String token = createToken(EXPIRE_MILLISECONDS_SUCCESS);

        Mockito.doReturn(token)
                .when(spyAuthValidatorService)
                .getAccessTokenFromOAuthServer(BASIC_AUTH);

        final String accessToken = spyAuthValidatorService.getAccessToken(BASIC_AUTH);

        verify(spyAuthValidatorService, times(0)).verifyJWT(token);
        verify(spyAuthValidatorService, times(1)).getAccessTokenFromOAuthServer(BASIC_AUTH);
        assertEquals(token, accessToken);
    }

    @Test
    @DisplayName("getAccessToken - fourth")
    void getAccessTokenTwice(){
        final String token = createToken(EXPIRE_MILLISECONDS_SUCCESS);

        Mockito.doReturn(token)
                .when(spyAuthValidatorService)
                .getAccessTokenFromOAuthServer(BASIC_AUTH);

        final String accessToken1 = spyAuthValidatorService.getAccessToken(BASIC_AUTH);
        final String accessToken2 = spyAuthValidatorService.getAccessToken(BASIC_AUTH);
        final String accessToken3 = spyAuthValidatorService.getAccessToken(BASIC_AUTH);
        final String accessToken4 = spyAuthValidatorService.getAccessToken(BASIC_AUTH);

        verify(spyAuthValidatorService, times(3)).verifyJWT(token);
        verify(spyAuthValidatorService, times(1)).getAccessTokenFromOAuthServer(BASIC_AUTH);
        assertEquals(token, accessToken1);
        assertEquals(token, accessToken2);
        assertEquals(token, accessToken3);
        assertEquals(token, accessToken4);
    }

    @Test
    @DisplayName("getAccessToken - invalid")
    void getAccessTokenInvalid(){
        final String token = createToken(EXPIRE_MILLISECONDS_FAIL);

        Mockito.doReturn(token)
                .when(spyAuthValidatorService)
                .getAccessTokenFromOAuthServer(BASIC_AUTH);

        final String accessToken = spyAuthValidatorService.getAccessToken(BASIC_AUTH);
        spyAuthValidatorService.getAccessToken(BASIC_AUTH);

        verify(spyAuthValidatorService, times(1)).verifyJWT(token);
        verify(spyAuthValidatorService, times(2)).getAccessTokenFromOAuthServer(BASIC_AUTH);
        assertEquals(token, accessToken);
    }

    @Test
    @DisplayName("verifyJWT")
    void verifyJWT(){
        assertFalse(authValidateService.verifyJWT(createToken(EXPIRE_MILLISECONDS_FAIL)));
        assertTrue(authValidateService.verifyJWT(createToken(EXPIRE_MILLISECONDS_SUCCESS)));
    }

    @Test
    @DisplayName("encodingBase64 > decodingBase64")
    void base64Test(){
        final String username = "bhhan";
        final String password = "password";

        final String base64Token = AuthValidateService.encodingBase64(username, password);

        final String[] results = AuthValidateService.decodeBase64(base64Token);

        assertEquals(username, results[0]);
        assertEquals(password, results[1]);
    }

    private String createToken(int expireMilliseconds){
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("data", "JWT Payload Data");

        return Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setSubject("user")
                .setExpiration(new Date(System.currentTimeMillis() + expireMilliseconds))
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();
    }
}