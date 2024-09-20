package com.dodatabase.backend.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(request -> request
            .requestMatchers(HttpMethod.GET, "/v1/home").permitAll() // 모두 접근 허용
            .requestMatchers(HttpMethod.GET, "/v1/wishlist").authenticated() // 로그인 필요
            .requestMatchers(HttpMethod.GET, "/v1/wish").authenticated() // 로그인 필요
            .requestMatchers(HttpMethod.POST, "/v1/wish").authenticated() // 로그인 필요
            .requestMatchers(HttpMethod.DELETE, "/v1/wish").authenticated() // 로그인 필요
            .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN") // 관리자 권한 필요
            .requestMatchers("/admin/**").hasAnyRole("ADMIN", "STAFF") // 관리자 또는 스태프 권한 필요
            .anyRequest().permitAll()) // 그 외 모든 요청은 모두 접근 허용
        .formLogin(login -> login
            .loginPage("/v1/login") // 로그인 페이지 설정
            .failureUrl("/v1/login?failure") // 로그인 실패 시 리다이렉트 URL
            .defaultSuccessUrl("/v1/home") // 로그인 성공 시 리다이렉트 URL
            .permitAll()) // 로그인 페이지 접근 허용
        .logout(logout -> logout.permitAll()) // 로그아웃 접근 허용
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(exceptionHandling -> exceptionHandling
            .authenticationEntryPoint((request, response, authException) -> {
              if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
              } else {
                response.sendRedirect("/v1/login");
              }
            }));
    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.builder()
        .username("user")
        .password("{noop}user")
        .roles("USER")
        .build();

    UserDetails admin = User.builder()
        .username("admin")
        .password("{noop}admin")
        .roles("ADMIN")
        .build();

    UserDetails tester = User.builder()
        .username("tester")
        .password("{noop}tester")
        .roles("TEST")
        .build();

    return new InMemoryUserDetailsManager(user, admin, tester);
  }
}
