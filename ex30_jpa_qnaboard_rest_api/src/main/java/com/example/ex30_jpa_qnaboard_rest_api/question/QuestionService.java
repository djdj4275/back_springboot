package com.example.ex30_jpa_qnaboard_rest_api.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.ex30_jpa_qnaboard_rest_api.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

  private final QuestionRepository questionRepository;

  // public List<Question> getList() {
  //     return this.questionRepository.findAll();
  // }

  // 페이징 처리해서 데이터를 10개 간격으로 파라미터로 받은 페이지 값에 맞는 10개 값을 찾아서 리턴
  public Page<Question> getList(int page) {
    Pageable pageable = PageRequest.of(page, 10);
    return this.questionRepository.findAll(pageable);
  }

  public Question getQuestion(Integer id) {  
    Optional<Question> question = this.questionRepository.findById(id);
    if (question.isPresent()) {
        return question.get();
    } else {
        throw new DataNotFoundException("question not found");
    }
  }

  public void create(String subject, String content) {
      Question q = new Question();
      q.setSubject(subject);
      q.setContent(content);
      q.setCreateDate(LocalDateTime.now());
      this.questionRepository.save(q);
  }
}