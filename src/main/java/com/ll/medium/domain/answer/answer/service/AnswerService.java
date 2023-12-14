package com.ll.medium.domain.answer.answer.service;

import com.ll.medium.domain.answer.answer.entity.Answer;
import com.ll.medium.domain.answer.answer.repository.AnswerRepository;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.question.question.entity.Question;
import com.ll.medium.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;


    public void write(Question question, String content, Member author){
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        answer.setCreateDate(LocalDateTime.now());
        answer.setAuthor(author);
        this.answerRepository.save(answer);
    }
    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if(answer.isPresent()){
            return answer.get();
        }else {
            throw new DataNotFoundException(" No Answer Found with Id: ");
        }
    }
    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setUpdateDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }
    public void delete(Integer id) {
        this.answerRepository.deleteById(id);
    }
}
