package jpabook.jpashop.service;

import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @Rollback(false)
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");
        
        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(savedId, memberRepository.findOne(savedId).getId());

    }

    @Test
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        try{
            memberService.join(member2); //예외터져야함
        }catch (IllegalStateException e){
            //System.out.println(e);
            return;
        }

        //then
        fail("이까지 내려오면 안됌");
    }
}