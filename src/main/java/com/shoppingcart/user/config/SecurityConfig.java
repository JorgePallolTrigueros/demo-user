package com.shoppingcart.user.config;


import com.shoppingcart.user.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Desactivar CSRF utilizando la nueva API
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(

                                "/v1/user/sign-up",
                                "/v1/user/recovery-password",
                                "/v1/user/login",
                                "/v3/api-docs/**",  // Cambia "/v2/api-docs" por "/v3/api-docs/**" si usas OpenAPI 3
                                "/swagger-resources/**",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/reset-password/**",
                                "/error-page/**",
                                "/password-changed/**",
                                "/reset-password.html",
                                "/swagger-ui.html",  // Esta línea es importante
                                "/swagger.json"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/v3/api-docs/**",  // Cambia "/v2/api-docs" por "/v3/api-docs/**" si usas OpenAPI 3
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/webjars/**",
                "/swagger-ui.html",  // Esta línea es importante
                "/swagger.json",
                "/v1/user-password/**"
        );
    }

}
