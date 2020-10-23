package com.omniupdate.addressbook;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {AddressBookApplication.class, DataLoaderService.class,}, initializers = ConfigFileApplicationContextInitializer.class)
@WebAppConfiguration
@DataJpaTest
public class ContactRepoIT {

    @Autowired
    ContactRepo contactRepo;

    @Test
    public void testCreateAndLookup() {

        Contact contact = Contact.builder()
                .firstName("Armin")
                .lastName("Ahu")
                .build();

        contactRepo.save(contact);

        Contact savedContact = contactRepo.getOne(contact.getId());

        assertThat(savedContact).extracting(Contact::getFirstName, Contact::getLastName)
                .contains(contact.getFirstName(), contact.getLastName());
    }

    @Test
    public void testNullLastName_thenThrowEx(){
        Contact contact = Contact.builder()
                .firstName("Armin")
//                .lastName("Ahu")
                .build();

        assertThatThrownBy(() -> contactRepo.save(contact))
                .isInstanceOf(ConstraintViolationException.class);

    }

}
