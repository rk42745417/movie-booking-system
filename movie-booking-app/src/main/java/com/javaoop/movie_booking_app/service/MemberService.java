package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.dto.RegistrationDto;
import com.javaoop.movie_booking_app.model.Member;
import com.javaoop.movie_booking_app.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing member-related operations such as registration,
 * password changes, and retrieval by email.
 */
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs MemberService with the necessary dependencies.
     *
     * @param memberRepository repository to access Member data
     * @param passwordEncoder  encoder for hashing passwords securely
     */
    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new member using the provided registration data.
     * Checks for email uniqueness before saving.
     *
     * @param registrationDto data transfer object containing email, password, and birthday
     * @throws IllegalStateException if a member with the same email already exists
     */
    @Transactional
    public void register(RegistrationDto registrationDto) {
        if (memberRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new IllegalStateException("使用者已經存在");
        }

        String passwordHash = passwordEncoder.encode(registrationDto.getPassword());
        memberRepository.save(new Member(registrationDto.getEmail(), passwordHash,
                registrationDto.getBirthday()));
    }

    /**
     * Changes the password of an existing member identified by email.
     * The new password is encoded before saving.
     *
     * @param email       the email of the member whose password is to be changed
     * @param newPassword the new password to set
     * @throws IllegalStateException if no member with the given email exists
     */
    @Transactional
    public void changePassword(String email, String newPassword) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(IllegalStateException::new);
        String passwordHash = passwordEncoder.encode(newPassword);
        member.setPasswordHash(passwordHash);
        memberRepository.save(member);
    }

    /**
     * Retrieves a member by their email.
     *
     * @param email the email to search for
     * @return an Optional containing the found Member or empty if not found
     */
    public Optional<Member> getByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
