package com.ll.medium.domain.answer.answer.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerCreateForm {
    @NotEmpty(message = "내용을 입력해주세요")
    private String content;

}
