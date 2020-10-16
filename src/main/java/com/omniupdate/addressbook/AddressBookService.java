package com.omniupdate.addressbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;


@Service
public class AddressBookService {

    @Autowired
    ContactRepo contactRepo;

    public Page<Contact> search(Specification<Contact> spec, Pageable pageable) {
        return contactRepo.findAll(spec, pageable);
    }

    public Contact getContact(Long id) {
        return contactRepo.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(id));
    }

    public Contact createContact(Contact contact) {
        return contactRepo.save(contact);
    }

    public Contact updateContact(Contact contact) {
        contactRepo.findById(contact.getId())
                .orElseThrow(() -> new ContactNotFoundException(contact.getId()));
        return contactRepo.save(contact);
    }

    /**
     * HARD delete contact by id
     * @param id
     */
    public void deleteContact(@NotNull Long id) {
        Contact contact = contactRepo.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(id));
        contactRepo.delete(contact);
    }

}
