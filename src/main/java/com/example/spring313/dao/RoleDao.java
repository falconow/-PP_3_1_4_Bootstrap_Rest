package com.example.spring313.dao;



import com.example.spring313.model.Role;

import java.util.List;

public interface RoleDao {
    List<Role> getAllRole();
    Role findRoleById(Long id);
    void addRole(Role role);
}
