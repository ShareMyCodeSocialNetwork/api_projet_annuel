package com.esgi.api_project_annuel.application.command;


import com.esgi.api_project_annuel.Domain.entities.Group;
import com.esgi.api_project_annuel.Domain.repository.GroupRepository;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import com.esgi.api_project_annuel.application.validation.GroupValidationService;
import com.esgi.api_project_annuel.web.request.GroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.Optional;


@Service
public class GroupCommand {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    GroupValidationService groupValidationService = new GroupValidationService();

    public Group create(GroupRequest groupRequest) throws InvalidObjectException {
        Group group = new Group();
        group.setGroupName(groupRequest.nameOfGroup);
        if (!groupValidationService.isValid(group)){
            throw new InvalidObjectException("Invalid user properties");
        }
        return groupRepository.save(group);
    }


    public Group update(int groupId, Group updateGroup) throws InvalidObjectException {
        Optional<Group> groupFromDB = Optional.ofNullable(groupRepository.findById(groupId));
        if (!groupValidationService.isValid(updateGroup)){
            throw new InvalidObjectException("Invalid user properties");
        }
        updateGroup.setId(groupFromDB.get().getId());
        return groupRepository.save(updateGroup);
    }


    public void delete(int groupId) {
        Optional<Group> groupFromDb = Optional.ofNullable(groupRepository.findById(groupId));
        if (groupFromDb.isEmpty()) {
            throw new RuntimeException("demand not found on id " + groupId);
        }
        Group group = groupFromDb.get();
        groupRepository.delete(group);
    }
    
}
