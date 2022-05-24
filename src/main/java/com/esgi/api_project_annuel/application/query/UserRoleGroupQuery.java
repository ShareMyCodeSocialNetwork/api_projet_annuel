package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.entities.Group;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.entities.UserRoleGroup;
import com.esgi.api_project_annuel.Domain.repository.UserRoleGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleGroupQuery {
    @Autowired
    UserRoleGroupRepository userRoleGroupRepository;

    public List<UserRoleGroup> getAll(){
        return userRoleGroupRepository.findAll();
    }

    public UserRoleGroup getById(int id){
        return userRoleGroupRepository.findById(id);
    }

    public UserRoleGroup getByGroupAndUser(Group group, User user){
        return userRoleGroupRepository.findByGroupAndUser(group,user);
    }

    public List<UserRoleGroup> getAllByGroup(Group group){
        return userRoleGroupRepository.findAllByGroup(group);
    }

    public List<UserRoleGroup> getAllByUser(User user){
        return userRoleGroupRepository.findAllByUser(user);
    }

}
