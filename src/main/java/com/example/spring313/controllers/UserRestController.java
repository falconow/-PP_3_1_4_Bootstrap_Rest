package com.example.spring313.controllers;

import com.example.spring313.dto.UserDTO;
import com.example.spring313.model.User;
import com.example.spring313.service.RoleService;
import com.example.spring313.service.UserService;
import com.example.spring313.utils.MapperUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class UserRestController {
    private final UserService userService;
    private final RoleService roleService;
    private final MapperUser mapperUser;
    private Logger logger = Logger.getLogger(UserRestController.class.getName());


    @Autowired
    public UserRestController(UserService userService, RoleService roleService, MapperUser mapperUser) {
        this.userService = userService;
        this.roleService = roleService;
        this.mapperUser = mapperUser;
    }

    @GetMapping(value = "/users")
    public List<UserDTO> users() {
        List<UserDTO> usersDTO = userService.getAllUser().stream().map(u -> mapperUser.toUserDTO(u)).toList();
        return usersDTO;
    }


}
