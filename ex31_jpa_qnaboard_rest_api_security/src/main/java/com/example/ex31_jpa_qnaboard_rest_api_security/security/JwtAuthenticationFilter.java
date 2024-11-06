package com.example.ex31_jpa_qnaboard_rest_api_security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component // 이 클래스를 bean으로 관리 할수있게 컴포넌트로 등록
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // tokenProvider 빈 주입: jwt를 검증하고, 사용자 id를 추출하는 메소를 제공해줌
    @Autowired
    private TokenProvider tokenProvider;

    // oncePerRequestFilter 의 추상메소드 doFilterInternal을 구현해 jwt를 검증하고 사용자 인증
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // parseBearerToken 메소드를 통해서 요청에서 bearer 토큰을 추출함
            String token = parseBearerToken(request);
            log.info("Filter is running...");

            // 토큰이 유효한경우
            if (token != null && !token.equalsIgnoreCase("null")) {
                // TokenProvider를 사용해서 토큰에서 사용자 id를 추출함
                String userId = tokenProvider.validateAndGetUserId(token);
                log.info("Authenticated user ID : " + userId);

                // 인증객체를 생성하여 securityContext에 설정할 준비를 함.
                // 여기서 권한 정보를 따로 지정하진않아 NO_AUTHORITIES로 설정
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null,
                        AuthorityUtils.NO_AUTHORITIES);
                // 요청의 세부정보를 인증객체에 설정
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 새로운 securityContext를 생성하고, 인증객체를 설정한 후 securityContextHolder에 추가
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

    // parseBearerToken: 요청 헤더에서 authorization 값을 읽어 bearer 토큰을 파싱함.
    private String parseBearerToken(HttpServletRequest request) {
        // authorization 헤더의 값을 추출
        String bearerToken = request.getHeader("Authorization");

        // bearer로 시작하는 경우에만 토큰을 반환, 그렇지않으면 null 반환
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
