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

/**
 * Service for handling member-related tasks.
 */
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a MemberService with the given repositories and password encoder.
     * @param memberRepository The repository for handling member data.
     * @param reservationRepository The repository for handling reservation data.
     * @param passwordEncoder The password encoder.
     */
    public MemberService(MemberRepository memberRepository,
                         ReservationRepository reservationRepository,
                         PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.reservationRepository = reservationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new member.
     * @param email The email of the new member.
     * @param rawPassword The raw password of the new member.
     * @param fullName The full name of the new member.
     * @param dobStr The date of birth of the new member as a string.
     * @param gender The gender of the new member.
     * @param phone The phone number of the new member.
     * @return A ServiceResult with the result of the operation.
     */
    public ServiceResult<Long> register(String email, String rawPassword,
                                        String fullName, String dobStr,
                                        Gender gender, String phone) {
        if (memberRepository.findByEmail(email).isPresent())
            return ServiceResult.fail("Email already registered");

        LocalDate dob = null;
        if (dobStr != null && !dobStr.isBlank()) {
            try { dob = LocalDate.parse(dobStr); }
            catch (DateTimeParseException e) { return ServiceResult.fail("Incorrect date of birth format"); }
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

    /**
     * Logs in a member.
     * @param email The email of the member.
     * @param rawPassword The raw password of the member.
     * @return The member if login is successful, otherwise null.
     */
    public Member login(String email, String rawPassword) {
        return memberRepository.findByEmail(email)
                .filter(m -> passwordEncoder.matches(rawPassword, m.getPasswordHash()))
                .orElse(null);
    }

    /**
     * Logs in a member and returns a ServiceResult.
     * @param email The email of the member.
     * @param rawPassword The raw password of the member.
     * @return A ServiceResult with the result of the operation.
     */
    public ServiceResult<Member> loginAsResult(String email, String rawPassword) {
        return memberRepository.findByEmail(email)
                .filter(m -> passwordEncoder.matches(rawPassword, m.getPasswordHash()))
                .map(m -> ServiceResult.ok(m))
                .orElse(ServiceResult.fail("Incorrect account or password"));
    }

    /**
     * Gets all reservations for a member.
     * @param memberId The ID of the member.
     * @return A list of all reservations for the member.
     */
    @Transactional(readOnly = true)
    public List<Reservation> getMemberReservations(Long memberId) {
        return reservationRepository.findByMemberId(memberId);
    }
}