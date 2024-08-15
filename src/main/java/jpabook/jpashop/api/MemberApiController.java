package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;


    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV1(@RequestBody @Valid UpdateMemberRequest request,
                                               @PathVariable Long id){

            Long updateId = memberService.updateName(id, request.getName());

            return new UpdateMemberResponse(updateId);
    }

    @GetMapping("/api/v1/members")
    public List<Member> getAllMembersV1(){
        return memberService.findAll();
    }

    @GetMapping("/api/v2/members")
    public Result getAllMembersV2(){
        List<Member> findMembers = memberService.findAll();
        List<MemberDTO> collect = findMembers.stream()
                .map(m -> new MemberDTO(m.getName()) )
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Data
    static class Result<T>{
        private T data;

        public Result(T data){
            this.data = data;
        }
    }


    @Data
    @AllArgsConstructor
    static class MemberDTO{
        private String name;
    }

    @Data
    static class UpdateMemberRequest {

        private String name;
    }

    @Data
    static class UpdateMemberResponse {
        private Long id;

        public UpdateMemberResponse(Long id){
            this.id = id;
        }
    }

    @Data
    static class CreateMemberRequest{
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id){
            this.id = id;
        }

    }

}
