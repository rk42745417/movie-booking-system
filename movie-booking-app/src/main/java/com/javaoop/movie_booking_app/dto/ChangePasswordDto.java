package com.javaoop.movie_booking_app.dto;

import jakarta.validation.constraints.NotEmpty;

/**
 * Data Transfer Object (DTO) for changing a user's password.
 * <p>
 * This class encapsulates the new password input provided by the user
 * during a password change request, typically used in forms where users
 * update their account credentials.
 * </p>
 * <p>
 * The field is validated to ensure it is not empty, preventing submission
 * of an empty or blank password.
 * </p>
 * 
 * <h2>Usage Example:</h2>
 * <pre>
 * ChangePasswordDto dto = new ChangePasswordDto();
 * dto.setNewPassword("MyNewStrongPassword123!");
 * </pre>
 */
public class ChangePasswordDto {

    /**
     * The new password entered by the user.
     * This field must not be empty, enforced by the {@link NotEmpty} annotation.
     */
    @NotEmpty(message = "新密碼不得為空")
    private String newPassword;

    /**
     * Default no-argument constructor.
     */
    public ChangePasswordDto() {
        // no-op
    }
    
    /**
     * Returns the new password string.
     *
     * @return the new password entered by the user
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Sets the new password string.
     *
     * @param newPassword the new password to set; must not be empty
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
