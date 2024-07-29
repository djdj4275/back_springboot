package spring_example.demo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter // lombok 라이브러리로 getter setter 바로적용
@Setter
@RequiredArgsConstructor // 매개변수를 넣은 생성자 또한 lombok 라이브러리로 생성
public class HelloLombok {
  // @RequiredArgsConstructor 사용시에 매개변수로 받을 값들은 final을 붙여줘야함.
  // lombok이 애노테이션을 사용해 자동으로 생성자 생성시에 final필드와 @NonNull 필드를 기반으로 생성자 생성하기 때문
  private final String hello; 
  private final int lombok;

  public static void main(String[] args) {
    HelloLombok helloLombok = new HelloLombok("김사남", 96);
    // helloLombok.setHello("김일남");
    // helloLombok.setLombok(99);

    System.out.println(helloLombok.getHello());
    System.out.println(helloLombok.getLombok());
  }
}
