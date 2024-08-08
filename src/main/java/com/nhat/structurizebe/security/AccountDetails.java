package com.nhat.structurizebe.security;

import com.nhat.structurizebe.models.documents.AccountDocument;
import com.nhat.structurizebe.models.documents.RoleDocument;
import com.nhat.structurizebe.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AccountDetails implements UserDetails {

    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public AccountDetails(AccountDocument account, RoleRepository roleRepository) {
        this.email = account.getEmail();
        this.password = account.getPassword();
        List<GrantedAuthority> auths = new ArrayList<>();

        for (String roleId : account.getRoleIds()) {
            RoleDocument role = roleRepository.findById(roleId).orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleId));
            auths.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
}