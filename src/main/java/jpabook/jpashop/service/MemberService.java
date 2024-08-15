package jpabook.jpashop.service;

import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;
    /*
    @RequiredArgsConstructor 랑 똑같음 (lombok 기능)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    */


    //회원 가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDuplicateMember(Member member) {
        List<Member> findmembers = memberRepository.findByName(member.getName());
        if (!findmembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //전체 회원 조회

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    //회원 수정
    @Transactional
    public Long updateName(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);

        return member.getId();
    }

}
