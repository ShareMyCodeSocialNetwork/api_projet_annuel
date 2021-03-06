package com.esgi.api_project_annuel.application.command;


import com.esgi.api_project_annuel.Domain.entities.Group;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.GroupRepository;
import com.esgi.api_project_annuel.application.validation.GroupValidationService;
import com.esgi.api_project_annuel.web.request.GroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
public class GroupCommand {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserRoleGroupCommand userRoleGroupCommand;
    @Autowired
    ProjectCommand projectCommand;
    GroupValidationService groupValidationService = new GroupValidationService();

    public Group create(GroupRequest groupRequest, User user) {
        var group = new Group();
        group.setName(groupRequest.name);
        group.setDescription(groupRequest.description);
        group.setOwner(user);
        if (!groupValidationService.isValid(group))
            return null;
        return groupRepository.save(group);
    }


    public Group update(int groupId, GroupRequest groupRequest) {
        Optional<Group> groupFromDB = Optional.ofNullable(groupRepository.findById(groupId));
        if(groupFromDB.isPresent()) {
            groupFromDB.get().setName(groupRequest.name);
            groupFromDB.get().setDescription(groupRequest.description);
            if (groupValidationService.isValid(groupFromDB.get()))
                return groupRepository.save(groupFromDB.get());
        }
        return null;
    }


    public Group changeName(int groupId, GroupRequest groupRequest){
        Optional<Group> dbGroup = Optional.ofNullable(groupRepository.findById(groupId));
        if(dbGroup.isPresent()){
            dbGroup.get().setName(groupRequest.name);
            if(groupValidationService.isValid(dbGroup.get()))
                return groupRepository.save(dbGroup.get());
        }
        return null;
    }

    public Group changeDescription(int groupId, GroupRequest groupRequest){
        Optional<Group> dbGroup = Optional.ofNullable(groupRepository.findById(groupId));
        if(dbGroup.isPresent()){
            dbGroup.get().setDescription(groupRequest.description);
            if(groupValidationService.isValid(dbGroup.get()))
                return groupRepository.save(dbGroup.get());
        }
        return null;
    }

    public void delete(int groupId) {
        var group = groupRepository.findById(groupId);
        group.setOwner(null);
        groupRepository.save(group);
        projectCommand.deleteAllProjectsGroup(group);
        userRoleGroupCommand.deleteAllByGroup(group);
        groupRepository.delete(group);
    }
    
}
