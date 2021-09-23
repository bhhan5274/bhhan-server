package io.github.bhhan.portfolio.oauth2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "OAUTH_ROLES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Role {
    public static final String ROLE = "ROLE_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Long id;

    @Column(unique = true)
    private String name;

    @Builder
    public Role(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public String getRoleName(){
        return ROLE + name;
    }
}
