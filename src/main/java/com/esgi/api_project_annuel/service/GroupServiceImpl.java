package com.esgi.api_project_annuel.service;

import com.esgi.api_project_annuel.model.Group;
import com.esgi.api_project_annuel.repositories.GroupRepository;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService{

    GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> getGroups() {
        return this.groupRepository.findAll();
    }

    @Override
    public Group getGroupById(long id) {
        return this.groupRepository.findById(id).get();
    }

    @Override
    public Group createGroup(Group group) throws InvalidObjectException {
        if (!group.isValid())
            throw new InvalidObjectException("Invalid user properties");
        return groupRepository.save(group);
    }

    @Override
    public void updateGroup(long id, Group group) throws InvalidObjectException {
        Group groupFromDB = groupRepository.findById(id).get();
        groupFromDB.setName(group.getName());
        if (!groupFromDB.isValid())
            throw new InvalidObjectException("Invalid user properties");
        groupRepository.save(groupFromDB);
    }

    @Override
    public void deleteGroup(long id) {
            groupRepository.deleteById(id);
    }
}
