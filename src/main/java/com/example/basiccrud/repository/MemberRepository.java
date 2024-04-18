package com.example.basiccrud.repository;

import com.example.basiccrud.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 전체 검색
    @Query(value = "select * from member order by id", nativeQuery = true)
    List<Member> searchQuery();

    // 이름으로 검색
    @Query(value = "select * from member where name like %:keyword% order by id", nativeQuery = true)
    List<Member> searchName(@Param("keyword")String keyword);

    // 주소로 검색
    @Query(value = "select * from member where my_address like %:keyword% order by id", nativeQuery = true)
    List<Member> searchAddress(@Param("keyword")String keyword);

}
