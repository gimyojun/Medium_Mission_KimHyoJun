package com.ll.medium.domain.quesstion.question;

import com.ll.medium.domain.question.question.repository.QuestionRepository;
import com.ll.medium.domain.question.question.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
public class QuestionTests {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionService questionService;
//
//    @Test
//    @DisplayName("t1")
//    void t1(){
//        Question q1 = new Question();
//        q1.setSubject("jpa가 뭐죠");
//        q1.setContent("orm인가요?");
//        q1.setCreateDate(LocalDateTime.now());
//        this.questionRepository.save(q1);
//
//        Question q2 = new Question();
//        q2.setSubject("스프링이 뭐죠");
//        q2.setContent("프레임워크인가요?");
//        q2.setCreateDate(LocalDateTime.now());
//        this.questionRepository.save(q2);
//    }
//    @Test
//    void testJpa() {
//        List<Question> all = this.questionRepository.findAll();
//        assertEquals(2, all.size());
//
//        Question q = all.get(0);
//        assertEquals("jpa가 뭐죠", q.getSubject());
//    }
//    @Test
//    void testJpa1() {
//        Optional<Question> oq = this.questionRepository.findById(1);
//        if(oq.isPresent()) {
//            Question q = oq.get();
//            assertEquals("jpa가 뭐죠", q.getSubject());
//        }
//    }
//    @Test
//    void testJpa2() {
//        Question q = this.questionRepository.findBySubject("jpa가 뭐죠");
//        assertEquals(1, q.getId());
//    }
    @Test
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용무";
            this.questionService.saveQuestion(content,subject);
        }
    }

}
