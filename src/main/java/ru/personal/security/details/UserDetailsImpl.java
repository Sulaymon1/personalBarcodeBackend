package ru.personal.security.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.personal.models.User;
import ru.personal.security.role.Role;

import java.util.Collection;
import java.util.Collections;

/**
 * Date 24.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
public class UserDetailsImpl implements UserDetails {

    private User user;

    public UserDetailsImpl(Long id, Role role, String phoneNumber) {
        this.user =  User.builder()
                .id(id)
                .role(role)
                .phoneNumber(phoneNumber)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
        return Collections.singletonList(authority);
    }


    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getPhoneNumber();
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

    public User getUser() {
        return user;
    }
}
