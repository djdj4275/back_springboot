package com.example.ex30_jpa_qnaboard_rest_api.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question") // 프리픽스
@RequiredArgsConstructor // lombok에서 지원하는 어노테이션으로 클래스 내의 final 필드나 @NonNull이 붙은 필드에 대해 생성자를 자동으로 생성해줌
@RestController // restController는 @Controller와 @ResponseBody가 결합된 형태 
// (RESTFUL API를 구현하기위해 사용됨) -> 클래스의 메소드 반환값이 뷰 이름이 아닌 데이터 그 자체로 클라이언트에게 전송됨. (뷰를 렌더링 하지않고 메소드 반환값을 http 응답 바디에 그대로 매핑)
public class QuestionController {

  private final QuestionService questionService;
  
  @GetMapping("/list")
  public Page<Question> list(@RequestParam(value="page", defaultValue="0") int page) { // page 파라미터값을 안받으면 기본값을 0으로 함
    Page<Question> paging = this.questionService.getList(page);
    return paging;
  }

  @GetMapping(value = "/detail/{id}")
  public Question detail(@PathVariable("id") Integer id) {
    Question question = this.questionService.getQuestion(id);
    return question;
  }

  @PostMapping("/create")
  public ResponseEntity<String> questionCreate(@Valid @RequestBody QuestionDTO questionDTO, BindingResult bindingResult) {

    // 유효성 검사 실패시 에러 메세지 반환
    if (bindingResult.hasErrors()) {
      StringBuilder errorMsg = new StringBuilder();
      bindingResult.getFieldErrors().forEach(e -> errorMsg.append(e.getDefaultMessage()).append("; "));
      return new ResponseEntity<>(errorMsg.toString(), HttpStatus.BAD_REQUEST);
    }

    try {
      this.questionService.create(questionDTO.getSubject(), questionDTO.getContent());
      return new ResponseEntity<String>("Question created successfully", HttpStatus.CREATED); 
    } catch (Exception e) {
      return new ResponseEntity<String>("Failed create question", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
