package com.javaoop.gym_booking_app.repository;

import com.javaoop.gym_booking_app.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // Example of a custom query method
    Optional<Member> findByEmail(String email);
}