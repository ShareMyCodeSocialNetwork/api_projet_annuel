package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleCommand {
    @Autowired
    RoleRepository roleRepository;
}
