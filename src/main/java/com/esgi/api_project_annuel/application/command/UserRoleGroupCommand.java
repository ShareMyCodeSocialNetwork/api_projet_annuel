package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.repository.UserRoleGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleGroupCommand {
    @Autowired
    UserRoleGroupRepository userRoleGroupRepository;
}
