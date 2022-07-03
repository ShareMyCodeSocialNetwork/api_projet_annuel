package com.esgi.api_project_annuel.web.controller;


import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.application.command.CodeCommand;
import com.esgi.api_project_annuel.application.query.CodeQuery;
import com.esgi.api_project_annuel.application.query.LanguageQuery;
import com.esgi.api_project_annuel.application.query.ProjectQuery;
import com.esgi.api_project_annuel.application.query.UserQuery;
import com.esgi.api_project_annuel.application.validation.CodeValidationService;
import com.esgi.api_project_annuel.application.validation.ProjectValidationService;
import com.esgi.api_project_annuel.web.request.CodeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InvalidObjectException;
import java.util.Optional;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping
public class CodeController {

    @Autowired
    private final CodeCommand codeCommand;

    @Autowired
    private final CodeQuery codeQuery;
    @Autowired
    LanguageQuery languageQuery;
    @Autowired
    UserQuery userQuery;
    @Autowired
    ProjectQuery projectQuery;


    public CodeController(CodeCommand codeCommand, CodeQuery codeQuery) {
        this.codeCommand = codeCommand;
        this.codeQuery = codeQuery;
    }

    @PostMapping("/code/create")
    public ResponseEntity<?> create(@RequestBody CodeRequest codeRequest){
        Code code;
        var language = languageQuery.getById(codeRequest.language_id);
        var user = userQuery.getById(codeRequest.userId);
        if(Optional.ofNullable(codeRequest.project_id).orElse(0) != 0){
            var project = projectQuery.getById(codeRequest.project_id);
             code = codeCommand.create(codeRequest,language,user,project);
        }else{
             code = codeCommand.createNotProject(codeRequest,language,user);
        }

        if(code != null) return new ResponseEntity<>(code, HttpStatus.CREATED);
        else return new ResponseEntity<>("Code not created",HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/code")
    public ResponseEntity<?> getAllCodes(){
        return new ResponseEntity<>(codeQuery.getAll(), HttpStatus.OK);
    }

    @GetMapping("/code/{codeId}")
    public ResponseEntity<?> getCodeById(@PathVariable int codeId){
        Code code = codeQuery.getById(codeId);
        if (code != null) {
            return new ResponseEntity<>(code, HttpStatus.OK);
        }
        return new ResponseEntity<>("Id for this code snippet not existing",HttpStatus.NOT_FOUND);
    }
    @GetMapping("/code/project/{projectId}")
    public ResponseEntity<?> getCodeByProject(@PathVariable int projectId){
        var project = projectQuery.getById(projectId);
        return new ResponseEntity<>(
                codeQuery.findAllByProject(project),
                HttpStatus.OK
        );
    }

    @PutMapping("/code/update/{codeId}")
    public ResponseEntity<?> updateCode(@PathVariable int codeId, @RequestBody CodeRequest updatedCode) {
        var language = languageQuery.getById(updatedCode.language_id);
        var project = projectQuery.getById(updatedCode.project_id);
        Code code = codeCommand.update(codeId, updatedCode,language,project);
        if (code != null) {
            return new ResponseEntity<>(code, HttpStatus.OK);
        }
        return new ResponseEntity<>("Check again the code to update",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/code/delete/{codeId}")
    public ResponseEntity<String> deleteCode(@PathVariable int codeId) {
        var code = codeQuery.getById(codeId);
        if(code == null)
            return new ResponseEntity<>(
                    "Code snippet " + codeId + " not found",
                    HttpStatus.NOT_FOUND
            );

        codeCommand.delete(codeId);
        return new ResponseEntity<>(
                "Code snippet " + codeId + " deleted successfully",
                HttpStatus.NO_CONTENT
        );
    }

}
