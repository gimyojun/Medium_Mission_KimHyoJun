package com.ll.medium.domain.answer.answer.controller;

import com.ll.medium.domain.answer.answer.entity.Answer;
import com.ll.medium.domain.answer.answer.form.AnswerCreateForm;
import com.ll.medium.domain.answer.answer.service.AnswerService;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.role.service.MemberService;
import com.ll.medium.domain.question.question.entity.Question;
import com.ll.medium.domain.question.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final MemberService memberService;


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/write")
    public String writeAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerCreateForm answerCreateForm, BindingResult bindingResult, Principal principal) {
        Question question = this.questionService.getQuestion(id);

        if(bindingResult.hasErrors()){
            model.addAttribute("question",question);
            return "domain/post/detail";
        }
        Member username = this.memberService.getMember(principal.getName());
        this.answerService.write(question,answerCreateForm.getContent(), username);
        return String.format("redirect:/post/%s", id);
    }

    @GetMapping("/{id}/modify")
    public String modifyAnswer(@PathVariable Integer id, AnswerCreateForm answerCreateForm, Principal principal){
        Answer answer = answerService.getAnswer(id);
        if(!answer.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
        }
        String a = answerCreateForm.getContent();
        System.out.println(a);
        answerCreateForm.setContent(answer.getContent());
        return "domain/post/answerModify";

    }
    @PostMapping("/{id}/modify")
    public String modifyAnswer(@PathVariable Integer id, @Valid AnswerCreateForm answerCreateForm, BindingResult bindingResult,Principal principal){
        if (bindingResult.hasErrors()) {
            return "domain/post/answerModify";
        }
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answerService.modify(answer, answerCreateForm.getContent());
        return String.format("redirect:/post/%s",answer.getQuestion().getId());
    }
    @GetMapping("/{id}/delete")
    public String deleteAnswer(@PathVariable Integer id, Principal principal) {
        Answer answer = this.answerService.getAnswer(id);
        if(!answer.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다");
        }
        answerService.delete(id);
        return String.format("redirect:/post/%s",answer.getQuestion().getId());
    }

}
