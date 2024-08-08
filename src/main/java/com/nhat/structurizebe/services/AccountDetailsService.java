package com.nhat.structurizebe.services;

import com.nhat.structurizebe.models.documents.AccountDocument;
import com.nhat.structurizebe.repositories.AccountRepository;
import com.nhat.structurizebe.security.AccountDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountDocument user = accountRepository.findByEmail(username).orElse(null);
        if(user == null){
            throw new UsernameNotFoundException("could not found user..!!");
        }
        return new AccountDetails(user);
    }
}