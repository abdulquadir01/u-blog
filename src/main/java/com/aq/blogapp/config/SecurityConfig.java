package com.aq.blogapp.config;

import com.aq.blogapp.config.security.CustomUserDetailService;
import com.aq.blogapp.config.security.JwtAuthenticationEntryPoint;
import com.aq.blogapp.config.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;




@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvc
public class SecurityConfig {

    public static final String[] PUBLIC_URLS = {
            "/api/auth/**",
            "/v2/api-docs" ,
            "/v3/api-docs",
            "swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**"
    };

    private final CustomUserDetailService userDetailService;
    private final JwtAuthenticationEntryPoint authEntryPoint;
    private final JwtAuthenticationFilter authFilter;

    public SecurityConfig(CustomUserDetailService userDetailService, JwtAuthenticationEntryPoint authEntryPoint, JwtAuthenticationFilter authFilter) {
        this.userDetailService = userDetailService;
        this.authEntryPoint = authEntryPoint;
        this.authFilter = authFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(PUBLIC_URLS).permitAll()
                .antMatchers(HttpMethod.GET).permitAll()            //allow all GET request
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.authenticationProvider(daoAuthenticationProvider());
        httpSecurity.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Accept","Authorization","Content-Type", "Body"));
        configuration.setMaxAge(1800L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


//    @Bean
//    public FilterRegistrationBean corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true);
//        configuration.addAllowedOriginPattern("*");
//        configuration.addAllowedMethod("GET");
//        configuration.addAllowedMethod("POST");
//        configuration.addAllowedMethod("PUT");
//        configuration.addAllowedMethod("DELETE");
//        configuration.addAllowedMethod("OPTIONS");
//        configuration.addAllowedHeader("Accept");
//        configuration.addAllowedHeader("Authorization");
//        configuration.addAllowedHeader("Content-Type");
//        configuration.setMaxAge(1800L);
//
//        source.registerCorsConfiguration("/**", configuration);
//
//
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(-110);
//
//        return bean;
//    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider () throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
