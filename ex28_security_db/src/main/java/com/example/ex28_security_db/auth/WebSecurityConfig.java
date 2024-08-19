package com.example.ex28_security_db.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

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

  @Autowired
  private DataSource dataSource; // 데이터베이스 연결을 위해 DataSource를 주입 (db와 연결을 관리)

  @Bean
  public UserDetailsService userDetailsService() {
    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource); // db에서 정보를 로드해와서 빈으로 등록
    users.setUsersByUsernameQuery("select name, password, enabled from user_list where name = ?"); // 사용자 쿼리 설정
    users.setAuthoritiesByUsernameQuery("select name, authority from user_list where name = ?"); // 권한 쿼리 설정
    return users;
  }

  // 1. 인코딩을 거치지않은 문자열 바로 비교방식
  // @Bean
  // public PasswordEncoder passwordEncoder() { // 비밀번호를 인코딩하고 비교
  //   return new PasswordEncoder() {
  //     // 입력된 비밀번호를 그대로 문자열로 변환해 반환 (즉, 비밀번호 인코딩을 하지않는 방식)
  //     @Override
  //     public String encode(CharSequence rawPassword) {
  //       return rawPassword.toString();
  //     }
  //     // 사용자가 입력한 비밀번호와 db에 저장된 인코딩된 비밀번호를 비교 (저장된 비밀번호가 인코딩이 되어있지않아 문자열만 일치하면 인증성공)
  //     @Override
  //     public boolean matches(CharSequence rawPassword, String encodedPassword) {
  //       return rawPassword.toString().equals(encodedPassword);
  //     }
  //   };
  // }

  // 2. 인코딩(해시함수를 이용해 비밀번호를 암호화)을 통해 비교방식
  // (이미 해시화 되어서 해시값으로 저장된 db패스워드와 사용자가 입력한 평문 패스워드를 암호화해서 해시값으로 변경후 그 두 값을 비교함)
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // BCryptPasswordEncoder는 spring security에서 제공하는 클래스로 BCrypt 해시 함수를 사용해 비밀번호를 암호화 시켜줌
  }
}
