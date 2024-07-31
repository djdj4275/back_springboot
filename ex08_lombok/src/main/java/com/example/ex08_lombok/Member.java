package com.example.ex08_lombok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Getter
// @Setter
// @AllArgsConstructor // 매개변수 받는 생성자
// @NoArgsConstructor // 매개변수가 없는 default 생성자
@Data // 모두 다 포함한것
public class Member {
  private String id;
  private String name;
}
