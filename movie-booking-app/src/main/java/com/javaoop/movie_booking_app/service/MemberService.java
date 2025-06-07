package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.model.Member;
import com.javaoop.movie_booking_app.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member registerMember(String email, String rawPassword, LocalDate dob) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email 已被註冊");
        }

        String hashedPassword = hashPassword(rawPassword); // Replace with actual secure hash
        Member newMember = new Member(email, hashedPassword, dob);
        return memberRepository.save(newMember);
    }

    public Optional<Member> login(String email, String rawPassword) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (verifyPassword(rawPassword, member.getPasswordHash())) {
                return Optional.of(member);
            }
        }
        return Optional.empty();
    }

    // TODO: Replace with secure hash functions like BCrypt
    private String hashPassword(String password) {
        return password; // Replace with BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean verifyPassword(String rawPassword, String storedHash) {
        return rawPassword.equals(storedHash); // Replace with BCrypt.checkpw(rawPassword, storedHash);
    }

    /**
     * Retrieve the currently authenticated member.
     * 
     * NOTE: This is a placeholder. Integrate with Spring Security or session management.
     */
    public Member getCurrentMember() {
        // Example: return (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return null; // TODO: implement based on your security setup
    }
}




