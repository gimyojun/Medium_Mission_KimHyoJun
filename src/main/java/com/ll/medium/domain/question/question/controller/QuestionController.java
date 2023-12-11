package com.ll.medium.domain.question.question.controller;

import com.ll.medium.domain.answer.answer.form.AnswerCreateForm;
import com.ll.medium.domain.question.question.entity.Question;
import com.ll.medium.domain.question.question.form.QuestionCreateForm;
import com.ll.medium.domain.question.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging",paging);
        return "domain/question/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id, AnswerCreateForm answerCreateForm){
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "domain/question/detail";
    }
    @GetMapping("/create")
    public String create(QuestionCreateForm questionCreateForm) {
        return "domain/question/create";
    }

    @PostMapping("/create")
    public String createQuestion(@Valid QuestionCreateForm questionCreateForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "domain/question/create";
        }

        this.questionService.saveQuestion(questionCreateForm.getContent(), questionCreateForm.getSubject());
        return "redirect:/question/list";
    }

}
