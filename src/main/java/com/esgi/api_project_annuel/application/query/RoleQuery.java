package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleQuery {
    @Autowired
    RoleRepository roleRepository;

    public RoleQuery(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
