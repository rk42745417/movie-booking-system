package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email); // 可用於登入或驗證註冊時 email 是否存在
}



