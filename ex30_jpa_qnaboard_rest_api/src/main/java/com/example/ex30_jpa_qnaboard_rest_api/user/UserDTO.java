package com.example.ex30_jpa_qnaboard_rest_api.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // 생성자 없이 호출하는경우
@AllArgsConstructor // 모든 생성자를 호출하는경우
public class UserDTO {

    private Long id;

    private String username;

    private String password;

    private String email;
}