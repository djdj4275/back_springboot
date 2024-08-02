package com.example.ex12_valid_anotation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContentDTO {
  private int id;

  // 어노테이션으로 이 값이 null이나 공백값으로 오면 message를 리턴함 (유효성검사)
  // 또한 길이도 체크
  @NotNull(message = "writer is null.")
  @NotEmpty(message = "writer is empty.")
  @Size(min = 3, max = 10, message = "{writer.tooShort}") // 메세지값은 message.properties에 정의되어있음
  private String writer;

  @NotNull(message = "content is null.")
  @NotEmpty(message = "content is empty.")
  private String content;
}
