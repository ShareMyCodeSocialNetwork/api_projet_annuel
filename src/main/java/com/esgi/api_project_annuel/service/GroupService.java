package com.esgi.api_project_annuel.service;

import com.esgi.api_project_annuel.integration.Group;

import java.io.InvalidObjectException;
import java.util.List;

public interface GroupService {
    List<Group> getGroups();

    Group getGroupById(long id);

    Group createGroup(Group group) throws InvalidObjectException;


    void updateGroup(long id, Group group) throws InvalidObjectException;

    void deleteGroup(long id);
}
