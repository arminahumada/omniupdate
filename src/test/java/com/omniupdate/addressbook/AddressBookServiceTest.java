package com.omniupdate.addressbook;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class AddressBookServiceTest {

    @InjectMocks
    private AddressBookService addressBookService;

    @Mock
    private ContactRepo contactRepo;

    @Test
    public void getExistingContact_thenReturnContact() {
        Contact contact = Contact.builder().id(1L).build();

        Mockito.when(contactRepo.findById(1L))
                .thenReturn(Optional.of(contact));

        Assertions.assertThat(addressBookService.getContact(1L))
                .isEqualTo(contact);
    }
    
    @Test
    public void getInvalidContact_thenThrowContactNotFound() {
        Mockito.when(contactRepo.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> addressBookService.getContact(1L))
                .isInstanceOf(ContactNotFoundException.class);
    }
}
