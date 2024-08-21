package com.example.ex30_jpa_qnaboard_rest_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ex30_jpa_qnaboard_rest_api.answer.Answer;
import com.example.ex30_jpa_qnaboard_rest_api.answer.AnswerRepository;
import com.example.ex30_jpa_qnaboard_rest_api.question.Question;
import com.example.ex30_jpa_qnaboard_rest_api.question.QuestionRepository;
import com.example.ex30_jpa_qnaboard_rest_api.question.QuestionService;

import jakarta.transaction.Transactional;

// spring boot 애플리케이션 컨텍스트가 정상적으로 로드되는지 확인하는 간단 테스트
@SpringBootTest
class Ex30JpaQnaboardRestApiApplicationTests {

	@Test
	void contextLoads() {
		System.out.println(">>> testing");
	}

	@Autowired
  private QuestionRepository questionRepository; // Question 엔티티를 다루는 JPA 레파지토리

  @Test
  void testJpaQuestionSave() {
    // 첫번째 질문 생성 및 저장
    Question q1 = new Question();
    q1.setSubject("sbb가 무엇인가요?");
    q1.setContent("sbb에 대해서 알고 싶습니다.");
    q1.setCreateDate(LocalDateTime.now());
    this.questionRepository.save(q1);  // 첫번째 질문 저장

    // 두번째 질문 생성 및 저장
    Question q2 = new Question();
    q2.setSubject("스프링부트 모델 질문입니다.");
    q2.setContent("id는 자동으로 생성되나요?");
    q2.setCreateDate(LocalDateTime.now());
    this.questionRepository.save(q2);  // 두번째 질문 저장
  }

  @Test
  void testJpaQuestionFindAll() {
    // 모든 질문을 조회하고 그 크기를 검증
    List<Question> all = this.questionRepository.findAll();
    assertEquals(2, all.size());

    // 첫 번째 질문의 제목이 예상대로인지 검증
    Question q = all.get(0);
    assertEquals("sbb가 무엇인가요?", q.getSubject());
  }

  @Test
  void testJpaQuestionFindById() {
    // ID가 2인 질문을 조회하고, 그 제목이 예상대로인지 검증
    Optional<Question> oq = this.questionRepository.findById(2);
    
    System.out.println(oq.get().getSubject());
    
    if(oq.isPresent()) {
        Question q = oq.get();
        assertEquals("스프링부트 모델 질문입니다.", q.getSubject());
    }
  }

  @Test
  void testJpaQuestionFindBySubject() {
    // 제목이 "sbb가 무엇인가요?"인 질문을 조회하고, 그 ID가 예상대로인지 검증
    Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
    assertEquals(1, q.getId());
  }

  @Test
  void testJpaQuestionFindBySubjectAndContent() {
     // 제목과 내용으로 질문을 조회하고, 그 ID가 예상대로인지 검증
    Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
    assertEquals(1, q.getId());
  }

  @Test
  void testJpaQuestionFindBySubjectLike() {
    // 제목이 "sbb"로 시작하는 질문을 조회하고, 그 첫 번째 질문의 제목이 예상대로인지 검증
    List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
    Question q = qList.get(0);
    assertEquals("sbb가 무엇인가요?", q.getSubject());
  }

  @Test
  void testJpaQuestionFindByIdSave() {
    // ID가 1인 질문을 조회한 후, 그 제목을 수정하고 다시 저장
    Optional<Question> oq = this.questionRepository.findById(1);
    assertTrue(oq.isPresent());
    Question q = oq.get();
    q.setSubject("수정된 제목");
    this.questionRepository.save(q);
  }

  @Test
  void testJpaQuestionFindByIdDelete() {
    // 현재 질문이 2개인지 검증 후, ID가 1인 질문을 삭제하고, 그 후 남은 질문이 1개인지 검증
    assertEquals(2, this.questionRepository.count());
    Optional<Question> oq = this.questionRepository.findById(1);
    assertTrue(oq.isPresent());
    Question q = oq.get();
    this.questionRepository.delete(q);
    assertEquals(1, this.questionRepository.count());
  }

  @Autowired
  private AnswerRepository answerRepository; // Answer 엔티티를 다루는 JPA 레포지토리

  @Test
  void testJpaAnswerFindByIdSave() {
    // ID가 2인 질문을 조회한 후, 그 질문에 대한 답변을 생성 및 저장
    Optional<Question> oq = this.questionRepository.findById(2);
    assertTrue(oq.isPresent());
    Question q = oq.get();

    Answer a = new Answer();
    a.setContent("네 자동으로 생성됩니다.");
    a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
    a.setCreateDate(LocalDateTime.now());
    this.answerRepository.save(a);
  }

  @Test
  void testJpaAnswerFindById() {
    // ID가 1인 답변을 조회하고, 그 답변이 연결된 질문의 ID가 예상대로인지 검증
    Optional<Answer> oa = this.answerRepository.findById(1);
    assertTrue(oa.isPresent());
    Answer a = oa.get();
    assertEquals(2, a.getQuestion().getId());
  }

  @Transactional // 메소드 또는 클래스에 적용해 해당 범위에서 수행되는 DB작업의 트랙잭션을 관리하는 역할을 함.
  @Test
  void testJpaQuestionFindByIdAnswer() {
    // ID가 2인 질문을 조회한 후, 그 질문에 연결된 답변 리스트의 크기와 내용이 예상대로인지 검증
    Optional<Question> oq = this.questionRepository.findById(2);
    assertTrue(oq.isPresent());
    Question q = oq.get();

    List<Answer> answerList = q.getAnswerList();

    assertEquals(1, answerList.size());
    assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
  }

  @Autowired
  private QuestionService questionService;

  @Test
  void testJpaAddDummyData() {
      for (int i = 1; i <= 300; i++) {
          String subject = String.format("테스트 데이터입니다:[%03d]", i);
          String content = "내용무";
          this.questionService.create(subject, content);
      }
  }
}
