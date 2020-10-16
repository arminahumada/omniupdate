package com.omniupdate.addressbook;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 *
 * Simple contact entry in the address book.  First and Last names are required (not blank) others are optional
 *  first and last name
 *  phone number
 *  address
 *  email address
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;
}
