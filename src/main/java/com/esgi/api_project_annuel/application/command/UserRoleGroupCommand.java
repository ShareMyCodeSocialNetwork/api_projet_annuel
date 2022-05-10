package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.*;
import com.esgi.api_project_annuel.Domain.repository.UserRoleGroupRepository;
import com.esgi.api_project_annuel.application.query.RoleQuery;
import com.esgi.api_project_annuel.application.validation.UserRoleGroupValidationService;
import com.esgi.api_project_annuel.web.request.UserRoleGroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleGroupCommand {
    @Autowired
    UserRoleGroupRepository userRoleGroupRepository;

    @Autowired
    RoleQuery roleQuery;

    UserRoleGroupValidationService userRoleGroupValidationService = new UserRoleGroupValidationService();

    public UserRoleGroup create(UserRoleGroupRequest userRoleGroupRequest, User user, Role role, Group group){
        UserRoleGroup userRoleGroup = new UserRoleGroup();
        userRoleGroup.setUser(user);
        userRoleGroup.setGroup(group);
        userRoleGroup.setRole(role);
        var userRoleGroupCheck = userRoleGroupRepository.findByGroupAndUser(group, user);
        if(userRoleGroupCheck == null)
            if(userRoleGroupValidationService.isValid(userRoleGroup))
                return userRoleGroupRepository.save(userRoleGroup);
        return null;
    }


    public UserRoleGroup changeUserRole(int userRoleGroupId,UserRoleGroupRequest userRoleGroupRequest){
        Optional<UserRoleGroup> userRoleGroupFromDB = Optional.ofNullable(userRoleGroupRepository.findById(userRoleGroupId));
        if(userRoleGroupFromDB.isPresent()){
            var userRoleGroup = userRoleGroupFromDB.get();
            userRoleGroup.setRole(roleQuery.getById(userRoleGroupRequest.role_id));
            if(userRoleGroupValidationService.isValid(userRoleGroup))
                return userRoleGroupRepository.save(userRoleGroup);
        }
        return null;
    }

    public void delete(int userRoleGroupId){
        Optional<UserRoleGroup> userRoleGroup = Optional.ofNullable(userRoleGroupRepository.findById(userRoleGroupId));
        userRoleGroup.ifPresent(userRoleGroup1 -> {
            userRoleGroup.get().setRole(null);
            userRoleGroup.get().setGroup(null);
            userRoleGroup.get().setUser(null);
            userRoleGroupRepository.save(userRoleGroup.get());
            userRoleGroupRepository.delete(userRoleGroup.get());
        });
    }


    public void deleteAllByUser(User user){
        var userRoleGroups = userRoleGroupRepository.findAllByUser(user);
        userRoleGroups.forEach(userRoleGroup -> {
            userRoleGroup.setGroup(null);
            userRoleGroup.setRole(null);
            userRoleGroup.setUser(null);
            userRoleGroupRepository.save(userRoleGroup);
            userRoleGroupRepository.delete(userRoleGroup);
        });
    }

    public void deleteAllByGroup(Group group){
        var userRoleGroups = userRoleGroupRepository.findAllByGroup(group);
        userRoleGroups.forEach(userRoleGroup -> {
            userRoleGroup.setGroup(null);
            userRoleGroup.setRole(null);
            userRoleGroup.setUser(null);
            userRoleGroupRepository.save(userRoleGroup);
            userRoleGroupRepository.delete(userRoleGroup);
        });
    }

    public void deleteAllByRole(Role role){
        var userRoleGroups = userRoleGroupRepository.findAllByRole(role);
        userRoleGroups.forEach(userRoleGroup -> {
            userRoleGroup.setGroup(null);
            userRoleGroup.setRole(null);
            userRoleGroup.setUser(null);
            userRoleGroupRepository.save(userRoleGroup);
            userRoleGroupRepository.delete(userRoleGroup);
        });
    }
}
