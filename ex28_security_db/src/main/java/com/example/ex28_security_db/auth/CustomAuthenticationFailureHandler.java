package com.example.ex28_security_db.auth;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// AuthenticationFailureHandler는 인증이 실패했을때 동작을 정의하는 메소드를 포함하는 인터페이스
@Configuration // 설정파일이라는 어노테이션
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{

  // onAuthenticationFailure 메소드는 인증이 실패시에 호출됨.

  // HttpServletRequest 는 사용자의 요청을 나타내는 객체로 요청의 세부정보가 들어있음
  // HttpServletResponse 는 응답을 나타내는 객체로 응답의 세부사항을 설정가능
  // AuthenticationException 는 인증 실패시 발생하는 예외객체로 어떤 이유로 인증이 실패했는지 파악가능.
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) 
  throws IOException, ServletException {

    String loginId = request.getParameter("username");
    String errorMsg = "";

    if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
      errorMsg = "아이디나 비밀번호가 맞지 않습니다. 다시 확인 해주세요.";
    }

    request.setAttribute("username", loginId);
    request.setAttribute("error_message", errorMsg);
    request.setAttribute("login_failure", true);

    // 한마디로 인증실패시 loginId와 에러메세지, fail여부를 request에 넣어 다시 로그인페이지로 전달하여 로그인 실패원인 파악하도록함.
    request.getRequestDispatcher("/login-form").forward(request, response);
    
  }
}
