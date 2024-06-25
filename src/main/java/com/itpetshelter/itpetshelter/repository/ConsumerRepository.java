package com.itpetshelter.itpetshelter.repository;

import com.itpetshelter.itpetshelter.domain.Consumer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ConsumerRepository extends JpaRepository<Consumer, String> {

    // 소셜 로그인이 아닌, 일반 로그인으로 검색하기.
    // N+1,한번에 다같이 조회를 하자. in 연산자 이용해서, 하나의 쿼리로 동작하기.
    @EntityGraph(attributePaths = "roleSet")
    @Query("select c from Consumer c where c.Cid = :Cid and c.social = false ")
    Optional<Consumer> getWithRoles(String Cid);

    // 이메일으로 유저 확인.
    @EntityGraph(attributePaths = "roleSet")
    Optional<Consumer> findByEmail(String email);

    //DML 적용하기
    @Modifying
    @Transactional
    @Query("update Consumer c set c.Cpw=:Cpw where c.Cid = :Cid")
    void updatePassword(@Param("Cpw") String Cpw, @Param("Cid") String Cid);

}
