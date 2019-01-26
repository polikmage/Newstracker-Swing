/*
package org.mpo.newstracker.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.mpo.newstracker.service.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mpo.newstracker.security.SecurityConstants.HEADER_STRING;
import static org.mpo.newstracker.security.SecurityConstants.SECRET;
import static org.mpo.newstracker.security.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  CustomUserDetailsService customUserDetailsService) {
        super(authenticationManager);
        this.customUserDetailsService = customUserDetailsService;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);
                if (header == null || !header.startsWith(TOKEN_PREFIX)){
                    chain.doFilter(request,response);
                    return;
                }
        UsernamePasswordAuthenticationToken usernamePasswordAuth = getAuthToken(request);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuth);
        chain.doFilter(request,response);

    }
//TODO pokud token je prosly vyhazuje se ExpiredJwtException, nutno odchytit a vratit response...
    private UsernamePasswordAuthenticationToken getAuthToken(HttpServletRequest request) throws ExpiredJwtException {
        String token = request.getHeader(HEADER_STRING);
        String username = Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX,""))
                .getBody()
                .getSubject();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        return  username!=null ? new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities()) : null;
    }

    */
/*public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }*//*

}
*/
