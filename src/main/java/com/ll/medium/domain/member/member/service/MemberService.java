package com.ll.medium.domain.member.member.service;

import com.ll.medium.domain.member.member.entity.MemberEntity;
import com.ll.medium.domain.member.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberEntity create(String usernmae, String password){
        MemberEntity user = new MemberEntity();
        user.setUsername(usernmae);

        user.setPassword(passwordEncoder.encode(password));
        memberRepository.save(user);
        return user;
    }


}
