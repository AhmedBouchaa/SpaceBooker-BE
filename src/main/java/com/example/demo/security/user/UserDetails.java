package com.example.demo.security.user;

import com.example.demo.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private User user; // Votre entité User
    public UserDetails(User user) {
        this.user = user;
    }
    @Override
    public String getPassword() {
        return user.getPwd(); // Le mot de passe haché
    }
    @Override
    public String getUsername() {
        return user.getUsername(); // Le nom d'utilisateur
    }
    @Override
    public boolean isAccountNonExpired() {
        return true; // Modifier selon votre logique métier
    }
    @Override
    public boolean isAccountNonLocked() {
        return true; // Modifier selon votre logique métier
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Modifier selon votre logique métier
    }
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        // Récupérer la chaîne de rôles et la diviser par les virgules
        String[] roles = user.getRole().split(",");
        // Utiliser une liste pour stocker les authorities
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.trim()));
        }
        return authorities;  // Retourner la liste d'autorités
    }

}
