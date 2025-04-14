package com.example.demo.Configuration;

import com.example.demo.Filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebMvc
@EnableWebSecurity
public class WebSecurity {
    private final JwtTokenFilter jwtTokenFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request ->{
                    request
                    .requestMatchers(
                            ("api/users/register"),
                            ("api/users/login")
                    )
                    .permitAll()
                            .requestMatchers(HttpMethod.POST,("api/order/**")).hasRole("user")
                            .requestMatchers(HttpMethod.PUT,("api/order/**")).hasRole("admin")
                            .requestMatchers(HttpMethod.DELETE,("api/order/**")).hasRole("admin")
                            .requestMatchers(HttpMethod.GET,("api/order/**")).permitAll()

                            .requestMatchers(HttpMethod.POST,("api/category/**")).hasRole("admin")
                            .requestMatchers(HttpMethod.PUT,("api/category/**")).hasRole("admin")
                            .requestMatchers(HttpMethod.DELETE,("api/category/**")).hasRole("admin")
                            .requestMatchers(HttpMethod.GET,("api/category/**")).permitAll()

                            .requestMatchers(HttpMethod.POST,("api/product/**")).hasRole("admin")
                            .requestMatchers(HttpMethod.PUT,("api/product/**")).hasRole("admin")
                            .requestMatchers(HttpMethod.DELETE,("api/product/**")).hasRole("admin")

                            .requestMatchers(HttpMethod.POST,("api/order_detail/**")).hasRole("user")
                            .requestMatchers(HttpMethod.PUT,("api/order_detail/**")).hasRole("admin")
                            .requestMatchers(HttpMethod.DELETE,("api/order_detail/**")).hasRole("admin")
                            .requestMatchers(HttpMethod.POST,("api/user/detail")).hasAnyRole("admin","user")

                            .requestMatchers(HttpMethod.GET,("api/order_detail/**")).permitAll()
                            .requestMatchers(HttpMethod.GET,("api/product/images/*")).permitAll()
                            .requestMatchers(HttpMethod.GET,("api/product/**")).permitAll()
                            .anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable);
        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration corsConfigurer = new CorsConfiguration();
                corsConfigurer.setAllowedOrigins(List.of("*"));
                corsConfigurer.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
                corsConfigurer.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                corsConfigurer.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", corsConfigurer);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
        return http.build();
    }
}
