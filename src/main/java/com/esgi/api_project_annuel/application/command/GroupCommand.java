package com.esgi.api_project_annuel.application.command;


import com.esgi.api_project_annuel.Domain.entities.Group;
import com.esgi.api_project_annuel.Domain.repository.GroupRepository;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import com.esgi.api_project_annuel.application.validation.GroupValidationService;
import com.esgi.api_project_annuel.web.request.GroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.Objects;
import java.util.Optional;


@Service
public class GroupCommand {

    @Autowired
    GroupRepository groupRepository;

    GroupValidationService groupValidationService = new GroupValidationService();

    public Group create(GroupRequest groupRequest) {
        var group = new Group();
        group.setGroupName(groupRequest.name);
        if (!groupValidationService.isValid(group))
            return null;
        return groupRepository.save(group);
    }


    public Group update(int groupId, GroupRequest groupRequest) {
        if (Objects.equals(groupRequest.name, "") || groupRequest.name.isBlank())
            return null;
        Optional<Group> groupFromDB = Optional.ofNullable(groupRepository.findById(groupId));
        if(groupFromDB.isPresent()) {
            groupFromDB.get().setGroupName(groupRequest.name);
            if (groupValidationService.isValid(groupFromDB.get()))
                return groupRepository.save(groupFromDB.get());;
        }
        return null;
    }


    public void delete(int groupId) {
        groupRepository.delete(groupRepository.findById(groupId));
    }
    
}
