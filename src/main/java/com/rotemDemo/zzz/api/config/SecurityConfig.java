package com.rotemDemo.zzz.api.config;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rotemDemo.zzz.api.jwt.JwtAuthenticationFilter;
import com.rotemDemo.zzz.api.jwt.JwtTokenProvider;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${cors.allowed-origins:http://localhost:9001}")
    private List<String> allowedOrigins;

    @Value("${cors.allowed-origin-patterns:}")
    private List<String> allowedOriginPatterns;

    @Value("${server.type:local}")
    private String serverType;
    
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"success\":false, \"code\":\"401\", \"message\":\"Unauthorized\"}");
                })
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(
                    "/api/public/**",
                    "/api/auth/login", "/api/auth/logout", "/api/auth/reissue",
                    "/api/auth/pwReset", "/api/auth/change-password", "/api/auth/pwChange", "/api/auth/pwChagne",
                    "/error", "/api/unit/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), 
                             UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
 
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        
        if (allowedOriginPatterns != null && !allowedOriginPatterns.isEmpty() && !isBlankList(allowedOriginPatterns)) {
            config.setAllowedOriginPatterns(allowedOriginPatterns);
        } else if ("local".equalsIgnoreCase(serverType)) {
            config.setAllowedOriginPatterns(List.of("*"));
        } else {
            config.setAllowedOrigins(allowedOrigins);
        }
        
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }    

    private boolean isBlankList(List<String> values) {
        if (values == null || values.isEmpty()) {
            return true;
        }
        return values.stream().allMatch(v -> v == null || v.trim().isEmpty());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
