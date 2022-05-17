package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.Language;
import com.esgi.api_project_annuel.Domain.entities.Snippet;
import com.esgi.api_project_annuel.Domain.entities.Snippet;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.LanguageRepository;
import com.esgi.api_project_annuel.Domain.repository.SnippetRepository;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import com.esgi.api_project_annuel.application.validation.SnippetValidationService;
import com.esgi.api_project_annuel.web.request.SnippetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.Optional;

@Service
public class SnippetCommand {

    @Autowired
    SnippetRepository snippetRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    LanguageRepository languageRepository;
    SnippetValidationService snippetValidationService = new SnippetValidationService();

    public Snippet create(SnippetRequest snippetRequest){
        Snippet snippet = new Snippet();
        snippet.setName(snippetRequest.name);
        snippet.setContent(snippetRequest.content);
        if(snippetRequest.user_id !=0 && userRepository.existsById(snippetRequest.user_id)){
            User user = userRepository.findById(snippetRequest.user_id);
            snippet.setUser(user);
        }
        if(snippetRequest.language_id !=0 && languageRepository.existsById(snippetRequest.language_id)){
            Language language = languageRepository.findById(snippetRequest.language_id);
            snippet.setLanguage(language);
        }
        if(!snippetValidationService.snippetIsValid(snippet)) throw new RuntimeException("Invalid Snippet snippet properties");
        return snippetRepository.save(snippet);
    }

    public Snippet update(int snippetId, Snippet updatedSnippet) throws InvalidObjectException {
        Optional<Snippet> snippetFromDB = Optional.ofNullable(snippetRepository.findById(snippetId));
        if(!snippetValidationService.snippetIsValid(updatedSnippet)){
            throw new InvalidObjectException("Invalid SnippetId properties");
        }
        if (snippetFromDB.isEmpty()) {
            throw new InvalidObjectException("Invalid SnippetId properties");
        }
        updatedSnippet.setId(snippetFromDB.get().getId());
        return snippetRepository.save(updatedSnippet);
    }

    public void delete(int snippetId) {
        Optional<Snippet> snippetFromDB = Optional.ofNullable(snippetRepository.findById(snippetId));
        if (snippetFromDB.isEmpty()) {
            throw new RuntimeException("Snippet snippet not found with id " + snippetId);
        }
        Snippet snippet = snippetFromDB.get();
        snippetRepository.delete(snippet);
    }
}

