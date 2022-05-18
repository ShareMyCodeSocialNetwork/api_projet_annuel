package com.esgi.api_project_annuel.web.controller;

import com.esgi.api_project_annuel.Domain.entities.Language;
import com.esgi.api_project_annuel.application.command.LanguageCommand;
import com.esgi.api_project_annuel.application.query.LanguageQuery;
import com.esgi.api_project_annuel.web.request.LanguageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping
public class LanguageController {

    @Autowired
    private final LanguageCommand languageCommand;

    @Autowired
    private final LanguageQuery languageQuery;


    public LanguageController(LanguageCommand languageCommand, LanguageQuery languageQuery) {
        this.languageCommand = languageCommand;
        this.languageQuery = languageQuery;
    }


    @PostMapping("/language/create")
    public ResponseEntity<?> create(@RequestBody LanguageRequest languageRequest){
        Language language = languageCommand.create(languageRequest);

        if(language != null) return new ResponseEntity<>(language, HttpStatus.CREATED);
        else return new ResponseEntity<>("Programming Language not created",HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/language")
    public ResponseEntity<?> getAllProgrammingLanguages(){

        Iterable<Language> allLanguages = languageQuery.getAll();
        try {
            return new ResponseEntity<>(allLanguages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while getting programming languages",HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/language/{languageId}")
    public ResponseEntity<?> getLanguageById(@PathVariable int languageId){
        Language language = languageQuery.getById(languageId);
        if (language != null && languageId > 0) {
            return new ResponseEntity<>(language, HttpStatus.OK);
        }
        return new ResponseEntity<>("Id for this programming language not existing",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/language/update/{languageId}")
    public ResponseEntity<?> updateLanguage(@PathVariable int languageId, @RequestBody LanguageRequest updatedLanguage) {
        Language language = languageCommand.update(languageId, updatedLanguage);
        if (language != null) {
            return new ResponseEntity<>(language, HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/language/delete/{languageId}")
    public ResponseEntity<String> deleteLanguage(@PathVariable int languageId) {
        languageCommand.delete(languageId);
        return new ResponseEntity<>(
                "Programming Language " + languageId + " deleted successfully",
                HttpStatus.NO_CONTENT
        );
    }





}
