/*
package org.mpo.newstracker.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mpo.newstracker.entity.dto.LoginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sun.misc.IOUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import static org.mpo.newstracker.security.SecurityConstants.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    //{"username":"batman@cz.cz","password":"pass"}
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {

        LoginDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);


        log.info("/login: user: "+ loginDto.getUsername()+ " is trying to login");
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword(),
                new ArrayList<>()
        ));
        } catch(IOException e){
        throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        LocalDateTime expirationTime = LocalDateTime.now().plus(EXPIRATION_TIME, ChronoUnit.SECONDS);
        log.info("expiration time is = " + expirationTime);
        String token = Jwts.builder().setSubject(((User)authResult.getPrincipal()).getUsername())
                .setExpiration(Date.from(expirationTime.toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .compact();
        response.addHeader("Access-Control-Expose-Headers","Authorization");
        response.addHeader(HEADER_STRING,TOKEN_PREFIX + token);
    }
}
*/
