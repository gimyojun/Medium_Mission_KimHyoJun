package com.ll.medium.domain.member.member.controller;

import com.ll.medium.domain.member.member.form.MemberCreateForm;
import com.ll.medium.domain.member.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String signup(MemberCreateForm memberCreateForm){
        return "domain/member/join";
    }

    @PostMapping("/join")
    public String signup(@Valid MemberCreateForm memberCreateForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "domain/member/join";
        }

        if (!memberCreateForm.getPassword1().equals(memberCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "domain/member/join";
        }
        memberService.create(memberCreateForm.getUsername(),memberCreateForm.getPassword1());

        return "redirect:/";
    }


}
