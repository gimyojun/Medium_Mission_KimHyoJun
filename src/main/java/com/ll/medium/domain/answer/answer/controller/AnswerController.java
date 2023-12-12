package com.ll.medium.domain.answer.answer.controller;

import com.ll.medium.domain.answer.answer.form.AnswerCreateForm;
import com.ll.medium.domain.answer.answer.service.AnswerService;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.role.service.MemberService;
import com.ll.medium.domain.question.question.entity.Question;
import com.ll.medium.domain.question.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final MemberService memberService;


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerCreateForm answerCreateForm, BindingResult bindingResult, Principal principal) {
        Question question = this.questionService.getQuestion(id);

        if(bindingResult.hasErrors()){
            model.addAttribute("question",question);
            return "domain/question/detail";
        }
        Member username = this.memberService.getMember(principal.getName());
        this.answerService.create(question,answerCreateForm.getContent(), username);
        return String.format("redirect:/question/detail/%s", id);
    }

}
