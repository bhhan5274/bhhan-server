package io.github.bhhan.portfolio.oauth2.web.api;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RoleDto {
    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RoleReq {
        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9_-]{2,20}$")
        private String name;

        @Builder
        public RoleReq(String name){
            this.name = name;
        }
    }
}
