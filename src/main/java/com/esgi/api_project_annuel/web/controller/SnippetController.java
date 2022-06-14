package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Snippet;
import com.esgi.api_project_annuel.application.command.SnippetCommand;
import com.esgi.api_project_annuel.application.query.LanguageQuery;
import com.esgi.api_project_annuel.application.query.SnippetQuery;
import com.esgi.api_project_annuel.application.query.UserQuery;
import com.esgi.api_project_annuel.web.request.SnippetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
public class SnippetController {

    @Autowired
    private final SnippetCommand snippetCommand;

    @Autowired
    private final SnippetQuery snippetQuery;
    @Autowired
    LanguageQuery languageQuery;
    @Autowired
    UserQuery userQuery;


    public SnippetController(SnippetCommand snippetCommand, SnippetQuery snippetQuery) {
        this.snippetCommand = snippetCommand;
        this.snippetQuery = snippetQuery;
    }

    @PostMapping("/snippet/create")
    public ResponseEntity<?> create(@RequestBody SnippetRequest snippetRequest){
        Snippet snippet = snippetCommand.create(snippetRequest);

        if(snippet != null) return new ResponseEntity<>(snippet, HttpStatus.CREATED);
        else return new ResponseEntity<>("Snippet not created",HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/snippet")
    public ResponseEntity<?> getAllSnippets(){
        return new ResponseEntity<>(snippetQuery.getAll(), HttpStatus.OK);
    }

    @GetMapping("/snippet/user/{userId}")
    public ResponseEntity<?> getAllSnippetsByUser(@PathVariable int userId){
        return new ResponseEntity<>(
                snippetQuery.getAllByUser(
                        userQuery.getById(userId)
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/snippet/{snippetId}")
    public ResponseEntity<?> getSnippetById(@PathVariable int snippetId){
        Snippet snippet = snippetQuery.getById(snippetId);
        if (snippet != null && snippetId > 0) {
            return new ResponseEntity<>(snippet, HttpStatus.OK);
        }
        return new ResponseEntity<>("Id for this Snippet snippet not existing",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/snippet/update/{snippetId}")
    public ResponseEntity<?> updateSnippet(@PathVariable int snippetId, @RequestBody SnippetRequest updatedSnippet) {
        var language = languageQuery.getById(updatedSnippet.language_id);
        Snippet snippet = snippetCommand.update(snippetId, updatedSnippet, language);
        if (snippet != null) {
            return new ResponseEntity<>(snippet, HttpStatus.OK);
        }
        return new ResponseEntity<>("Check again the Snippet to update",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/snippet/delete/{snippetId}")
    public ResponseEntity<String> deleteSnippet(@PathVariable int snippetId) {
        var snippet = snippetQuery.getById(snippetId);
        if(snippet == null)
            return new ResponseEntity<>(
                    "Snippet " + snippetId + " not exist",
                    HttpStatus.NOT_FOUND
            );
        snippetCommand.delete(snippetId);
        return new ResponseEntity<>(
                "Snippet snippet " + snippetId + " deleted succesfully",
                HttpStatus.NO_CONTENT
        );
    }

}
