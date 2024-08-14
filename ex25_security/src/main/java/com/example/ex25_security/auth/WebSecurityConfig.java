package com.example.ex25_security.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 설정파일이라는걸 알려주기위한 어노테이션
public class WebSecurityConfig {
  
  @Bean // 이 메소드의 반환값을 빈으로 등록함.
  public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
    // 아래의 경로에서 접근하는것에 대한 권한 설정
    http.authorizeHttpRequests(
      authorizeRequests -> authorizeRequests
      .requestMatchers("/").permitAll() // 루트경로에 대해 모든 사용자에게 접근을 허용
      .requestMatchers("/css/**", "/js/**", "/img/**").permitAll() // 정적 리소스 경로에 대한 접근 모두 허용
      .requestMatchers("/guest/**").permitAll()
      .requestMatchers("/member/**").hasAnyRole("USER", "ADMIN") // member에 접근시에 USER, ADMIN만 권한 허용
      .requestMatchers("/admin/**").hasRole("ADMIN")
      .anyRequest().authenticated() // 나머지 모든 요청에 대해서는 인증된 사용자만 접근할 수 있도록 설정
    ).formLogin(formLogin -> formLogin.permitAll()) // 폼 기반 로그인을 활성화함, 로그인 페이지와 처리url에 대해 모든 사용자가 접근가능 허용
    .logout(logout -> logout.permitAll()); // 로그아웃 기능을 활성화, 로그아웃 url에 대해 모든 사용자가 접근가능 허용

    return http.build(); // 설정된 보안 필터 체인을 빌드해서 반환함.
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.withUsername("user")
    .password(passwordEncoder().encode("1234"))
    .roles("USER")
    .build();

    return new InMemoryUserDetailsManager(user);
  }
}
