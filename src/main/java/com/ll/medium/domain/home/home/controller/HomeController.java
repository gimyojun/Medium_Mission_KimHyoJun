package com.ll.medium.domain.home.home.controller;

import com.ll.medium.domain.question.question.entity.Question;
import com.ll.medium.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    private final QuestionService questionService;
    @GetMapping("")
    public String ShowMain(Model model){
        List<Question> list = this.questionService.getHomeList();
        model.addAttribute("list", list);
        return "domain/home/main";
    }


}
