package com.esgi.api_project_annuel.web.controller;



import com.esgi.api_project_annuel.Domain.entities.Group;
import com.esgi.api_project_annuel.application.command.GroupCommand;
import com.esgi.api_project_annuel.application.query.GroupQuery;
import com.esgi.api_project_annuel.web.request.GroupRequest;
import com.esgi.api_project_annuel.web.response.GroupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private final GroupCommand groupCommand;

    @Autowired
    private final GroupQuery groupQuery;

    public GroupController(GroupCommand groupCommand, GroupQuery demandQuery){
        this.groupCommand = groupCommand;
        this.groupQuery = demandQuery;
    }

    @PostMapping("/create")
    public ResponseEntity<GroupResponse> addGroup(@RequestBody GroupRequest groupRequest) {
        var group = groupCommand.create(groupRequest);
        if(group != null)
            return new ResponseEntity<>(groupToGroupResponse(group), HttpStatus.CREATED);
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "/", produces = { MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
    public ResponseEntity<List<GroupResponse>> getGroupAll(){
        return new ResponseEntity<>(
                listGroupToListGroupResponse(groupQuery.getAll()),
                HttpStatus.OK);
    }

    @GetMapping("/name/{groupName}")
    public ResponseEntity<List<GroupResponse>> getRoleByName(@PathVariable String groupName){
        return new ResponseEntity<>(listGroupToListGroupResponse(
                groupQuery.getByName(groupName)),
                HttpStatus.OK
        );
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> getGroupById(@PathVariable int groupId) {
        var group = groupQuery.getById(groupId);
        if(group == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(groupToGroupResponse(
                group),
                HttpStatus.OK
        );
    }

    @PatchMapping("/change_name/{groupId}")
    public ResponseEntity<GroupResponse> changeName(@PathVariable int groupId, @RequestBody GroupRequest groupRequest) {
        var group = groupQuery.getById(groupId);
        if(group == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        var updatedGroup = groupCommand.changeName(groupId, groupRequest);
        if(updatedGroup == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(
                groupToGroupResponse(
                        updatedGroup
                ),
                HttpStatus.OK
        );
    }


    @PutMapping("/update/{groupId}")
    public ResponseEntity<GroupResponse> updateGroup(@PathVariable int groupId, @RequestBody GroupRequest groupRequest) {
        var group = groupCommand.update(groupId, groupRequest);
        if (group != null)
            return new ResponseEntity<>(groupToGroupResponse(group), HttpStatus.OK);
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping("/{groupId}")
    public ResponseEntity<String> deleteGroup(@PathVariable int groupId) {
        var group = groupQuery.getById(groupId);
        if(group == null)
            return new ResponseEntity<>(
                    "Role " + groupId + " not exist",
                    HttpStatus.BAD_REQUEST
            );
        groupCommand.delete(groupId);
        return new ResponseEntity<>(
                "group " + groupId + " deleted",
                HttpStatus.NO_CONTENT
        );
    }



    private GroupResponse groupToGroupResponse(Group group){
        return new GroupResponse()
                .setId(group.getId())
                .setName(group.getName());
    }

    private List<GroupResponse> listGroupToListGroupResponse(List<Group> groups){
        List<GroupResponse> groupResponses = new ArrayList<>();
        groups.forEach(group -> groupResponses.add(groupToGroupResponse(group)));
        return groupResponses;
    }

}
