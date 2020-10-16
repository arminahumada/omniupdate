package com.omniupdate.addressbook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Contact not found")
public class ContactNotFoundException extends RuntimeException {
    private Long id;
}
