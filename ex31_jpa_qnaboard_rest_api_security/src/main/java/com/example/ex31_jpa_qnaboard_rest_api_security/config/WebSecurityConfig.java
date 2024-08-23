package com.example.ex31_jpa_qnaboard_rest_api_security.config;

import org.springframework.context.annotation.Bean; // 스프링 컨텍스트에 빈으로 등록하기 위한 어노테이션
import org.springframework.context.annotation.Configuration; // 이 클래스가 설정 파일임을 나타내는 어노테이션
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // http 보단을 설정하기 위한 클래스
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // 웹 보안을 활성화하기 위한 어노테이션
import org.springframework.security.config.http.SessionCreationPolicy; // 세션 관리 정책을 설정하기 위한 클래스
import org.springframework.security.web.SecurityFilterChain; // 시큐리티 필터 체인을 구성하는 클래스
import org.springframework.web.filter.CorsFilter; // CORS 필터를 처리하는 클래스
import com.example.ex31_jpa_qnaboard_rest_api_security.security.JwtAuthenticationFilter; // JWT 인증 필터 클래스


@Configuration // 이 클래스가 스프링 설정 관련 클래스임을 나타냄
@EnableWebSecurity // spring security 를 활성화
public class WebSecurityConfig {

  // JWTAuthenticationFilter 인스턴스를 생성자의 의존성 주입으로 받아옴
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  public WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
      this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  // Spring Security의 필터체인을 정의하는 메소드
  @Bean // 이 메소드의 반환값을 스프링 컨텍스트에 빈으로 등록
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
        // (csrf는 크로스사이트 요청위조 라고 해서 이미 도메인에 접근중인데 다른 브라우저로 접근하여서 중요 정보등을 사용할수없게 만드는 조치를 말함)
        .csrf(csrf -> csrf.disable()) // CSRF 보호 기능을 비활성화, REST API의 경우 일반적으로 비활성화 
        .sessionManagement( // 세션을 생성하지 않도록 설정, JWT를 사용하므로 서버에 세션상태를 저장할 필요가 없음 (stateless 방식)
          sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
        )
        .authorizeHttpRequests(
          authorizeRequests -> authorizeRequests
            .requestMatchers("/", "/auth/**").permitAll() // "/" 및 "/auth/**" 경로는 인증없이 접근 가능하도록 설정
            .anyRequest().authenticated() // 그 외의 요청은 모두 인증을 요구하도록 설정
        )
        .formLogin(formLogin -> formLogin.disable()) // 기본 폼 로그인 기능을 비활성화, REST API에서 주로 사용하지 않음
        .httpBasic(httpBasic -> httpBasic.disable()) // 기본 HTTP 인증 비활성화, (대신 JWT 인증을 사용함)
        .addFilterAfter(jwtAuthenticationFilter, CorsFilter.class); // JWT 인증 필터를 CORS 필터 뒤에 추가, 모든 요청에 대해 JWT 검증을 수행
        return http.build(); // 설정된 securityFilterChain을 반환
    }
}
