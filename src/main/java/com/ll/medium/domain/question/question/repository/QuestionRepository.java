package com.ll.medium.domain.question.question.repository;

import com.ll.medium.domain.question.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String s);
}
