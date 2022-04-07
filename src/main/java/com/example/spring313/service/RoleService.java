package com.example.spring313.service;


import com.example.spring313.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role findRoleById(Long id);
}
