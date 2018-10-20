package ru.personal.security.filters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.personal.security.Authentication.JwtAuthentication;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Date 24.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Component
public class JwtAuthenticationFilter implements Filter {


    @Value("${jwt.header}")
    private String tokenHeader;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getParameter(tokenHeader);

        Authentication authentication;
        if (token == null){
            authentication = new JwtAuthentication(null);
            authentication.setAuthenticated(false);
        }else {
            authentication = new JwtAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
