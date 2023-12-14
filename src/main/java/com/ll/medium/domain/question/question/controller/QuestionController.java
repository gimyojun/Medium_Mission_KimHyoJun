package com.ll.medium.domain.question.question.controller;

import com.ll.medium.domain.answer.answer.form.AnswerCreateForm;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.role.service.MemberService;
import com.ll.medium.domain.question.question.entity.Question;
import com.ll.medium.domain.question.question.form.QuestionCreateForm;
import com.ll.medium.domain.question.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class QuestionController {

    private final QuestionService questionService;
    private final MemberService memberService;
    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging",paging);
        return "domain/post/list";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable Integer id, AnswerCreateForm answerCreateForm){
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "domain/post/detail";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String create(QuestionCreateForm questionCreateForm) {
        return "domain/post/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String createQuestion(@Valid QuestionCreateForm questionCreateForm, BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()) {
            return "domain/post/write";
        }
        Member member =memberService.getMember(principal.getName());
        this.questionService.saveQuestion(questionCreateForm.getContent(), questionCreateForm.getSubject(),questionCreateForm.isPublished(),member);
        return "redirect:/post/list";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String modifyQuestion(@PathVariable Integer id, QuestionCreateForm questionCreateForm, Principal principal) {
        Question question = questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
        }

        questionCreateForm.setSubject(question.getSubject());
        questionCreateForm.setContent(question.getContent());
        return "domain/post/write";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/modify")
    public String modifyQuestion(@PathVariable Integer id, @Valid QuestionCreateForm questionCreateForm, BindingResult bindingResult,Principal principal) {
        Question question = questionService.getQuestion(id);
        if(bindingResult.hasErrors()){
            return "domain/post/write";
        }
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionService.modifyQuestion(questionCreateForm.getContent(), questionCreateForm.getSubject(), question);


        return String.format("redirect:/post/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/delete")
    public String deleteQuestion(@PathVariable Integer id,Principal principal) {
        Question question = questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다");
        }
        questionService.deleteQuestion(id);
        return "redirect:/post/list";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/like")
    public String likeQuestion(@PathVariable Integer id, Principal principal) {
        Member member = memberService.getMember(principal.getName());
        questionService.likeQuestion(id, member);
        return String.format("redirect:/post/%s", id);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myList")
    public String ShowMyList(Model model, Principal principal){
        Member member = memberService.getMember(principal.getName());
        List<Question> list = this.questionService.getUserQuestionlist(member);
        model.addAttribute("list", list);
        return "domain/post/userQuestionList";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/b/{username}")
    public String ShowUserQuestionList(Model model, @PathVariable String username){
        Member member = memberService.getMember(username);
        List<Question> list = this.questionService.getUserQuestionlist(member);
        model.addAttribute("list", list);
        return "domain/post/userQuestionList";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/b/{username}/{id}")
    public String ShowUserQuestionListDetail(Model model, @PathVariable String username, @PathVariable Integer id,AnswerCreateForm answerCreateForm){
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "domain/post/userQuestionDetail";
    }
}
