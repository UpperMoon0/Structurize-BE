package com.nhat.structurizebe.services;

import com.nhat.structurizebe.models.documents.AccountDocument;
import com.nhat.structurizebe.models.documents.RoleDocument;
import com.nhat.structurizebe.models.dto.request.RegisterRequest;
import com.nhat.structurizebe.repositories.AccountRepository;
import com.nhat.structurizebe.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public void register(String email, String username, String password) {

        AccountDocument account = new AccountDocument();
        account.setEmail(email);
        account.setUsername(username);
        String encodedPassword = passwordEncoder.encode(password);
        account.setPassword(encodedPassword);

        RoleDocument role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));
        account.getRoles().add(role);

        accountRepository.save(account);
    }

    public String login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(email);
        } else {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }
}
