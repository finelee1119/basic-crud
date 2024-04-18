package com.example.basiccrud.controller;

import com.example.basiccrud.dto.MemberDto;
import com.example.basiccrud.entity.Member;
import com.example.basiccrud.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController {
    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("show")
    public String showAll(Model model) {
        List<Member> memberList = memberRepository.findAll();

//        방법1.
//        MemberDto dto = null;
//        List<MemberDto> dtoList = new ArrayList();
//        for (Member member : memberList) {
//            dto.setId(member.getId());
//            dto.setName(member.getName());
//            dto.setAge(member.getAge());
//            dto.setAddr(member.getAddr());
//            dtoList.add(dto);
//        }

//        방법2. MemberDto static Method로 사용하는 법
//        List<MemberDto> dtoList = new ArrayList();
//        for (Member member : memberList) {
//            dtoList.add(MemberDto.fromMemberEntity(member));
//        }

//        방법3. Static Method와 Stream 사용
        List<MemberDto> memberDtoList = memberRepository.findAll()
                .stream()
                .map(x -> MemberDto.fromMemberEntity(x))
                .toList();
        log.info("memberDtoList = " + memberDtoList.toString());

        model.addAttribute("memberDto", memberDtoList);
        return "showMember";
    }

    @GetMapping("insertForm")
    public String insertForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "insertForm";
    }

    @PostMapping("insert")
    public String insertMember(@ModelAttribute("memberDto")MemberDto dto) {
        log.info(dto.toString());
        // 가져온 DTO를 Entity Member Class에 옮겨 담아요
        Member member = dto.fromMemberDto(dto);
        log.info("Member : " + member.toString());

        // 저장합니다
        memberRepository.save(member);
        return "redirect:/member/show";
    }

    @GetMapping("/update")
    public String updateMember(@RequestParam("updateId")Long id, Model model) {
        log.info("====== id : " + String.valueOf(id));
        
        // 가져온 ID를 데이터베이스에서 검색
        Member member = memberRepository.findById(id).orElse(null);
        
        // 검색한 결과를 MemberDto에 옮겨 담기
        MemberDto dto = MemberDto.fromMemberEntity(member);

        // 위 2가지를 한번에 처리하기
        MemberDto memberDto = memberRepository.findById(id)
                .map(x -> MemberDto.fromMemberEntity(x))
                .orElse(null);

        // dto를 모델에 담아서 updateForm 전송해야 보여줄 수 있음
        model.addAttribute("memberDto", memberDto);

        return "updateForm";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("memberDto") MemberDto dto) {
        log.info("Update : " + dto.toString());
        //1. dto를 entity로 변경
        Member member = dto.fromMemberDto(dto);

        //2. 저장
        memberRepository.save(member);

        return "redirect:/member/show";
    }
}
