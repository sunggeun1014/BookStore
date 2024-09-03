package com.ezen.bookstore.admin.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ezen.bookstore.admin.security.service.CustomAuthenticationFailureHandler;
import com.ezen.bookstore.admin.security.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
	private final PasswordEncoder passwordEncoder;

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);  // 주입받은 PasswordEncoder 사용
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        	.authenticationProvider(authenticationProvider())
        	.sessionManagement(session -> session
            .sessionFixation().migrateSession()  // 세션 고정 보호 설정
            .maximumSessions(1)                 // 최대 동시 세션 수
            .expiredUrl("/login?expired=true")  // 세션 만료 시 리다이렉트할 URL
            )
        	.csrf(csrf -> csrf.disable())
        	.authorizeHttpRequests(auth -> auth
                // 로그인 페이지와 리소스 경로는 누구나 접근 가능
                // "/admin/**", "/**/"
                .requestMatchers("/admin/login/**", "/admin/resources/**", "/static/**", "/admin/common/**", "/admin/**").permitAll()
                .anyRequest().hasAuthority("ROLE_USER") // ROLE_USER 권한이 있어야만 접근 가능
            )
            .formLogin(form -> form
            	.loginProcessingUrl("/loginProc")	
                .loginPage("/admin/login") // 커스텀 로그인 페이지 설정
                .defaultSuccessUrl("/admin/index", true) // 로그인 성공 시 리다이렉트 경로
                .failureHandler(new CustomAuthenticationFailureHandler()) // 커스텀 FailureHandler 설정
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .sessionManagement(session -> session
                .invalidSessionUrl("/admin/login") // 세션이 없는 경우 로그인 페이지로 리다이렉트
            )
            .userDetailsService(customUserDetailsService); // MyBatis를 사용한 UserDetailsService 설정

        return http.build();
    }
    
    /*
     	.requestMatchers("/public/**").permitAll() : /public으로 시작하는 모든 url에 대해 접근 허용 로그인 하지 않은 사용자도 경로 접근 가능
     	.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") : "/admin/** 로 시작하는 모든 url에 대해 ROLE_ADMIN 권한 있는 사람만 접근 가능
		.requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN") : "/user/** 로 시작하는 모든 url에 대해 ROLE_USER 또는 ROLE_ADMIN 권한이 있는 사용자만 접근할 수 있음
		.requestMatchers("/secure/**").hasIpAddress("192.168.1.0/24") : "/secure/** 로 시작하는 모든 URL에 대해 특정 IP주소 대역에서만 접근을 허용합니다
		.requestMatchers("/login", "/static/**", "/resources/**").permitAll() : 로그인 페이지("/login"), 정적 리소스("/static/**"), 그리고 리소스 폴더("/resources/**")에 대해서는 모두 접근을 허용
		.anyRequest().authenticated() : 앞에서 명시적으로 허용된 URL 패턴을 제외한 모든 요청에 대해 인증된 사용자만 접근할 수 있도록 설정합니다.
		
				http
		    .authorizeHttpRequests(auth -> auth
		        .requestMatchers("/public/**").permitAll()
		        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
		        .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
		        .anyRequest().authenticated()
		    )
		    .formLogin(form -> form
		        .loginPage("/login")
		        .defaultSuccessUrl("/home", true)
		        .permitAll()
		    )
		    .logout(logout -> logout
		        .logoutUrl("/logout")
		        .logoutSuccessUrl("/login")
		        .invalidateHttpSession(true)
		        .deleteCookies("JSESSIONID")
		        .permitAll()
    );
     */
	
}