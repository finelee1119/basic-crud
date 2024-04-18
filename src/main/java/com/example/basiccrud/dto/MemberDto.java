package com.example.basiccrud.dto;

import com.example.basiccrud.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String name;
    private int age;
    private String addr;

    // Entity를 Dto로 변환
    public static MemberDto fromMemberEntity(Member member) {
        return new MemberDto(
          member.getId(), member.getName(), member.getAge(), member.getAddr()
        );
    }

    // Dto를 Entity로 변환
    public static Member fromMemberDto(MemberDto dto) {
        Member member = new Member();
        member.setId(dto.getId());
        member.setName(dto.getName());
        member.setAge(dto.getAge());
        member.setAddr(dto.getAddr());
        return member;
    }
}
