package com.soni.SpringAuth.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupDTO {
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String password;

}
