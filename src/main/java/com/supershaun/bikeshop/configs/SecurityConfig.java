package com.supershaun.bikeshop.configs;

import com.supershaun.bikeshop.security.jwt.JwtAuthenticationEntryPoint;
import com.supershaun.bikeshop.security.jwt.JwtAuthenticationFilter;
import com.supershaun.bikeshop.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .and().authorizeRequests().antMatchers("/api/category/all").hasRole("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/api/items/**", "/api/category/**", "/media/**").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/api/orders").authenticated()
                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/api/orders").authenticated()
                .and().authorizeRequests().antMatchers(
                        "/api/items/**",
                        "/api/itemInstances/**",
                        "/api/itemInstanceSpecifications/**",
                        "/api/itemSpecifications/**",
                        "/api/orders/status/**",
                        "/api/orders/all").hasRole("MANAGER")
                .and().authorizeRequests().antMatchers(
                "/api/category/**",
                        "/api/users/**",
                        "/api/categorySpecifications/**").hasRole("ADMIN")
                .and().addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/api/auth/logout");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
