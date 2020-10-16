package com.omniupdate.addressbook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContactRepo extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {
}
