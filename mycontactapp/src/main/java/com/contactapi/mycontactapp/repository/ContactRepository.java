package com.contactapi.mycontactapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import com.contactapi.mycontactapp.entity.Contact;


public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("SELECT c FROM Contact c WHERE LOWER(c.firstName) LIKE %:query% OR LOWER(c.lastName) LIKE %:query% OR LOWER(c.email) LIKE %:query%")
    List<Contact> searchByKeyword(String query);
    
}

