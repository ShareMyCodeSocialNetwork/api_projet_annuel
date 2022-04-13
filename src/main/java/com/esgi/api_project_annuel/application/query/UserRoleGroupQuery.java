package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.repository.UserRoleGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleGroupQuery {
    @Autowired
    UserRoleGroupRepository userRoleGroupRepository;

    public UserRoleGroupQuery(UserRoleGroupRepository userRoleGroupRepository) {
        this.userRoleGroupRepository = userRoleGroupRepository;
    }
}
