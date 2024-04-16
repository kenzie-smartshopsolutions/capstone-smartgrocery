package com.kenzie.appserver.config.auth;

import com.kenzie.appserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .csrf()
                    .ignoringAntMatchers("/User/")
                    .disable()
                .exceptionHandling()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authorizeRequests()
                    .antMatchers("/User/login/**",
                            "/User/register/**").permitAll()

                /**
                 Comment out TODO items when LIVE
                 * **/

//                * Allows open endpoints for development testing
//                * TODO comment out when LIVE

                .antMatchers("/example/**").permitAll()
                    .antMatchers("/Pantry/**").permitAll()
                    .antMatchers("/Recipe/**").permitAll()

//                 * Allows swaggerUI testing
//                 * TODO comment out when LIVE
                .antMatchers("/v1/api/get-token",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**").permitAll()

                // All other requests must be authenticated
                .anyRequest().authenticated().and()

        // Logout & invalidate authentication/session
        .logout()
                .logoutUrl("/User/logout")
                .logoutSuccessUrl("/User/login?logout")
                .deleteCookies("token")

                // Invalidate session
                .invalidateHttpSession(true)

                // Clear authentication attributes
                .clearAuthentication(true)
                .permitAll().and()

                // Add JWT token filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
