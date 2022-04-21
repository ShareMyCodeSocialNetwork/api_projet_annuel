package com.esgi.api_project_annuel.web.controller;



import com.esgi.api_project_annuel.Domain.entities.Group;
import com.esgi.api_project_annuel.application.command.GroupCommand;
import com.esgi.api_project_annuel.application.query.GroupQuery;
import com.esgi.api_project_annuel.application.validation.GroupValidationService;
import com.esgi.api_project_annuel.web.request.GroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.io.InvalidObjectException;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping
public class GroupController {

    @Autowired
    private final GroupCommand groupCommand;

    @Autowired
    private final GroupQuery groupQuery;

    private GroupValidationService groupValidationService;


    public GroupController(GroupCommand groupCommand, GroupQuery demandQuery){
        this.groupCommand = groupCommand;
        this.groupQuery = demandQuery;
    }

    @PostMapping("/group/create")
    public ResponseEntity<?> addGroup(@RequestBody GroupRequest groupRequest) throws InvalidObjectException {
        Group group = groupCommand.create(groupRequest);
        if(group != null)
        {
            return new ResponseEntity<Group>(group, HttpStatus.CREATED);

        }else{

            return new ResponseEntity<String>("group not created",HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(value = "/group", produces = { MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
    public ResponseEntity<?> getGroupAll(){
        Iterable<Group> groupAll = groupQuery.getAll();
        try {
            return new ResponseEntity<Iterable<Group>>(groupAll, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Error de recuperation des utilisateurs",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<?> getGroupById(@PathVariable int groupId) {
        Group group = groupQuery.getById(groupId);
        if (group != null && groupId > 0) {
            return new ResponseEntity<Group>(group, HttpStatus.OK);
        }
        return new ResponseEntity<String>("L'id de cette utilisateur n'existe pas",HttpStatus.NOT_FOUND);
    }



    @PutMapping("/group/update/{groupId}")
    public ResponseEntity<?> updateGroup(@PathVariable int groupId, @RequestBody Group updatedgroup) throws InvalidObjectException {
        Group group = groupCommand.update(groupId, updatedgroup);
        if (group != null) {
            return new ResponseEntity<Group>(group, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Verifier le body ou l'entete envoyer",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/group/delete/{groupId}")
    public ResponseEntity<String> deleteGroup(@PathVariable int groupId) {
        groupCommand.delete(groupId);
        return new ResponseEntity<>(
                "group " + groupId + " deleted",
                HttpStatus.NO_CONTENT
        );
    }

}
