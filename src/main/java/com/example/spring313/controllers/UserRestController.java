package com.example.spring313.controllers;

import com.example.spring313.dto.UserDTO;
import com.example.spring313.model.Role;
import com.example.spring313.model.User;
import com.example.spring313.service.RoleService;
import com.example.spring313.service.UserService;
import com.example.spring313.utils.MapperUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
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


    @GetMapping(value = "/user/{id}")
    public UserDTO getUser (@PathVariable("id") Long id) {
        User user = userService.findUserById(id);
        return mapperUser.toUserDTO(user);
    }

    @PostMapping(value = "/user")
    public UserDTO createUser(@ModelAttribute User user, @RequestParam(required = false, value = "role") List<Long> selectRoles) {
        List<Role> roles = new ArrayList<>();
        if (selectRoles != null && !selectRoles.isEmpty()) {
            roles = selectRoles.stream().map(roleService::findRoleById).toList();
        }
        user.setCollectionsRoles(roles);
        userService.addUser(user);
        return mapperUser.toUserDTO(user);
    }

    @DeleteMapping(value = "/user/{id}")
    public Long deleteUser (@PathVariable("id") Long id) {
        logger.info("delete");
        userService.deleteUser(id);
        return id;
    }

    //Изменение пользователя
    @PutMapping(value = "/user/{id}")
    public UserDTO editUser(@ModelAttribute User user, @RequestParam(required = false, value = "Role") List<Long> selectRoles) {
        List<Role> roles = new ArrayList<>();
        if (selectRoles != null && !selectRoles.isEmpty()) {
            roles = selectRoles.stream().map(roleService::findRoleById).toList();
        }

        user.setCollectionsRoles(roles);
        userService.updateUser(user);
        return mapperUser.toUserDTO(user);
    }


}
