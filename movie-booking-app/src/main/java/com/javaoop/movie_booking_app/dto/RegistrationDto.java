package com.javaoop.movie_booking_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for user registration form.
 * <p>
 * 用於接收用戶註冊時提交的資料，包括電子郵件、密碼和生日。
 * </p>
 */
public class RegistrationDto {

    /**
     * Default no-argument constructor.
     */
    public RegistrationDto() {
        // no-op
    }
    
    /**
     * The email address of the user.
     * Must not be empty and must be a valid email format.
     */
    @NotEmpty(message = "Email 不得為空")
    @Email(message = "Email 格式不正確")
    private String email;

    /**
     * The password for the new user account.
     * Must not be empty.
     */
    @NotEmpty(message = "密碼不得為空")
    private String password;

    /**
     * The birthday of the user.
     * Must not be null.
     */
    @NotNull(message = "生日不得為空")
    private LocalDate birthday;

    /**
     * Returns the user's email.
     *
     * @return the email address string
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     *
     * @param email the email address string to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's password.
     *
     * @return the password string
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the password string to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user's birthday.
     *
     * @return the birthday as LocalDate
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Sets the user's birthday.
     *
     * @param birthday the birthday to set as LocalDate
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
