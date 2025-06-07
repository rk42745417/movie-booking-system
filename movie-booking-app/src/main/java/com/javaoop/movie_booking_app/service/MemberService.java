package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.dto.RegistrationDto;
import com.javaoop.movie_booking_app.model.Member;
import com.javaoop.movie_booking_app.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;
import java.time.LocalDate;

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
}

