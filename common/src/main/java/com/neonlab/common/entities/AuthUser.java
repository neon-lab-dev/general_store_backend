package com.neonlab.common.entities;

import com.neonlab.common.constants.GlobalConstants;
import com.neonlab.common.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@Table(name = "auth_user", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_user_name", columnList = "user_name")
})
public class AuthUser extends Generic implements UserDetails {

    public AuthUser(){
        super();
    }

    public AuthUser(String createdBy, String modifiedBy){
        super(createdBy, modifiedBy);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "active")
    private boolean active;

    @Column(name = "roles")
    private String roles = Role.USER.name();

    @Column(name = "token")
    private String token;

    @Column(name = "password")
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var roles = this.roles.split(GlobalConstants.Symbols.COMMA);
        var retVal = new ArrayList<SimpleGrantedAuthority>();
        Arrays.stream(roles)
                .forEach(role -> retVal.add(new SimpleGrantedAuthority(String.format("ROLE_%s", role))));
        return retVal;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.token;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //generic

}
