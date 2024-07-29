package spring_example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

  // RequestMapping시에는 무조건 반환값이 있어야함
  @RequestMapping("/hello") // 해당 도메인으로 접근시 코드 적용"
  @ResponseBody // 리턴으로 응답한값을 브라우저 화면상에 띄움(body 태그로 응답함)
  public String hello() {
    return "Hello spring boot world!";
  }
}
