package io.github.bhhan.portfolio.dashboard.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OAuthPropertiesTest {
    private static final String BASIC_AUTH_TOKEN = "YmhoYW46YmhoYW5fcGFzc3dvcmQ=";

    @Test
    @DisplayName("getBasicAuthToken")
    void getBasicAuthToken(){
        final OAuthProperties oAuthProperties = new OAuthProperties();
        oAuthProperties.setUsername("bhhan");
        oAuthProperties.setPassword("bhhan_password");

        assertEquals(BASIC_AUTH_TOKEN, oAuthProperties.getBasicAuthToken());
    }
}