package io.github.bhhan.portfolio.dashboard.service.user;

import io.github.bhhan.portfolio.dashboard.domain.user.Account;
import io.github.bhhan.portfolio.dashboard.domain.user.Role;
import io.github.bhhan.portfolio.dashboard.service.BaseServiceTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = BaseServiceTest.Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountServiceTest extends BaseServiceTest{

    private static final String EMAIL = "test@email.com";
    private static final String PASSWORD = "testPassword";
    private static Long ROLE_ID;
    private static Long ACCOUNT_ID;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("addAccount")
    @Order(1)
    void addAccount(){
        ROLE_ID = roleService.addRole(Role.ADMIN).getId();
        Account account = accountService.addAccount(EMAIL, PASSWORD, Collections.singletonList(Role.ADMIN));
        ACCOUNT_ID = account.getId();

        assertEquals(EMAIL, account.getEmail());
        assertTrue(passwordEncoder.matches(PASSWORD, account.getPassword()));

        for (Role role : account.getRoles()) {
            assertEquals(ROLE_ID, role.getId());
            assertEquals("ROLE_" + Role.ADMIN, role.getRole());
        }
    }

    @Test
    @DisplayName("loadUserByUsername")
    @Order(2)
    void loadUserByUsername(){
        UserDetails userDetails = accountService.loadUserByUsername(EMAIL);

        assertEquals(EMAIL, userDetails.getUsername());
        assertTrue(passwordEncoder.matches(PASSWORD, userDetails.getPassword()));

        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            assertEquals("ROLE_" + Role.ADMIN, authority.getAuthority());
        }
    }

    @Test
    @DisplayName("clear")
    @Order(3)
    void clear(){
        accountRepository.deleteById(ACCOUNT_ID);
        roleRepository.deleteById(ROLE_ID);
    }
}
