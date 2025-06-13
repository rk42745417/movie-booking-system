package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Member} entities.
 * 
 * Extends {@link JpaRepository} to provide CRUD operations and pagination
 * capabilities for Member objects.
 * 
 * Provides an additional method to find a Member by their email address.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    /**
     * Finds a Member entity by its email address.
     * 
     * @param email the email address to search for
     * @return an {@link Optional} containing the Member if found, or empty if not found
     */
    Optional<Member> findByEmail(String email);
}
