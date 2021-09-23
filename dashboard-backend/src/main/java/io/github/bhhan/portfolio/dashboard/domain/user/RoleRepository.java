package io.github.bhhan.portfolio.dashboard.domain.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String roleName);
    Long removeByName(String roleName);
}
