package com.example.ex31_jpa_qnaboard_rest_api_security.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex31_jpa_qnaboard_rest_api_security.security.TokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 비밀번호 암호화

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            if(userDTO == null || userDTO.getPassword() == null) {
                throw new RuntimeException("Invalid Password value.");
            }

            // UserEntity user = new UserEntity();
            // user.setUsername(userDTO.getUsername());
            // user.setUsername(userDTO.getPassword());
            // user.setUsername(userDTO.getEmail());

            UserEntity user = UserEntity.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword())) // 비밀번호 암호화해서 저장
                .email(userDTO.getEmail()).build();

            UserEntity registeredUser = userService.create(user);

            UserDTO responseUserDTO = UserDTO.builder()
                .id(registeredUser.getId())
                .username(registeredUser.getUsername())
                .email(registeredUser.getEmail())
                .build();
            
            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User creation failed");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        try {
            UserEntity user = userService.getByCredentials(userDTO.getEmail(), userDTO.getPassword(), passwordEncoder);

            if (user != null) {
                final String token = tokenProvider.create(user); // 로그인전에 먼저 토큰발급

                final UserDTO responseDTO = UserDTO.builder()
                    .username(user.getUsername())
                    .id(user.getId())
                    .token(token) // 토큰도 추가
                    .email(user.getEmail())
                    .build();

                return ResponseEntity.ok().body(responseDTO);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured while processing the request");
        }
    }
}
