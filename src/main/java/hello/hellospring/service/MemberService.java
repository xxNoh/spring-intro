package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * service 는 비즈니스적인 용어를 선택해야 한다.
 * repository 는 그냥 기계적인? findAll(), findById()
 * ⭐️ memberService 처럼 외부에서 memberRepository 를 넣어주는 것을 DI 라고 한다.
 */
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     * 요구사항 -
     *  같은 이름이 존재하면 안된다.
     *  command + opt + v == return 값 반환해주는 개꿀 단축키
     *  ctrl + t == 리팩토링 관련 메소드가 많이 나온다. ⭐️
     */
    public Long join(Member member) {
        vaildataDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void vaildataDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(name -> {
                    throw new IllegalStateException("회원 아이디 중복 발생");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findALl();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
