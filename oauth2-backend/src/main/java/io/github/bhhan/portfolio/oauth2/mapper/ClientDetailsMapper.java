package io.github.bhhan.portfolio.oauth2.mapper;

import io.github.bhhan.portfolio.oauth2.domain.ClientDetails;
import io.github.bhhan.portfolio.oauth2.web.api.ClientDetailsDto;

public class ClientDetailsMapper {
    public ClientDetails clientDetailsReqToClientDetails(ClientDetailsDto clientDetailsDto){
        return new ClientDetails.ClientDetailsBuilder()
                .clientId(clientDetailsDto.getClientId())
                .clientSecret(clientDetailsDto.getClientSecret())
                .resourceIds(clientDetailsDto.getResourceIds())
                .scope(clientDetailsDto.getScope())
                .authorizedGrantTypes(clientDetailsDto.getAuthorizedGrantTypes())
                .webServerRedirectUri(clientDetailsDto.getWebServerRedirectUri())
                .authorities(clientDetailsDto.getAuthorities())
                .accessTokenValidity(clientDetailsDto.getAccessTokenValidity())
                .refreshTokenValidity(clientDetailsDto.getRefreshTokenValidity())
                .additionalInformation(clientDetailsDto.getAdditionalInformation())
                .autoApprove(clientDetailsDto.getAutoApprove())
                .build();
    }
}
