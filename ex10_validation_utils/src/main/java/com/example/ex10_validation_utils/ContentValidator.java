package com.example.ex10_validation_utils;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

// Validator 클래스는 spring boot가 지원해주어서 그걸 인용해 구현
public class ContentValidator implements Validator {
  
  @Override
  public boolean supports(Class<?> arg0) {
    // ContentDTO클래스를 사용
    return ContentDTO.class.isAssignableFrom(arg0);
  }

  @Override
  public void validate(Object obj, Errors err) {

    ContentDTO dto = (ContentDTO)obj;

    // String sWriter = dto.getWriter();
    // if (sWriter == null || sWriter.trim().isEmpty()) {
    //   System.out.println("Writer is null or empty");
    //   // 에러 띄우기 (필드명, 에러코드, 에러메세지)
    //   err.rejectValue("writer", "trouble", "Writer is required");
    // }

    // String sContent = dto.getContent();
    // if (sContent == null || sContent.trim().isEmpty()) {
    //   System.out.println("Content is null or empty");
    //   err.rejectValue("Content", "trouble", "Content is required");
    // }

    // 위와동일 하지만 다른 방식
    ValidationUtils.rejectIfEmptyOrWhitespace(err, "writer", "Writer is required");
    ValidationUtils.rejectIfEmptyOrWhitespace(err, "content", "Content is required");

    String sWriter = dto.getWriter();
    if (sWriter.length() < 3) {
      err.rejectValue("writer", "writer.tooShort", "Writer is too short");
    }
  }
}
