package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.entities.Role;
import com.esgi.api_project_annuel.Domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleQuery {
    @Autowired
    RoleRepository roleRepository;

    public RoleQuery(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAll(){
        return roleRepository.findAll();
    }

    public Role getById(int id){
        return roleRepository.findById(id);
    }

    public List<Role> getByName(String roleName) {
        return roleRepository.findRoleByTitlePermission(roleName);
    }
}
