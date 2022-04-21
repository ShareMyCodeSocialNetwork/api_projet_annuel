package com.esgi.api_project_annuel.web.controller;


import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.application.command.CodeCommand;
import com.esgi.api_project_annuel.application.query.CodeQuery;
import com.esgi.api_project_annuel.application.validation.CodeValidationService;
import com.esgi.api_project_annuel.web.request.CodeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InvalidObjectException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping
public class CodeController {

    @Autowired
    private final CodeCommand codeCommand;

    @Autowired
    private final CodeQuery codeQuery;

    private CodeValidationService codeValidationService;

    public CodeController(CodeCommand codeCommand, CodeQuery codeQuery) {
        this.codeCommand = codeCommand;
        this.codeQuery = codeQuery;
    }

    @PostMapping("/code/create")
    public ResponseEntity<?> create(@RequestBody CodeRequest codeRequest){
        Code code = codeCommand.create(codeRequest);

        if(code != null) return new ResponseEntity<Code>(code, HttpStatus.CREATED);
        else return new ResponseEntity<String>("Code not created",HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/code")
    public ResponseEntity<?> getAllCodes(){

        Iterable<Code> allCodes = codeQuery.getAll();
        try {
            return new ResponseEntity<Iterable<Code>>(allCodes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Error while getting code snippets",HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("code/{codeId}")
    public ResponseEntity<?> getCodeById(@PathVariable int codeId){
        Code code = codeQuery.getById(codeId);
        if (code != null && codeId > 0) {
            return new ResponseEntity<Code>(code, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Id for this code snippet not existing",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/code/update/{codeId}")
    public ResponseEntity<?> updateCode(@PathVariable int codeId, @RequestBody Code updatedCode) throws InvalidObjectException {
        Code code = codeCommand.update(codeId, updatedCode);
        if (code != null) {
            return new ResponseEntity<Code>(code, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Check again the code to update",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/code/delete/{codeId}")
    public ResponseEntity<String> deleteCode(@PathVariable int codeId) {
        codeCommand.delete(codeId);
        return new ResponseEntity<>(
                "Code snippet " + codeId + " deleted succesfully",
                HttpStatus.NO_CONTENT
        );
    }

}
