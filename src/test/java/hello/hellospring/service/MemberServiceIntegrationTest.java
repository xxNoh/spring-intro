package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    // test case 는 그냥 필드기반으로 autowired 받아도 크게 상관없음 편한걸로 해라.
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;


    /**
     * 테스트는 예외 테스트가 정상 테스트보다 더 중요하다.
     */
    @Test
    @Commit
    void join() {
        //given
        Member member = new Member();
        member.setName("spring4");
        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        Member member2 = new Member();
        member2.setName("spring1");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        //then
        assertThat(e.getMessage()).isEqualTo("회원 아이디 중복 발생");
    }

    @Test
    void findMembers() {
        // given
            Member member = new Member();
            member.setName("spring3");
        // when
            Member result = memberRepository.findByName(member.getName()).get();
        // then
        assertThat(result.getName()).isEqualTo("spring4");
    }

    @Test
    void findOne() {

    }
}