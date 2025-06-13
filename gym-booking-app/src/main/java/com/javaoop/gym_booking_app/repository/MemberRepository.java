package com.javaoop.gym_booking_app.repository;

import com.javaoop.gym_booking_app.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for handling members.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    /**
     * Finds a member by their email.
     * @param email The email of the member to find.
     * @return An Optional containing the member if found, otherwise empty.
     */
    Optional<Member> findByEmail(String email);
}