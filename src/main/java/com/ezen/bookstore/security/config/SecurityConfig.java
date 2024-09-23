package com.ezen.bookstore.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ezen.bookstore.security.service.admin.CustomAdminAuthenticationFailureHandler;
import com.ezen.bookstore.security.service.admin.CustomAdminAuthenticationSuccessHandler;
import com.ezen.bookstore.security.service.admin.CustomAdminDetailsService;
import com.ezen.bookstore.security.service.user.CustomOAuth2UserService;
import com.ezen.bookstore.security.service.user.CustomUserAuthenticationFailureHandler;
import com.ezen.bookstore.security.service.user.CustomUserAuthenticationSuccessHandler;
import com.ezen.bookstore.security.service.user.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAdminDetailsService customAdminDetailsService;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final PasswordEncoder passwordEncoder;
  
    @Bean
    DaoAuthenticationProvider adminAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customAdminDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);  
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    @Bean
    DaoAuthenticationProvider userAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService); 
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }
    
    // Admin 경로용 SecurityFilterChain
    @Bean
    @Order(1)
    SecurityFilterChain securityAdminFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/admin/**")
            .authenticationProvider(adminAuthenticationProvider())
            .sessionManagement(session -> 
                session
                    .sessionFixation().newSession()
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                    .invalidSessionUrl("/admin/login") 
                    .maximumSessions(1)                 
                    .expiredUrl("/login?expired=true")  
            )
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/login/**", "/admin/common/**","/images/**").permitAll()
                .anyRequest().hasAuthority("ROLE_ADMIN")
            )
            .formLogin(form -> form
                .loginProcessingUrl("/admin/loginProc")    
                .loginPage("/admin/login") 
                .successHandler(new CustomAdminAuthenticationSuccessHandler()) 
                .failureHandler(new CustomAdminAuthenticationFailureHandler()) 
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .userDetailsService(customAdminDetailsService);

        return http.build();
    }

    // User 경로용 SecurityFilterChain
    @Bean
    @Order(2)
    SecurityFilterChain securityUserFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/user/**", "/oauth2/**", "/login/**")
            .authenticationProvider(userAuthenticationProvider())
            .sessionManagement(session -> 
                session
                    .sessionFixation().newSession()
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                    .invalidSessionUrl("/user/login") 
                    .maximumSessions(1) 
                    .expiredUrl("/login?expired=true")  
            )
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/oauth2/authorization/**", "/login/oauth2/code/**").permitAll() // 여기를 전역적으로 허용
                .requestMatchers("/images/**").permitAll()
                .requestMatchers("/user/mypage/**", "/user/cart/**").hasAuthority("ROLE_USER")
                .anyRequest().permitAll()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/user/login")  // 사용자 정의 로그인 페이지
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService)  // 사용자 정보를 처리하는 서비스
                )
                .successHandler(new CustomUserAuthenticationSuccessHandler())
            )
            .formLogin(form -> form
                .loginProcessingUrl("/user/loginProc")    
                .loginPage("/user/login") 
                .successHandler(new CustomUserAuthenticationSuccessHandler()) 
                .failureHandler(new CustomUserAuthenticationFailureHandler()) 
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/user/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.sendRedirect("/user/login");
                })
                .authenticationEntryPoint((request, response, authException) -> {
                	response.sendRedirect("/user/login");
                })
            )
            .userDetailsService(customUserDetailsService);

        return http.build();
    }
}

    

	
