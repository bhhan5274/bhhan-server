package io.github.bhhan.portfolio.oauth2.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "OAUTH_CLIENT_DETAILS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "clientId")
public class ClientDetails{
    @Id
    private String clientId;
    private String resourceIds;
    private String clientSecret;
    private String scope;
    private String authorizedGrantTypes;
    private String webServerRedirectUri;
    private String authorities;
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;
    private String additionalInformation;
    private String autoapprove;

    private ClientDetails(ClientDetailsBuilder clientDetailBuilder){
        this.clientId = clientDetailBuilder.clientId;
        this.clientSecret = clientDetailBuilder.clientSecret;
        this.accessTokenValidity = clientDetailBuilder.accessTokenValidity;
        this.refreshTokenValidity = clientDetailBuilder.refreshTokenValidity;
        this.resourceIds = clientDetailBuilder.resourceIds;
        this.scope = clientDetailBuilder.scope;
        this.authorities = clientDetailBuilder.authorities;
        this.authorizedGrantTypes = clientDetailBuilder.authorizedGrantTypes;
        this.webServerRedirectUri = clientDetailBuilder.webServerRedirectUri;
        this.autoapprove = clientDetailBuilder.autoApprove;
        this.additionalInformation = clientDetailBuilder.additionalInformation;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public String toString() {
        return "ClientDetails{" +
                "clientId='" + clientId + '\'' +
                ", resourceIds='" + resourceIds + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", scope='" + scope + '\'' +
                ", authorizedGrantTypes='" + authorizedGrantTypes + '\'' +
                ", webServerRedirectUri='" + webServerRedirectUri + '\'' +
                ", authorities='" + authorities + '\'' +
                ", accessTokenValidity=" + accessTokenValidity +
                ", refreshTokenValidity=" + refreshTokenValidity +
                ", additionalInformation='" + additionalInformation + '\'' +
                ", autoapprove='" + autoapprove + '\'' +
                '}';
    }

    public static class ClientDetailsBuilder{
        private ObjectMapper objectMapper = new ObjectMapper();
        private String clientId;
        private String clientSecret;
        private String resourceIds;
        private String scope;
        private String authorizedGrantTypes;
        private String webServerRedirectUri;
        private String authorities;
        private Integer accessTokenValidity;
        private Integer refreshTokenValidity;
        private String additionalInformation;
        private String autoApprove;

        public ClientDetailsBuilder clientId(String clientId){
            this.clientId = Objects.nonNull(clientId) ? clientId : "";
            return this;
        }

        public ClientDetailsBuilder resourceIds(Set<String> resourceIds){
            this.resourceIds = Objects.nonNull(resourceIds) ? writeValueAsString(resourceIds) : "";
            return this;
        }

        public ClientDetailsBuilder clientSecret(String clientSecret){
            this.clientSecret = Objects.nonNull(clientSecret) ? clientSecret : "";
            return this;
        }

        public ClientDetailsBuilder scope(Set<String> scope){
            this.scope = Objects.nonNull(scope) ? writeValueAsString(scope) : "";
            return this;
        }

        public ClientDetailsBuilder authorizedGrantTypes(Set<String> authorizedGrantTypes){
            this.authorizedGrantTypes = Objects.nonNull(authorizedGrantTypes) ? writeValueAsString(authorizedGrantTypes) : "";
            return this;
        }

        public ClientDetailsBuilder webServerRedirectUri(Set<String> webServerRedirectUri){
            this.webServerRedirectUri = Objects.nonNull(webServerRedirectUri) ? writeValueAsString(webServerRedirectUri) : "";
            return this;
        }

        public ClientDetailsBuilder authorities(Set<String> authorities){
            this.authorities = Objects.nonNull(authorities) ? writeValueAsString(authorities) : "";
            return this;
        }

        public ClientDetailsBuilder autoApprove(Set<String> autoApprove){
            this.autoApprove = Objects.nonNull(autoApprove) ? writeValueAsString(autoApprove) : "";
            return this;
        }

        public ClientDetailsBuilder additionalInformation(Map<String, Object> additionalInformation){
            this.additionalInformation = Objects.nonNull(additionalInformation) ? writeValueAsString(additionalInformation) : "";
            return this;
        }

        public ClientDetailsBuilder accessTokenValidity(Integer accessTokenValidity){
            this.accessTokenValidity = Objects.nonNull(accessTokenValidity) ? accessTokenValidity : 4200;
            return this;
        }

        public ClientDetailsBuilder refreshTokenValidity(Integer refreshTokenValidity){
            this.refreshTokenValidity = Objects.nonNull(refreshTokenValidity) ? refreshTokenValidity : 4200;
            return this;
        }

        public ClientDetails build(){
            return new ClientDetails(this);
        }

        private String writeValueAsString(Collection<String> collection){
            try {
                return String.join(",", collection);
            }catch (Exception e){
                return "";
            }
        }

        private String writeValueAsString(Map<String, Object> map){
            try {
                return objectMapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                return "";
            }
        }
    }
}
