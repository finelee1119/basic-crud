package com.example.basiccrud.dto;

import com.example.basiccrud.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private Long id;

    @Size(min = 2, message = "이름은 2자 이상으로 입력해야 합니다.")
    private String name;

    @Range(min = 0, max = 120, message = "나이는 0세부터 120세까지 입니다.")
    private int age;

    @NotBlank(message = "주소는 꼭 입력해야 합니다.")
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
