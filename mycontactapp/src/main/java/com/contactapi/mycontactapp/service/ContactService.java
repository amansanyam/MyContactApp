package com.contactapi.mycontactapp.service;

import java.util.List;

import com.contactapi.mycontactapp.entity.Contact;


public interface ContactService {
     
    List<Contact> getAllContacts();

    Contact getContactById(Long id);

    void addContact(Contact contact);

    void updateContact(Contact contact);

    void deleteContact(Long id);

    List<Contact> searchContacts(String query);
    
}
