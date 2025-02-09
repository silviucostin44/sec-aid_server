package com.example.secaidserver.config;

import com.example.secaidserver.model.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class JwtAuthSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final UserDetailsService userDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    /**
     * Configure AuthenticationManager so that it knows from where to load user for matching credentials
     * Use BCryptPasswordEncoder
     *
     * @param auth the auth manager builder
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // Disable CSRF
        httpSecurity.csrf().disable()
                    .cors().and()
                    .authorizeRequests()
                    .antMatchers("/authenticate", "/register", "/files/**", "/ie/**",
                                 "/questionnaire/default", "/questionnaire/update-rating").permitAll()
                    // any authenticated user can perform all operations
                    .antMatchers("/**").hasAnyRole(Role.ADMIN, Role.USER)
                    // Permit all other request without authentication
                    .and().authorizeRequests().anyRequest().permitAll()
                    // Reject every unauthenticated request and send error code 401.
                    .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    // We don't need sessions to be created.
                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
