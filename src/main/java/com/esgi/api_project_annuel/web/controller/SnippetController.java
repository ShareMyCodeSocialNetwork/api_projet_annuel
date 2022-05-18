package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Snippet;
import com.esgi.api_project_annuel.application.command.SnippetCommand;
import com.esgi.api_project_annuel.application.query.SnippetQuery;
import com.esgi.api_project_annuel.web.request.SnippetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InvalidObjectException;

@CrossOrigin(origins = "*")
@RestController
public class SnippetController {

    @Autowired
    private final SnippetCommand snippetCommand;

    @Autowired
    private final SnippetQuery snippetQuery;


    public SnippetController(SnippetCommand snippetCommand, SnippetQuery snippetQuery) {
        this.snippetCommand = snippetCommand;
        this.snippetQuery = snippetQuery;
    }

    @PostMapping("/snippet/create")
    public ResponseEntity<?> create(@RequestBody SnippetRequest snippetRequest){
        Snippet snippet = snippetCommand.create(snippetRequest);

        if(snippet != null) return new ResponseEntity<Snippet>(snippet, HttpStatus.CREATED);
        else return new ResponseEntity<String>("Snippet not created",HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/snippet")
    public ResponseEntity<?> getAllSnippets(){

        Iterable<Snippet> allSnippets = snippetQuery.getAll();
        try {
            return new ResponseEntity<Iterable<Snippet>>(allSnippets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Error while getting Snippet snippets",HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/snippet/{snippetId}")
    public ResponseEntity<?> getSnippetById(@PathVariable int snippetId){
        Snippet snippet = snippetQuery.getById(snippetId);
        if (snippet != null && snippetId > 0) {
            return new ResponseEntity<Snippet>(snippet, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Id for this Snippet snippet not existing",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/snippet/update/{SnippetId}")
    public ResponseEntity<?> updateSnippet(@PathVariable int snippetId, @RequestBody Snippet updatedSnippet) throws InvalidObjectException {
        Snippet snippet = snippetCommand.update(snippetId, updatedSnippet);
        if (snippet != null) {
            return new ResponseEntity<Snippet>(snippet, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Check again the Snippet to update",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/snippet/delete/{SnippetId}")
    public ResponseEntity<String> deleteSnippet(@PathVariable int snippetId) {
        snippetCommand.delete(snippetId);
        return new ResponseEntity<>(
                "Snippet snippet " + snippetId + " deleted succesfully",
                HttpStatus.NO_CONTENT
        );
    }

}
