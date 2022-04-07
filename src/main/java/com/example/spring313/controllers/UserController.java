package com.example.spring313.controllers;


import com.example.spring313.model.Role;
import com.example.spring313.model.User;
import com.example.spring313.service.RoleService;
import com.example.spring313.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private Logger logger = Logger.getLogger(UserController.class.getName());


    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String printIndex() {
        return "index";
    }

    @GetMapping("/admin")
    public String printAdmin(Model model) {
        List<User> listUsers = userService.getAllUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authUser = (User) authentication.getPrincipal();
        authUser.setPassword("");
        model.addAttribute("authUser", authUser);
        model.addAttribute("authUserRoles",  authentication.getAuthorities());
        //model.addAttribute("authUser", authentication.getName() + " with roles: " + authentication.getAuthorities().toString());
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "adminPanel";
    }

    //Форма для ввода нового пользователя
    @GetMapping(value = "/admin/new")
    public String printFormCreate(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "createUserForm";
    }

    //Форма для редактирования
    @GetMapping(value = "/admin/{id}/edit")
    public String editUser(Model model, @PathVariable("id") Long id) {
        User editUser = userService.findUserById(id);
        model.addAttribute("user", editUser);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "editUserForm";
    }

    //Сохранение нового пользователя
    @PostMapping(value = "/admin/new")
    public String createUser(@ModelAttribute User user, @RequestParam(required = false, value = "role") List<Long> selectRoles) {
        logger.info(selectRoles.toString());
        List<Role> roles = new ArrayList<>();
        if (selectRoles != null && !selectRoles.isEmpty()) {
            roles = selectRoles.stream().map(roleService::findRoleById).toList();
        }

        user.setCollectionsRoles(roles);
        userService.addUser(user);
        return "redirect:/admin";
    }

    //Удаление пользователя
    @DeleteMapping(value = "/admin/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    //Изменение пользователя
    @PutMapping(value = "/admin/{id}/edit")
    public String saveEditUser(@ModelAttribute User user, @RequestParam(required = false, value = "role") List<Long> selectRoles) {
        List<Role> roles = new ArrayList<>();
        if (selectRoles != null && !selectRoles.isEmpty()) {
            roles = selectRoles.stream().map(roleService::findRoleById).toList();
        }

        user.setCollectionsRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    //Страница user
    @GetMapping("/user")
    public String printUser(Principal principal, Model model) {
        User user = userService.findUserByLogin(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

}
