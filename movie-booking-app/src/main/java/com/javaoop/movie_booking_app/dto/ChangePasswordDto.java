package com.javaoop.movie_booking_app.dto;

import jakarta.validation.constraints.NotEmpty;

public class ChangePasswordDto {
    @NotEmpty
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
