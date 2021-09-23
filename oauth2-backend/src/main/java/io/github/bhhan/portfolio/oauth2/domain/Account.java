package io.github.bhhan.portfolio.oauth2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "OAUTH_ACCOUNTS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;
    private String password;

    @ManyToMany
    @JoinTable(name = "OAUTH_ACCOUNT_ROLES", joinColumns = @JoinColumn(name = "ACCOUNT_ID"),
        inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<Role> roles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCOUNT_ID")
    private List<ClientDetails> clientDetailsList = new ArrayList<>();

    @Builder
    public Account(Long id, String email, String name, String password, List<Role> roles, List<ClientDetails> clientDetailsList){
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;

        if(Objects.nonNull(roles)){
            this.roles.addAll(roles);
        }

        if(Objects.nonNull(clientDetailsList)){
            this.clientDetailsList.addAll(clientDetailsList);
        }
    }

    public boolean addRole(Role role){
        return this.roles.add(role);
    }

    public boolean removeRole(Role role){
        return this.roles.remove(role);
    }

    public boolean addClientDetails(ClientDetails clientDetails){
        return this.clientDetailsList.add(clientDetails);
    }

    public ClientDetails getClientDetails(String clientId){
        int index = this.clientDetailsList.indexOf(new ClientDetails.ClientDetailsBuilder().clientId(clientId).build());

        if(index == -1){
            throw new IllegalArgumentException("Invalid ClientId");
        }

        return this.clientDetailsList.get(index);
    }

    public boolean removeClientDetails(String clientId){
        return this.clientDetailsList.removeIf(clientDetails -> clientDetails.getClientId().equals(clientId));
    }

    public boolean removeClientDetails(ClientDetails clientDetails){
        return this.clientDetailsList.remove(clientDetails);
    }
}
