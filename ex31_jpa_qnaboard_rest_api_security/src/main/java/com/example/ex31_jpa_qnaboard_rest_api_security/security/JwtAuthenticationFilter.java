package com.example.ex31_jpa_qnaboard_rest_api_security.security;

import org.springframework.beans.factory.annotation.Autowired; // 스프링 프레임워크에서 의존성 주입을 자동으로 처리하기 위한 어노테이션
import org.springframework.security.authentication.AbstractAuthenticationToken; // 인증 객체를 나타내는 추상 클래스
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // 사용자 이름과 비밀번호 기반의 인증 객체
import org.springframework.security.core.authority.AuthorityUtils; // 권한 정보를 관리하는 유틸리티 클래스
import org.springframework.security.core.context.SecurityContext; // 현재 인증된 사용자의 정보를 담고 있는 보안 컨텍스트 인터페이스
import org.springframework.security.core.context.SecurityContextHolder; // SecurityContext를 저장하고 관리하는 홀더 클래스
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource; // 웹 기반 인증 세부정보를 생성하는 클래스
import org.springframework.stereotype.Component; // 이 클래스가 스프링의 빈으로 관리되도록 하는 어노테이션
import org.springframework.util.StringUtils; // 문자열 처리를 위한 유틸리티 클래스
import org.springframework.web.filter.OncePerRequestFilter; // http 요청당 한번만 실행되는 필터를 나타내는 클래스
import java.io.IOException; // 예외처리를 위한 클래스
import jakarta.servlet.FilterChain; // 필터 체인의 인터페이스로, 다음 필터를 호출하기 위해 사용
import jakarta.servlet.ServletException; // 서블릿 관련 예외처리를 위한 클래스
import jakarta.servlet.http.HttpServletRequest; // http 요청을 나타내는 클래스
import jakarta.servlet.http.HttpServletResponse; // http 응답을 나타내는 클래스
import lombok.extern.slf4j.Slf4j; // 로깅을 위한 lombok 어노테이션으로 로거 변수를 자동으로 생성

// jwt로 인증 확인
@Component // 이 클래스를 스프링의 관리 빈으로 등록, 이 클래스가 스프링에서 자동으로 감지되어 빈으로 등록됨
@Slf4j // lombok 어노테이션으로, 로깅 기능을 위한 logger 객체를 자동으로 생성해줌
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  // < 전체적 흐름 설명 >

  // 요청이 필터를 통과할때마다 "doFilterInteranl"메소드가 실행됨.
  // "Autorization" 헤더에서 JWT를 추출(이때, 만들어둔 'parseBearerToken' 메소드 사용)해, 토큰이 유효한경우 사용자 ID를 가져오고,
  // 가져온 사용자 ID 기반으로 Spring Security의 인증객체를 생성하고, 이걸 SecurityContext에 설정함.
  // 그 다음 요청을 필터 체인의 다음 필터로 전달.

  // < 필터체인의 구조 >

  // http 요청이 서버에 도착하면 spring security와 같은 프레임워크는 요청 처리전 여러개의 필터를 거치게됨.
  // 각 필터는 요청을 검사, 변경, 차단의 역할을 가지며 각 필터들이 순차적으로 요청을 처리하는 연결된 구조를 가짐
  // 필터체인은 보통 
  // 1.인증필터 (사용자 인증 정보확인), 
  // 2.권한필터 (사용자의 권한확인), 
  // 3.CORS필터 (cors를 처리해 웹어플리케이션이 다른 도메인으로 요청 보내는걸 허용할지 결정), 
  // 4.로깅필터 (요청 및 응답에 대한 로깅 처리) 로 나뉘며,
  // 자신의 역할 수행후 filterChain.doFilter(request, response)를 호출해 다음 필터로 요청을 전달함.
  
  // JWT 토큰을 생성하고 검증하는 역할을 담당하는 TokenProvider 주입
  @Autowired // 스프링의 의존성 주입(DI)를 통해 TokenProvider 빈을 자동으로 주입
  private TokenProvider tokenProvider;

  // HTTP 요청이 필터를 거칠때 실행되는 메소드 (인터페이스의 추상메소드 구현)
  @Override
  protected void doFilterInternal(
			HttpServletRequest request, 
      HttpServletResponse response, 
      FilterChain filterChain
  ) throws ServletException, IOException {
    try {
      // 요청 헤더에서 JWT 토큰을 추출
      String token = parseBearerToken(request);
      log.info("Filter is running...");

      // 토큰이 유효한지 확인 (null 아니고, "null" 문자열이 아닌지)
      if (token != null && !token.equalsIgnoreCase("null")) {

          // 토큰이 유효하면 사용자 ID를 가져옴
          String userId = tokenProvider.validateAndGetUserId(token);
          log.info("Authenticated user ID : " + userId);

          // Spring Security의 인증객체 생성 (사용자 ID만 설정, 권한은 없음)
          AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, AuthorityUtils.NO_AUTHORITIES);

          // 인증 객체에 요청 정보를 설정
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          // 새로운 SecurityContext를 생성하여 인증 정보를 설정
          SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
          securityContext.setAuthentication(authentication);
          SecurityContextHolder.setContext(securityContext);
      }
    } catch (Exception e) {
        logger.error("Could not set user authentication in security context", e);
    }

    // 다음 필터로 요청을 전달
    filterChain.doFilter(request, response);
  }

  // Authenticate 헤더에서 bearer 토큰을 추출하는 메소드
  private String parseBearerToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization"); // 요청 헤더에서 "Authorization" 값을 가져옴
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) { // "Bearer" 로 시작하는지 확인후 토큰 문자열만 반환
        return bearerToken.substring(7); // 앞의 7자리 잘라내는게 Bearer 값을 제외한 토큰 오리지널 값
    }
    return null; // 유효 토큰이 없다면 null 반환
  }
  
}
