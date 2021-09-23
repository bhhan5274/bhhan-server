package io.github.bhhan.portfolio.oauth2.mapper;

import io.github.bhhan.portfolio.oauth2.domain.Role;
import io.github.bhhan.portfolio.oauth2.web.api.RoleDto;

public class RoleMapper {
    public Role roleReqToRole(RoleDto.RoleReq roleReq){
        return Role.builder()
                .name(roleReq.getName())
                .build();
    }
}
