package com.example.spring313.utils;

import com.example.spring313.dto.UserDTO;

import com.example.spring313.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MapperUser {

    public UserDTO toUserDTO (User user) {
        Set<String> roleDTO = user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toSet());
        UserDTO toDTO = new UserDTO (
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                roleDTO
        );
        return toDTO;
    }

}
