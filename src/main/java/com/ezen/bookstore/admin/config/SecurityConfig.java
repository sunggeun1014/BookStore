package com.ezen.bookstore.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // 기본 사용자 계정 설정 (user / generated security password)
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                               .password(bCryptPasswordEncoder().encode("password")) // 비밀번호는 임시로 설정한 후 콘솔에 출력된 임시 비밀번호로 교체 가능
                               .roles("USER")
                               .build();
        return new InMemoryUserDetailsManager(user);
    }
    //"cba91cf4-1064-4ea0-9147-7d839c1092e9"
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/login").permitAll() // 로그인 페이지는 누구나 접근 가능
                .requestMatchers("/admin/index").authenticated() 
                .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER") // /my/** 경로는 ADMIN 또는 USER 권한이 있어야 접근 가능
                .requestMatchers("/admin/common/**","/admin/login/**").permitAll() // /admin/common/ 하위 경로의 모든 리소스에 접근 허용
                .anyRequest().authenticated() // 그 외의 모든 요청은 인증이 필요
        );

        http.formLogin(form -> form
                .loginPage("/admin/login") // 커스텀 로그인 페이지 경로 설정
                .loginProcessingUrl("/loginProc") // 로그인 처리 URL 설정
                .defaultSuccessUrl("/admin/index", true) // 로그인 성공 시 기본 리다이렉션 URL 설정
                .permitAll()
        );
        
        http.csrf(csrf -> csrf.disable()); // CSRF 보호 비활성화 (개발용)
        
        return http.build();
    }
}
