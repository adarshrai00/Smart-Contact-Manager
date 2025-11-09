package com.scm.forms;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {
    @NotBlank(message = "user name required")
    @Size(min = 3)
    private String name;
    @Email
    private String email;
    @NotBlank
    @Size(min = 6)
    private String password;
    @Size(min = 10)
    private String PhoneNumber;
    private String  about;
}
