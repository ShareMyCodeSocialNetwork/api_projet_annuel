package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.Role;
import com.esgi.api_project_annuel.Domain.repository.RoleRepository;
import com.esgi.api_project_annuel.application.validation.RoleValidationService;
import com.esgi.api_project_annuel.web.request.RoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleCommand {
    @Autowired
    RoleRepository roleRepository;

    RoleValidationService roleValidationService = new RoleValidationService();

    public RoleCommand(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role create(RoleRequest roleRequest){
        var role = new Role();
        role.setName(roleRequest.name);
        if (!roleValidationService.isValid(role))
            return null;
        return roleRepository.save(role);
    }


    public void delete(int id){
        roleRepository.delete(roleRepository.findById(id));
    }

}
