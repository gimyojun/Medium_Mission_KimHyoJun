package com.ll.medium.domain.question.question.controller;

import com.ll.medium.domain.question.question.entity.Question;
import com.ll.medium.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Question> list = this.questionService.getList();
        model.addAttribute("questionList",list);
        return "domain/question/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id){
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "domain/question/detail";
    }


}
