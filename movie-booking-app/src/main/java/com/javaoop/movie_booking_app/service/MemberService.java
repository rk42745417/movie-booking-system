package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.dto.RegistrationDto;
import com.javaoop.movie_booking_app.model.Member;
import com.javaoop.movie_booking_app.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(RegistrationDto registrationDto) {
        if (memberRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new IllegalStateException("使用者已經存在");
        }

        String passwordHash = passwordEncoder.encode(registrationDto.getPassword());
        memberRepository.save(new Member(registrationDto.getEmail(), passwordHash,
                registrationDto.getBirthday()));
    }

    @Transactional
    public void changePassword(String email, String newPassword) {
        Member member = memberRepository.findByEmail(email).orElseThrow(IllegalStateException::new);
        String passwordHash = passwordEncoder.encode(newPassword);
        member.setPasswordHash(passwordHash);
        memberRepository.save(member);
    }

    public Optional<Member> getByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}

