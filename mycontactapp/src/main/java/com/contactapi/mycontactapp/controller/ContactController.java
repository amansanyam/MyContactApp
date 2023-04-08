package com.contactapi.mycontactapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.contactapi.mycontactapp.dto.JwtAuthRequest;

import com.contactapi.mycontactapp.entity.Contact;
import com.contactapi.mycontactapp.service.ContactService;
import com.contactapi.mycontactapp.service.JwtService;


import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping
    @SecurityRequirement(name = "token")
    public ResponseEntity<List<Contact>> getAllContacts(){
        List<Contact> contacts=contactService.getAllContacts();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }
 
    
    @GetMapping("/{id}")
    @SecurityRequirement(name = "token")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id){
        Contact contact=contactService.getContactById(id);
        if(contact==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contact,HttpStatus.OK);

    }


    @PostMapping
    @SecurityRequirement(name = "token")
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact){
        contactService.addContact(contact);
        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @SecurityRequirement(name = "token")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact contact){
        Contact existingContact = contactService.getContactById(id);
        if(existingContact==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingContact.setFirstName(contact.getFirstName());
        existingContact.setLastName(contact.getLastName());
        existingContact.setEmail(contact.getEmail());
        existingContact.setPhoneNumber(contact.getPhoneNumber());

        contactService.updateContact(existingContact);

        return new ResponseEntity<>(existingContact,HttpStatus.OK);

    }   

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "token")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id){
            Contact contact= contactService.getContactById(id);
            if(contact==null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            contactService.deleteContact(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/search")
    @SecurityRequirement(name = "token")
    public ResponseEntity<List<Contact>> searchContacts(@RequestParam("query") String query){
        List<Contact> contacts = contactService.searchContacts(query);
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @PostMapping("/auth/login")
    public String authenticateAndGetToken(@RequestBody JwtAuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    
    

}
