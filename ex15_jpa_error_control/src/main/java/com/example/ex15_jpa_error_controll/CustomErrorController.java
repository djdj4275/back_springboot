package com.example.ex15_jpa_error_controll;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller
@Slf4j // 로그 쓰기위한 어노테이션
public class CustomErrorController implements ErrorController {

  @RequestMapping("/error")
  public String handlerError(HttpServletRequest httpServletRequest) {
    Integer statuscode = (Integer)httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    
    log.error("error code : " + statuscode);

    if (statuscode != null) {
      if (statuscode == 404) {
        return "redirect:/not-found";
      } else if (statuscode == 500) {
        return "redirect:/server-error";
      }
    }

    return "redirect:/";
  }

  @RequestMapping("/not-found")
  @ResponseStatus(HttpStatus.NOT_FOUND) // 에러페이지에 붙여주는 어노테이션
  public String notFound() {
    return "not-found";
  }

  @RequestMapping("/server-error")
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 에러페이지에 붙여주는 어노테이션
  public String serverError() {
    return "server-error";
  }

  // 서버에러 확인을 위한 의도적 서버에러 페이지
  @GetMapping("/trigger-500")
  public String triggerServerError() {
    throw new RuntimeException("500 Internal Server Error 발생");
  }
} 
