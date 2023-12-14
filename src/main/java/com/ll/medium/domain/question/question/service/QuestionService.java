package com.ll.medium.domain.question.question.service;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.question.question.entity.Question;
import com.ll.medium.domain.question.question.repository.QuestionRepository;
import com.ll.medium.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;



    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isPresent()){
            return question.get();
        }else {
            throw new DataNotFoundException(" No Question Found with Id: ");
        }
    }
    public void saveQuestion(String content, String subject, boolean published, Member member){
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        question.setAuthor(member);
        question.setPublished(published);
        this.questionRepository.save(question);
    }
    public Page<Question> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
//        return this.questionRepository.findAll(pageable);
        return this.questionRepository.findByPublishedTrue(pageable);

    }

    public List<Question> getHomeList(){
        return questionRepository.findTop30ByOrderByCreateDateDesc();
    }

    public void modifyQuestion(String content, String subject, Question question){

        question.setSubject(subject);
        question.setContent(content);
        question.setUpdateDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void deleteQuestion(int id){
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            this.questionRepository.deleteById(id);
        } else {
            throw new DataNotFoundException("존재하지 않는 글입니다");
        }
    }

    public void likeQuestion(Integer id, Member member) {
        Question question = getQuestion(id);
        question.getVoter().add(member);
        questionRepository.save(question);
    }

    public List<Question> getUserQuestionlist(Member member){
        return questionRepository.findByAuthorAndPublishedTrue(member);
    }
}
