package org.example.eric.controller.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordFormDTO {

    @NotBlank
    private String oldPassword;

    @NotBlank
    @Size(min = 10)
    private String newPassword;

    @NotBlank
    @Size(min = 10)
    private String newRetypedPassword;

    public ChangePasswordFormDTO() {

    }

    public ChangePasswordFormDTO(String oldPassword, String newPassword, String newRetypedPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newRetypedPassword = newRetypedPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewRetypedPassword() {
        return newRetypedPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setNewRetypedPassword(String newRetypedPassword) {
        this.newRetypedPassword = newRetypedPassword;
    }
}
