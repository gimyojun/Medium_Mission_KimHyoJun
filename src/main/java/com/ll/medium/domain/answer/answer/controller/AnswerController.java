package com.ll.medium.domain.answer.answer.controller;

import com.ll.medium.domain.answer.answer.form.AnswerCreateForm;
import com.ll.medium.domain.answer.answer.service.AnswerService;
import com.ll.medium.domain.question.question.entity.Question;
import com.ll.medium.domain.question.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerCreateForm answerCreateForm, BindingResult bindingResult) {
        Question question = this.questionService.getQuestion(id);

        if(bindingResult.hasErrors()){
            model.addAttribute("question",question);
            return "domain/question/detail";
        }

        this.answerService.create(question,answerCreateForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

}
