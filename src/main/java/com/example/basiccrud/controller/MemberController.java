package com.example.basiccrud.controller;

import com.example.basiccrud.dto.MemberDto;
import com.example.basiccrud.entity.Member;
import com.example.basiccrud.repository.MemberRepository;
import com.example.basiccrud.service.MemberService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/member")
@Slf4j
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("show")
    public String showAll(Model model) {
        List<MemberDto> memberDtoList = memberService.showAllMembers();

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
//        List<MemberDto> memberDtoList = memberRepository.findAll()
//                .stream()
//                .map(x -> MemberDto.fromMemberEntity(x))
//                .toList();
//        log.info("memberDtoList = " + memberDtoList.toString());

        model.addAttribute("memberDto", memberDtoList);
        return "showMember";
    }

    @GetMapping("insertForm")
    public String insertForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "insertForm";
    }

    @PostMapping("insert")
    public String insertMember(@Valid @ModelAttribute("memberDto")MemberDto dto,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("========Validation Error");
            return "insertForm";
        }

        memberService.insertMember(dto);
//        log.info(dto.toString());

        // 가져온 DTO를 Entity Member Class에 옮겨 담아요
//        Member member = dto.fromMemberDto(dto);
//        log.info("Member : " + member.toString());

        // 저장합니다
//        memberRepository.save(member);
        return "redirect:/member/show";
    }

    @GetMapping("/update")
    public String updateMember(@RequestParam("updateId")Long id, Model model) {

        MemberDto memberDto = memberService.getOneMember(id);
        
//        // 가져온 ID를 데이터베이스에서 검색
//        Member member = memberRepository.findById(id).orElse(null);
//
//        // 검색한 결과를 MemberDto에 옮겨 담기
//        MemberDto dto = MemberDto.fromMemberEntity(member);
//
//        // 위 2가지를 한번에 처리하기
//        MemberDto memberDto = memberRepository.findById(id)
//                .map(x -> MemberDto.fromMemberEntity(x))
//                .orElse(null);

        // dto를 모델에 담아서 updateForm 전송해야 보여줄 수 있음
        model.addAttribute("memberDto", memberDto);
        return "updateForm";
    }

    @PostMapping("update")
    public String update(@Valid @ModelAttribute("memberDto") MemberDto dto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("========Validation Error");
            return "updateForm";
        }

        memberService.update(dto);
//    public String update(@RequestParam("id")Long id, @RequestParam("name")String name, @RequestParam("age")int age, @RequestParam("addr")String addr) {

//       //1. dto를 entity로 변경
//        Member member = dto.fromMemberDto(dto);
//
//        //2. 저장
//        memberRepository.save(member);

        return "redirect:/member/show";
    }

    //삭제 처리
//    @PostMapping("/delete")
    @PostMapping("/delete/{deleteId}")
//    public String delete(@RequestParam("deleteId")Long id) {
    public String delete(@PathVariable("deleteId")Long id) {
//        memberRepository.deleteById(id);
        memberService.delete(id);
        return "redirect:/member/show";
    }

    //검색 처리
    @GetMapping("/search")
    public String searchMember(@RequestParam("type")String type,
                               @RequestParam("keyword")String keyword,
                               Model model) {
//        log.info("type = " + type + ", keyword = " + keyword);
        List<MemberDto> memberDtoList = memberService.searchMember(type, keyword);
//        List<MemberDto> memberDtoList = new ArrayList<>();
//        switch (type) {
//            case "name" :
//                // 이름으로 DB 검색
//                memberDtoList = memberRepository.searchName(keyword)
//                        .stream()
//                        .map(x -> MemberDto.fromMemberEntity(x))
//                        .toList();
//                break;
//            case "addr" :
//                // 주소로 DB 검색
//                memberDtoList = memberRepository.searchAddress(keyword)
//                        .stream()
//                        .map(x -> MemberDto.fromMemberEntity(x))
//                        .toList();
//                break;
//            default:
//                // 전체 검색
//                memberDtoList = memberRepository.searchQuery()
//                        .stream()
//                        .map(x -> MemberDto.fromMemberEntity(x))
//                        .toList();
//                break;
//        }
        model.addAttribute("memberDto", memberDtoList);
        return "showMember";
    }
}
