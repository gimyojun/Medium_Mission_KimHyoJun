package com.ll.medium.domain.member.member.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MemberCreateForm {
    @Size(min =3 , max = 25)
    @NotEmpty(message = "사용자 id는 필수 항목입니다")
    private String username;
    @NotEmpty(message = "사용자 비밀번호는 필수 항목입니다")
    private String password1;
    @NotEmpty(message = "비밀번호 확인은 필수 항목입니다")
    private String password2;

}
