package com.javaoop.gym_booking_app.service;

import com.javaoop.gym_booking_app.model.*;
import com.javaoop.gym_booking_app.repository.MemberRepository;
import com.javaoop.gym_booking_app.repository.ReservationRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;
    private final PasswordEncoder passwordEncoder;

    // ★ 顯式建構子注入
    public MemberService(MemberRepository memberRepository,
                         ReservationRepository reservationRepository,
                         PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.reservationRepository = reservationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /* -------- 註冊 -------- */
    public ServiceResult<Long> register(String email, String rawPassword,
                                        String fullName, String dobStr,
                                        Gender gender, String phone) {
        if (memberRepository.findByEmail(email).isPresent())
            return ServiceResult.fail("Email 已被註冊");

        LocalDate dob = null;
        if (dobStr != null && !dobStr.isBlank()) {
            try { dob = LocalDate.parse(dobStr); }
            catch (DateTimeParseException e) { return ServiceResult.fail("生日格式錯誤"); }
        }

        Member m = new Member();
        m.setEmail(email);
        m.setPasswordHash(passwordEncoder.encode(rawPassword));
        m.setFullName(fullName);
        m.setDateOfBirth(dob);
        m.setGender(gender);
        m.setPhone(phone);
        m.setRole(Role.MEMBER);
        m.setCreatedAt(LocalDateTime.now());
        m.setActive(true);

        m = memberRepository.save(m);
        return ServiceResult.ok(m.getId());
    }

    /* -------- 登入 -------- */
    public Member login(String email, String rawPassword) {
        return memberRepository.findByEmail(email)
                .filter(m -> passwordEncoder.matches(rawPassword, m.getPasswordHash()))
                .orElse(null);
    }
    
    public ServiceResult<Member> loginAsResult(String email, String rawPassword) {
        return memberRepository.findByEmail(email)
                .filter(m -> passwordEncoder.matches(rawPassword, m.getPasswordHash()))
                .map(m -> ServiceResult.ok(m))
                .orElse(ServiceResult.fail("帳號或密碼錯誤"));
    }

    /* -------- 查詢預約 -------- */
    @Transactional(readOnly = true)
    public List<Reservation> getMemberReservations(Long memberId) {
        return reservationRepository.findByMemberId(memberId);
    }
}

