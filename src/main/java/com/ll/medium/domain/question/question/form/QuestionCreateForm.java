package com.ll.medium.domain.question.question.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionCreateForm {

    @NotEmpty(message = "제목을 입력해주세요")
    @Size(max=200)
    private String subject;

    @NotEmpty(message = "내용을 입력해주세요")
    private String content;

    private boolean published;  // isPublished 필드 추가

}
