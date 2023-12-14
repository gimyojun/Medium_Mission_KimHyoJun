package com.ll.medium.domain.question.question.repository;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.question.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String s);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);

    Page<Question> findAll(Pageable pageable);

    List<Question> findTop30ByOrderByCreateDateDesc();

    //페이징 사용하고 전체 글 조회하는데 publised true인것만
    Page<Question> findByPublishedTrue(Pageable pageable);

    //나의 글 보기
    List<Question> findByAuthor(Member author);

    //author로 글찾고 publised true인것만
    List<Question> findByAuthorAndPublishedTrue(Member author);
}
