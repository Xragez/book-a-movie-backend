package com.bookamovie.be.view;

import com.bookamovie.be.constraint.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {

    private String firstName;
    private String surname;
    private String username;

    @ValidPassword
    private String password;
}
