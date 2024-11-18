package com.example.ex12_valid_annotation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContentDTO {
    private int id;
    
    @NotNull(message = "writer is null.")
    @NotEmpty(message = "writer is empty.")
    @Size(min = 3, max = 10, message = "{writer.tooShort}")
    private String writer;

    @NotNull(message = "content is null.")
    @NotEmpty(message = "content is empty.")
    private String content;
}
