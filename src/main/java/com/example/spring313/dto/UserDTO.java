package com.example.spring313.dto;


import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.HashSet;
import java.util.Set;

public class UserDTO {
    private Long id;
    private String FirstName;
    private String LastName;
    private String Age;
    private String Email;
    private String Password;
    private Set<String> roles = new HashSet<>();

    public UserDTO() {}

    public UserDTO(Long id, String firstName, String lastName, String age, String email, String password, Set<String> roles) {
        this.id = id;
        FirstName = firstName;
        LastName = lastName;
        Age = age;
        Email = email;
        Password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
