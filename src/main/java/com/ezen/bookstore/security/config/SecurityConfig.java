package com.ezen.bookstore.security.config;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
                .anyRequest().hasAuthority("ROLE_OPERATOR")
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
            .exceptionHandling(exception -> exception
        	    .accessDeniedHandler((request, response, accessDeniedException) -> {
        	        String errorMessage = URLEncoder.encode("접근 권한이 없습니다.", StandardCharsets.UTF_8.toString());
        	        response.sendRedirect("/admin/login?accessError=" + errorMessage);
        	    })
        	    .authenticationEntryPoint((request, response, authException) -> {
        	        String errorMessage = URLEncoder.encode("로그인이 필요합니다.", StandardCharsets.UTF_8.toString());
        	        response.sendRedirect("/admin/login?accessError=" + errorMessage);
        	    })
        	)
            .userDetailsService(customAdminDetailsService);

        return http.build();
    }

    // User 경로용
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
                .requestMatchers("/oauth2/authorization/**", "/login/oauth2/code/**", "/user/mypage/notices-page/**", "/user/mypage/notices/**", "/user/mypage/notices-detail/**").permitAll()
                .requestMatchers("/images/**").permitAll()
                .requestMatchers("/user/mypage/**", "/user/cart/**", "/user/order/**", "/user/order").hasAuthority("ROLE_USER")
                .anyRequest().permitAll()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/user/login") 
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService)  
                )
                .successHandler(new CustomUserAuthenticationSuccessHandler())
                .failureHandler(new CustomUserAuthenticationFailureHandler()) 

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
    
    @Bean
    @Order(3)
    SecurityFilterChain securityAdminMobileFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/mobile/admin/**")
            .authenticationProvider(adminAuthenticationProvider())
            .sessionManagement(session -> 
                session
                    .sessionFixation().newSession()
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                    .invalidSessionUrl("/mobile/admin/login") 
                    .maximumSessions(1)                 
                    .expiredUrl("/login?expired=true")  
            )
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/mobile/admin/login/**", "/mobile/common/**","/mobile/images/**").permitAll()
                .requestMatchers("/mobile/admin/**").hasAuthority("ROLE_WORKER")
                .anyRequest().hasAnyAuthority("ROLE_WORKER")
            )
            .formLogin(form -> form
                .loginProcessingUrl("/mobile/admin/loginProc")    
                .loginPage("/mobile/admin/login") 
                .successHandler(new CustomAdminAuthenticationSuccessHandler()) 
                .failureHandler(new CustomAdminAuthenticationFailureHandler()) 
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/mobile/admin/logout")
                .logoutSuccessUrl("/mobile/admin/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(exception -> exception
        	    .accessDeniedHandler((request, response, accessDeniedException) -> {
        	        String errorMessage = URLEncoder.encode("접근 권한이 없습니다.", StandardCharsets.UTF_8.toString());
        	        response.sendRedirect("/mobile/admin/login?accessError=" + errorMessage);
        	    })
        	    .authenticationEntryPoint((request, response, authException) -> {
        	        String errorMessage = URLEncoder.encode("로그인이 필요합니다.", StandardCharsets.UTF_8.toString());
        	        response.sendRedirect("/mobile/admin/login?accessError=" + errorMessage);
        	    })
        	)
            .userDetailsService(customAdminDetailsService);

        return http.build();
    }
}

    

	
