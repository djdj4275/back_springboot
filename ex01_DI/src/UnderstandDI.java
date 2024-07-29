class Member {
  String name;
  private Member() {}
}

public class UnderstandDI { // Member 클래스를 의존함
  public static void main(String[] args) {
    
  }

  // 객체를 직접 생성 (생성자가 private일때 문제발생) = 강한결합
  // public static void memberUse1() { 
  //   Member m1 = new Member();
  // }

  // 객체를 주입 = 약한결합
  public static void memberUse2(Member member) { 
    Member m2 = member;
  }
}
