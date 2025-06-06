package com.javaoop.gym_booking_app.service;

import model.Member;
import model.Reservation;
import model.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 健身房會員服務（記憶體版本）
 * <p>
 * ※ 目前所有資料都存放在假想的 Database 類別的 Map 裡，
 *   方便單元測試；之後如需接 MySQL / JPA，只要把
 *   Database.xxx 換成 Repository 呼叫即可。
 */
public class MemberService {

    /**
     * 註冊新會員
     *
     * @param email       信箱（唯一）
     * @param password    明碼密碼（這裡僅 Base64，正式環境請改用 BCrypt）
     * @param name        真實姓名
     * @param birthdayStr 生日字串 yyyy-MM-dd
     * @return true = 註冊成功；false = 信箱重複 / 生日格式錯
     */
    public boolean register(String email,
                            String password,
                            String name,
                            String birthdayStr) {

        /* 1. 檢查 email 是否已存在 */
        boolean exists = Database.members.values()
                .stream()
                .anyMatch(m -> m.getEmail().equalsIgnoreCase(email));

        if (exists) return false;

        /* 2. 解析生日 */
        LocalDate birthday;
        try {
            birthday = LocalDate.parse(birthdayStr);    // yyyy-MM-dd
        } catch (DateTimeParseException e) {
            return false;
        }

        /* 3. 產生會員物件並存入「資料庫」 */
        String memberId = UUID.randomUUID().toString();
        String encryptedPw = Base64.getEncoder()
                                   .encodeToString(password.getBytes());

        Member m = new Member();
        m.setId(memberId);
        m.setEmail(email);
        m.setPassword(encryptedPw);
        m.setName(name);
        m.setDateOfBirth(birthday);
        m.setRole(Role.MEMBER);
        m.setCreatedAt(LocalDateTime.now());

        Database.members.put(memberId, m);
        return true;
    }

    /**
     * 登入
     *
     * @return 找不到或密碼不符 → null
     */
    public Member login(String email, String password) {
        String encrypted = Base64.getEncoder()
                                 .encodeToString(password.getBytes());

        return Database.members.values().stream()
                .filter(m -> m.getEmail().equalsIgnoreCase(email) &&
                             m.getPassword().equals(encrypted))
                .findFirst()
                .orElse(null);
    }

    /**
     * 依會員查詢所有預約
     */
    public List<Reservation> getMemberReservations(String memberId) {
        return Database.reservations.values().stream()
                .filter(r -> r.getMember().getId().equals(memberId))
                .collect(Collectors.toList());
    }
}