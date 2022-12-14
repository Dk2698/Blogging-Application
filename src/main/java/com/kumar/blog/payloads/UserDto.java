package com.kumar.blog.payloads;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// data expose directly  -> response body
// validating data using bean validator with custom messages
// java beans is validated with JSR 380 know as BEan validation 2.0
// from request body
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;
    // create proper message exception handle
    @NotEmpty
    @Size(min = 4, message ="username must be min of 4 characters !!")
    private String name;

    @Email(message= " Email address is not valid !!")
    private String email;

    @NotEmpty
    @Size(min = 5, max = 10, message ="password must be min  5 char and max 10 char")
    private String password;

    @NotEmpty
    private String about;
}