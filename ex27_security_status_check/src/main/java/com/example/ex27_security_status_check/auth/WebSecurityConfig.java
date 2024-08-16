package com.example.ex27_security_status_check.auth;

import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private CustomAuthenticationFailureHandler customAuthenticationFailureHandlerHandler;
  
  @Bean // 이 메소드의 반환값을 빈으로 등록함.
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // 아래의 경로에서 접근하는것에 대한 권한 설정
    http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
      .requestMatchers("/").permitAll() // 루트경로에 대해 모든 사용자에게 접근을 허용
      .requestMatchers("/css/**", "/js/**", "/img/**").permitAll() // 정적 리소스 경로에 대한 접근 모두 허용
      .requestMatchers("/guest/**").permitAll()
      .requestMatchers("/member/**").hasAnyRole("USER", "ADMIN") // member에 접근시에 USER, ADMIN만 권한 허용
      .requestMatchers("/admin/**").hasRole("ADMIN")
      .anyRequest().authenticated() // 나머지 모든 요청에 대해서는 인증된 사용자만 접근할 수 있도록 설정
    ).formLogin(formLogin -> formLogin
      .permitAll() // 폼 기반 로그인을 활성화함, 로그인 페이지와 처리url에 대해 모든 사용자가 접근가능 허용
      .loginPage("/login-form") // 미리 만들어둔 로그인 페이지를 로그인 페이지로서 사용함.
      .loginProcessingUrl("/security-check")
      .defaultSuccessUrl("/member/welcome", true) // 로그인 성공시 이동할 url
      // .failureUrl("/login-error") // 로그인 실패시 이동할 url
      .failureHandler(customAuthenticationFailureHandlerHandler) // 로그인 실패시 핸들러를 이용해서 예외처리
      .permitAll() // 모두 접근 허용
    ).logout(logout -> logout
      .logoutUrl("/logout")
      .logoutSuccessUrl("/login?logout")
      .permitAll()
    ); // 로그아웃 기능을 활성화, 로그아웃 url에 대해 모든 사용자가 접근가능 허용

    return http.build(); // 설정된 보안 필터 체인을 빌드해서 반환함.
  }

  // 이때 따로 계정을 생성해 정보를 넣지않으면 기본 default 아이디는 'user'이며 비밀번호는 log에 Using generated security password 에 뜸.

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.withUsername("user") // UserDetail은 사용자 정보를 불러오는데 사용되는 인터페이스로 구현하면 사용자 인증에 필요한 정보 제공받을수있음
    .password(passwordEncoder().encode("1234")) // 유저의 아이디(user)와 비밀번호(1234)를 설정 -> 이때 passwordEncoder().encode()를 사용해 비밀번호 암호화
    .roles("USER") // 그리고 권한은 USER로 주고
    .build(); // 빌드

    UserDetails admin = User.withUsername("admin")
    .password(passwordEncoder().encode("1234"))
    .roles("ADMIN") 
    .build(); 

    return new InMemoryUserDetailsManager(user, admin); // InMemoryUserDetailsManager 는 사용자 정보를 저장하고 관리
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // BCryptPasswordEncoder는 spring security에서 제공하는 클래스로 BCrypt 해시 함수를 사용해 비밀번호를 암호화 시켜줌
  }
}
