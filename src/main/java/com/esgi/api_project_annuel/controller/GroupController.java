package com.esgi.api_project_annuel.controller;

import com.esgi.api_project_annuel.model.Group;
import com.esgi.api_project_annuel.service.GroupService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InvalidObjectException;
import java.util.List;

@RestController
@RequestMapping(value = "/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {this.groupService = groupService;}

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.getGroups();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping({"/{groupId}"})
    public ResponseEntity<Group> getGroup(@PathVariable Long groupId) {
        return new ResponseEntity<>(groupService.getGroupById(groupId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Group> saveGroup(@RequestBody Group group) throws InvalidObjectException {
        Group groupCreated = groupService.createGroup(group);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("group", "/group/" + groupCreated.getId());
        return new ResponseEntity<>(groupCreated, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping({"/{groupId}"})
    public ResponseEntity<Group> updateGroup(@PathVariable("groupId") Long groupId, @RequestBody Group group) throws InvalidObjectException {
        groupService.updateGroup(groupId, group);
        return new ResponseEntity<>(groupService.getGroupById(groupId), HttpStatus.OK);
    }

    @DeleteMapping({"/{groupId}"})
    public ResponseEntity<Group> deleteGroup(@PathVariable("groupId") Long groupId) {
        groupService.deleteGroup(groupId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
