package com.esgi.api_project_annuel.application.security;



import com.esgi.api_project_annuel.application.filter.CustomAuthenticationFilter;
import com.esgi.api_project_annuel.application.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        http.cors().and().authorizeRequests().antMatchers("/resource").permitAll();
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests()
                .antMatchers(
                        HttpMethod.POST,
                        "/login",
                        "/token/refresh",
                        "/user/create"
                ).permitAll()
                .antMatchers(
                        HttpMethod.POST,
                        "/code/create",
                        "/comment/create",
                        "/follow/create",
                        "/group/create",
                        "/like/create",
                        "/post/create",
                        "/project/create",
                        "/snippet/create",
                        "/user_role_group/create"
                ).hasAnyAuthority("USER","ADMIN")
                .antMatchers(
                        HttpMethod.POST,
                        "/role/create",
                        "/language/create"
                ).hasAnyAuthority("ADMIN")
                .antMatchers(
                        HttpMethod.GET,
                        "/**",
                        "/group/name/**"
                ).hasAnyAuthority("USER","ADMIN")
        //todo, pour update un user peut etre verifier dans la route que lemail du token = email user modifer
        //todo idem pour projet verifier l owner, pareil group
        //todo idem pour delete
                .antMatchers(
                        HttpMethod.PUT,
                        "/code/**",
                        "/comment/**",
                        "/follow/**",
                        "/group/**",
                        "/like/**",
                        "/post/**",
                        "/project/**",
                        "/snippet/**",
                        "/user/email/**",
                        "/user/password/**"
                ).hasAnyAuthority("USER","ADMIN")
                .antMatchers(
                        HttpMethod.PATCH,
                        "/code/**",
                        "/comment/**",
                        "/follow/**",
                        "/group/**",
                        "/like/**",
                        "/post/**",
                        "/project/**",
                        "/snippet/**",
                        "/user/email/**",
                        "/user/password/**"
                ).hasAnyAuthority("USER","ADMIN")
                .antMatchers(
                        HttpMethod.DELETE,
                        "/code/delete/**",
                        "/comment/delete/**",
                        "/follow/delete/**",
                        "/group/delete/**",
                        "/like/delete/**",
                        "/post/delete/**",
                        "/project/delete/**",
                        "/snippet/delete/**",
                        "/user/delete/**"
                ).hasAnyAuthority("USER","ADMIN");

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    /*@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH",
                "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }*/



}
