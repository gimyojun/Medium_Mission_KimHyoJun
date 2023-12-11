package com.ll.medium.domain.answer.answer.controller;

import com.ll.medium.domain.answer.answer.service.AnswerService;
import com.ll.medium.domain.question.question.entity.Question;
import com.ll.medium.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable Integer id, @RequestParam(value ="content") String content) {

        Question question = this.questionService.getQuestion(id);
        this.answerService.create(question, content );
        return String.format("redirect:/question/detail/%s", id);
    }

}
