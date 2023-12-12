package com.ll.medium.domain.member.member.role.service;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.repository.MemberRepository;
import com.ll.medium.global.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(String usernmae, String password){
        Member user = new Member();
        user.setUsername(usernmae);

        user.setPassword(passwordEncoder.encode(password));
        memberRepository.save(user);
        return user;
    }

    public Member getMember(String username){
        Optional<Member> user = memberRepository.findByUsername(username);
        if(user.isPresent()){
            return user.get();
        }else{
            throw new DataNotFoundException("사용자를 찾을수 없습니다.");
        }

    }


}
