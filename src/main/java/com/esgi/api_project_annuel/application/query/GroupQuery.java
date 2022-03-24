package com.esgi.api_project_annuel.application.query;


import com.esgi.api_project_annuel.Domain.entities.Group;
import com.esgi.api_project_annuel.Domain.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupQuery {

    @Autowired
    GroupRepository groupRepository;

    public GroupQuery(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }


    public List<Group> getAll() {
        return this.groupRepository.findAll();
    }

    public Group getById(int id) {return this.groupRepository.findById(id);}
}