package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {
    // 일단은 저장소가 선정되지 않은 상태라 store가 디비라고 생각하면 된다.
    public static Map<Long, Member> store = new HashMap<>();
    public static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findALl() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

}
