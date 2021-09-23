package io.github.bhhan.portfolio.oauth2.service;

import io.github.bhhan.portfolio.oauth2.domain.Role;
import io.github.bhhan.portfolio.oauth2.domain.RoleRepository;
import io.github.bhhan.portfolio.oauth2.exception.RoleException;
import io.github.bhhan.portfolio.oauth2.mapper.RoleMapper;
import io.github.bhhan.portfolio.oauth2.web.api.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public Role addRole(RoleDto.RoleReq roleReq){
        try{
            return roleRepository.save(roleMapper.roleReqToRole(roleReq));
        }catch(Exception e){
            throw new RoleException("Role 추가에 실패했습니다.");
        }
    }

    public void deleteRole(Long roleId){
        try{
            roleRepository.deleteById(roleId);
        }catch(Exception e){
            throw new RoleException("Role 삭제에 실패했습니다.");
        }
    }
}
