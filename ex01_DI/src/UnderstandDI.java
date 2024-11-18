class Member {
    String name;

    Member() {}
    // private Member() {}
}

public class UnderstandDI {
    public static void main(String[] args) {
        
    }

    public static void memberUse1() {
        Member m1 = new Member(); // 강한 결합
    }

    public static void memberUse2(Member member) {
        Member m2 = member; // 약한 결합
    }
}
