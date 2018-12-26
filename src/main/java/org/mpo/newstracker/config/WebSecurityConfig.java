package org.mpo.newstracker.config;

import org.mpo.newstracker.security.JwtAuthenticationFilter;
import org.mpo.newstracker.security.JwtAuthorizationFilter;
import org.mpo.newstracker.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

//@Configuration
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    //@Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Autowired
    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //System.out.println(EXPOSED_HEADER);
        http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);

        //http.cors().and().csrf().disable().antMatcher("/sign_on").authorizeRequests().anyRequest().permitAll();

        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                //ZASADNI, u exact match  pro sign_up musi byt cela cesta vcetne v1
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/v1/sign_up").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/actuator").permitAll()
                //.antMatchers("/h2").permitAll()
                //.antMatchers("/h2/**").permitAll()
                .antMatchers("/v1/admin/**").hasRole("ADMIN")//.hasRole("ADMIN")
                .antMatchers("/v1/user/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                //.addFilter(getJWTAuthenticationFilter())
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), customUserDetailsService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

/*    //@Bean
    public JwtAuthenticationFilter getJWTAuthenticationFilter() throws Exception {
        final JwtAuthenticationFilter filter = new JwtAuthenticationFilter(authenticationManager());
        //TODO get login url from configuration file
        filter.setFilterProcessesUrl("/v1/login");
        return filter;
    }*/

}



