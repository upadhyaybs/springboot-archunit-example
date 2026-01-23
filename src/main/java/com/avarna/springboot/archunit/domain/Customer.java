package com.tp.springboot.archunit.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
	private Long id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String phone;

}
