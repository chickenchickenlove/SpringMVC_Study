package hello.login.member;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class MemberRepositoryTest {


    private MemberRepository memberRepository = new MemberRepository();
    
    
    
    @Test
    public void memberTest(){
        Member member = new Member();
        member.setLoginId("myTest");
        member.setPassword("a");

        memberRepository.save(member);


        Optional<Member> loginId = memberRepository.findByLoginId(member.getLoginId());
        System.out.println("loginId = " + loginId);


    }
    
    
    
    
    
    
    
    
}
