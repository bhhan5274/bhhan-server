package io.github.bhhan.portfolio.dashboard.service.user;

import io.github.bhhan.portfolio.dashboard.domain.user.Role;
import io.github.bhhan.portfolio.dashboard.service.BaseServiceTest;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = BaseServiceTest.Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleServiceTest extends BaseServiceTest {
    private static Long ROLE_ID;

    @Test
    @DisplayName("addRole")
    @Order(1)
    void addRole(){
        Role role = roleService.addRole(Role.ADMIN);
        ROLE_ID = role.getId();

        assertEquals("ROLE_" + Role.ADMIN, role.getRole());
    }

    @Test
    @DisplayName("getRole")
    @Order(2)
    void getRole(){
        Role findRole = roleService.getRole(Role.ADMIN);

        assertEquals(ROLE_ID, findRole.getId());
        assertEquals("ROLE_" + Role.ADMIN, findRole.getRole());
    }

    @Test
    @DisplayName("removeRole")
    @Order(3)
    void removeRole(){
        roleService.removeRole(Role.ADMIN);

        assertEquals(roleRepository.count(), 0);
    }
}
