package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.UserRoleGroup;
import com.esgi.api_project_annuel.application.command.UserRoleGroupCommand;
import com.esgi.api_project_annuel.application.query.GroupQuery;
import com.esgi.api_project_annuel.application.query.RoleQuery;
import com.esgi.api_project_annuel.application.query.UserRoleGroupQuery;
import com.esgi.api_project_annuel.application.query.UserQuery;
import com.esgi.api_project_annuel.web.request.UserRoleGroupRequest;
import com.esgi.api_project_annuel.web.response.UserRoleGroupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/user_role_group")
public class UserRoleGroupController {

    @Autowired
    UserRoleGroupCommand userRoleGroupCommand;
    @Autowired
    UserRoleGroupQuery userRoleGroupQuery;
    @Autowired
    UserQuery userQuery;
    @Autowired
    RoleQuery roleQuery;
    @Autowired
    GroupQuery groupQuery;

    public UserRoleGroupController(UserRoleGroupCommand userRoleGroupCommand, UserRoleGroupQuery userRoleGroupQuery, UserQuery userQuery, RoleQuery roleQuery, GroupQuery groupQuery) {
        this.userRoleGroupCommand = userRoleGroupCommand;
        this.userRoleGroupQuery = userRoleGroupQuery;
        this.userQuery = userQuery;
        this.roleQuery = roleQuery;
        this.groupQuery = groupQuery;
    }

    @RequestMapping("/create")
    public ResponseEntity<UserRoleGroupResponse> addUserRoleGroup(@RequestBody UserRoleGroupRequest userRoleGroupRequest){
        var user = userQuery.getById(userRoleGroupRequest.user_id);
        var role = roleQuery.getById(userRoleGroupRequest.role_id);
        var group = groupQuery.getById(userRoleGroupRequest.group_id);
        var userRoleGroup = userRoleGroupCommand.create(userRoleGroupRequest, user, role, group);
        if(userRoleGroup == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userRoleGroupToUserRoleGroupResponse(userRoleGroup), HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<List<UserRoleGroupResponse>> getAll(){
        return new ResponseEntity<>(
                this.listUserRoleGroupToListUserRoleGroupResponse(userRoleGroupQuery.getAll()),
                HttpStatus.OK);
    }

    @GetMapping("/{userRoleGroupId}")
    public ResponseEntity<UserRoleGroupResponse> getUserRoleGroupById(@PathVariable int userRoleGroupId){
        var userRoleGroup = userRoleGroupQuery.getById(userRoleGroupId);
        if(userRoleGroup == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userRoleGroupToUserRoleGroupResponse(
                userRoleGroup),
                HttpStatus.OK
        );
    }

    @GetMapping("/group/{groupId}/user/{userId}")
    public ResponseEntity<UserRoleGroupResponse> getUserRoleGroupByPost(@PathVariable int groupId, @PathVariable int userId){
        var user = userQuery.getById(userId);
        var group = groupQuery.getById(groupId);
        var userRoleGroup = userRoleGroupQuery.getByGroupAndUser(group,user);
        if(userRoleGroup == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userRoleGroupToUserRoleGroupResponse(
                userRoleGroup),
                HttpStatus.OK
        );
    }


    @DeleteMapping("/{userRoleGroupId}")
    public ResponseEntity<?> deleteUserRoleGroup(@PathVariable int userRoleGroupId){
        var userRoleGroup = userRoleGroupQuery.getById(userRoleGroupId);
        if(userRoleGroup == null)
            return new ResponseEntity<>(
                    "UserRoleGroup " + userRoleGroupId + " not exist",
                    HttpStatus.BAD_REQUEST
            );
        userRoleGroupCommand.delete(userRoleGroupId);
        return new ResponseEntity<>(
                "UserRoleGroup " + userRoleGroupId + " deleted",
                HttpStatus.OK
        );
    }






    private UserRoleGroupResponse userRoleGroupToUserRoleGroupResponse(UserRoleGroup userRoleGroup){
        return new UserRoleGroupResponse()
                .setId(userRoleGroup.getId())
                .setGroup(userRoleGroup.getGroup())
                .setUser(userRoleGroup.getUser())
                .setRole(userRoleGroup.getRole());
    }

    private List<UserRoleGroupResponse> listUserRoleGroupToListUserRoleGroupResponse(List<UserRoleGroup> userRoleGroups){
        List<UserRoleGroupResponse> userRoleGroupResponses = new ArrayList<>();
        userRoleGroups.forEach(userRoleGroup -> userRoleGroupResponses.add(this.userRoleGroupToUserRoleGroupResponse(userRoleGroup)));
        return userRoleGroupResponses;
    }

}
