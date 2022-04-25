package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Role;
import com.esgi.api_project_annuel.application.command.RoleCommand;
import com.esgi.api_project_annuel.application.query.RoleQuery;
import com.esgi.api_project_annuel.web.request.RoleRequest;
import com.esgi.api_project_annuel.web.response.RoleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleCommand roleCommand;

    @Autowired
    RoleQuery roleQuery;

    public RoleController(RoleCommand roleCommand, RoleQuery roleQuery) {
        this.roleCommand = roleCommand;
        this.roleQuery = roleQuery;
    }


    @RequestMapping("/create")
    public ResponseEntity<RoleResponse> addRole(@RequestBody RoleRequest roleRequest){
        var role = roleCommand.create(roleRequest);
        if(role == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(roleToRoleResponse(role), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<RoleResponse>> getAll(){
        return new ResponseEntity<>(
                this.listRoleToListRoleResponse(roleQuery.getAll()),
                HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable int roleId){
        var role = roleQuery.getById(roleId);
        if(role == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(roleToRoleResponse(
                role),
                HttpStatus.OK
        );
    }

    @GetMapping("/{roleName}")
    public ResponseEntity<RoleResponse> getRoleByName(@PathVariable String roleName){
        var role = roleQuery.getByName(roleName);
        if(role == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(roleToRoleResponse(
                role),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{roleId}")
    public ResponseEntity<?> changeContent(@PathVariable int roleId,@RequestBody RoleRequest roleRequest){
        var role = roleCommand.changeName(roleRequest,roleId);
        if(role == null)
            return new ResponseEntity<>("Invalid properties", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(roleToRoleResponse(role), HttpStatus.OK);
    }

    //todo: quand userrolegroup sera fait, a prendre ne compte les liens de ce role dans les autres tables
    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable int roleId){
        var role = roleQuery.getById(roleId);
        if(role == null)
            return new ResponseEntity<>(
                    "Role " + roleId + " not exist",
                    HttpStatus.BAD_REQUEST
            );
        roleCommand.delete(roleId);
        return new ResponseEntity<>(
                "Role " + roleId + " deleted",
                HttpStatus.BAD_REQUEST
        );
    }






    private RoleResponse roleToRoleResponse(Role role){
        return new RoleResponse()
                .setId(role.getId())
                .setName(role.getName());
    }

    private List<RoleResponse> listRoleToListRoleResponse(List<Role> roles){
        List<RoleResponse> roleResponses = new ArrayList<>();
        roles.forEach(role -> roleResponses.add(this.roleToRoleResponse(role)));
        return roleResponses;
    }
}
