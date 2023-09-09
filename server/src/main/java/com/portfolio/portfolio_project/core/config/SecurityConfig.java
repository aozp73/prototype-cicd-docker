package com.portfolio.portfolio_project.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.portfolio.portfolio_project.core.jwt.JwtAuthorizationFilter;
import com.portfolio.portfolio_project.core.jwt.MyJwtProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecurityConfig {

    @Autowired
    private MyJwtProvider myJwtProvider;

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            builder.addFilterAt(new JwtAuthorizationFilter(authenticationManager , myJwtProvider), BasicAuthenticationFilter.class);
            super.configure(builder);
        }
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // // httpBasic 정책 해제 (BasicAuthenticationFilter 해제)
    // http.httpBasic().disable();

    // 6. XSS (lucy 필터)
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. CSRF 해제
        http.csrf().disable(); 

        // 2. ifram 거부
        http.headers().frameOptions().disable();

        // 3. cors 재설정
        http.cors().configurationSource(configurationSource());

        // 4. jSessionId 사용 거부
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 5. form 로그인 해제
        http.formLogin().disable();

        // 6. 커스텀 필터 적용 (시큐리티 필터 교환)
        http.apply(new CustomSecurityFilterManager());

        // 7. 인증 실패 처리
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            log.error("에러 : 인증 실패 : " + authException.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"data\":\"인증이 필요합니다.\"}");
        });

        // 8. 권한 실패 처리
        http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
            log.error("에러 : 권한 실패 : " + accessDeniedException.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"data\":\"권한이 없습니다.\"}");
        });

        // 9. 인증, 권한 필터 설정
        http.authorizeRequests(
                authroize -> authroize.antMatchers("/auth/**").authenticated()// 인증이 필요한곳
                        .antMatchers("/auth/**").access("hasRole('ROLE_admin')")
                        .anyRequest().permitAll());

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowCredentials(true); 
        configuration.addExposedHeader("Authorization"); 
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

// Security만 사용하여 로그인, 로그아웃 진행시 코드
// ===========================================
// @RequiredArgsConstructor
// @Configuration
// public class SecurityConfig {
	
// 	@Bean
// 	public BCryptPasswordEncoder encode() {
// 		return new BCryptPasswordEncoder();
// 	}
	
// 	@Bean
// 	SecurityFilterChain configure(HttpSecurity http) throws Exception {
// 		http.csrf().disable();
		
// 		http.authorizeRequests()
// 			.antMatchers("/auth/**").authenticated()
// 			.anyRequest().permitAll()
			
// 			.and()
// 			.formLogin()
// 			.loginPage("/login") // 인증이 필요한 페이지 요청 시 진행 (GET 방식)  
// 			.loginProcessingUrl("/auth/signin") // 로그인 POST요청 시 진행 (POST 방식)
// 			.defaultSuccessUrl("/main")
			
// 			.and()
// 			.logout()  // 로그아웃 관련 설정 추가
// 			.logoutUrl("/auth/logout")  // 로그아웃을 진행할 URL
// 			.logoutSuccessUrl("/main")  // 로그아웃 성공 후 리다이렉트될 URL
// 			.invalidateHttpSession(true)  // 로그아웃 시 세션 무효화
// 			.deleteCookies("JSESSIONID");  // 로그아웃 시 쿠키 삭제
		
// 		return http.build();
// 	}	
// }