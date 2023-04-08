package com.contactapi.mycontactapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.contactapi.mycontactapp.entity.Contact;
import com.contactapi.mycontactapp.repository.ContactRepository;


@Service
public class ContactServiceImpl implements ContactService {
    
    private final ContactRepository contactRepository;


    public ContactServiceImpl(ContactRepository contactRepository){
        this.contactRepository=contactRepository;
    }


    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }


    @Override
    public Contact getContactById(Long id) {
        return contactRepository.findById(id).orElse(null);
    }


    @Override
    public void addContact(Contact contact) {
        contactRepository.save(contact);
    }


    @Override
    public void updateContact(Contact contact) {
        contactRepository.save(contact);
    }


    @Override
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

    
    @Override
    public List<Contact> searchContacts(String query) {
        return contactRepository.searchByKeyword(query);
    }


}
