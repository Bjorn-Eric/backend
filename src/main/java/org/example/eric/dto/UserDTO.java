package org.example.eric.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 255)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255)
    private String password;

    @NotBlank(message = "Re-entered password is required")
    @Size(min = 8, max = 255)
    private String rePassword;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "ROLE_USER|ROLE_ADMIN")
    private String role;

    public UserDTO() {
    }

    public UserDTO(String username, String password, String rePassword, String role) {
        this.username = username;
        this.password = password;
        this.rePassword = rePassword;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
