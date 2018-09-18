package ru.personal.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.personal.models.User;
import ru.personal.security.Authentication.JwtAuthentication;
import ru.personal.security.JwtTokenUtil;
import ru.personal.security.details.UserDetailsImpl;

/**
 * Date 24.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/

@Component
public class JwtAuthProvider implements AuthenticationProvider {


    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        User user = jwtTokenUtil.getUserFromToken(jwtAuthentication.getName());
        UserDetails userDetails = new UserDetailsImpl(
                user.getId(),
                user.getRole(),
                user.getPhoneNumber());
        jwtAuthentication.setUserDetails(userDetails);
        jwtAuthentication.setAuthenticated(true);
        return jwtAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}
