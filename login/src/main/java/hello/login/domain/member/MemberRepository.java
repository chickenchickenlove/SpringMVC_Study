package hello.login.domain.member;


// 저장, id로 찾기, login Id로 찾기, 전체 회원 불러오기, 스토어 지우기


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.*;


@Slf4j
@Repository
public class MemberRepository {

    private static final Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;




    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member = {}", member);
        store.put(member.getId(), member);
        return member;

    }

    public Member findById(Long id) {
        return store.get(id);
    }


    public List<Member> findAll(){
        return new ArrayList<Member>(store.values());
    }

    public Optional<Member> findByLoginId(String loginId) {
        Optional<Member> findMember = findAll().stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .findAny();

        return findMember;
    }


    public void clearStore(){
        store.clear();
    }






}
