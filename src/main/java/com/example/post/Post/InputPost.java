package com.example.post.Post;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InputPost {
    @NotBlank(message = "제목을 입력하세요")
    private String title;
    @NotBlank(message = "내용을 입력하세요")
    private String content;
}
