package com.aq.blogapp.config;


import com.aq.blogapp.config.security.CustomUserDetailService;
import com.aq.blogapp.config.security.JwtAuthenticationEntryPoint;
import com.aq.blogapp.config.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvc
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf()
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

        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
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
}
