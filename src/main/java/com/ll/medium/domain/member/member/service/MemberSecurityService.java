package com.ll.medium.domain.member.member.service;

import com.ll.medium.domain.member.member.entity.MemberEntity;
import com.ll.medium.domain.member.member.repository.MemberRepository;
import com.ll.medium.domain.member.member.role.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberSecurityService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    //UserDetailsService를 구현하면 해당 메서드를 구현하도록 강제한다
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberEntity> memberObject = memberRepository.findByusername(username);
        //member없는 경우
        if(memberObject.isEmpty()){
            throw new UsernameNotFoundException("등록되지 않은 사용자입니다.");
        }
        //있는경우. 로그인시켜야지, 권한 부여
        MemberEntity member = memberObject.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        if("amdin".equals(username)){
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        }else{
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        //스프링 시큐리티는 loadUserByUsername 메서드에 의해 리턴된 User 객체의 비밀번호가
        //화면으로부터 입력 받은 비밀번호와 일치하는지를 검사하는 로직을 내부적으로 가지고 있다.
        return new User(member.getUsername(),member.getPassword(),authorities);
    }
}
