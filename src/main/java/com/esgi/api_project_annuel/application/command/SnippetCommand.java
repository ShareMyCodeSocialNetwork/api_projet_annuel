package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.*;
import com.esgi.api_project_annuel.Domain.entities.Snippet;
import com.esgi.api_project_annuel.Domain.repository.LanguageRepository;
import com.esgi.api_project_annuel.Domain.repository.SnippetRepository;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import com.esgi.api_project_annuel.application.query.LanguageQuery;
import com.esgi.api_project_annuel.application.validation.SnippetValidationService;
import com.esgi.api_project_annuel.web.request.SnippetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.List;
import java.util.Objects;
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
        if(!snippetValidationService.snippetIsValid(snippet)) return null;
        return snippetRepository.save(snippet);
    }

    public Snippet update(int snippetId, SnippetRequest snippetRequest, Language language)  {
        Optional<Snippet> snippetFromDB = Optional.ofNullable(snippetRepository.findById(snippetId));

        if(snippetFromDB.isPresent()){
            snippetFromDB.get().setLanguage(language == null ? snippetFromDB.get().getLanguage() : language);
            snippetFromDB.get().setName(Objects.equals(snippetRequest.name, "") ? snippetFromDB.get().getName() : snippetRequest.name);
            snippetFromDB.get().setContent(snippetRequest.content.equals("") ? snippetFromDB.get().getContent() : snippetRequest.content);

            if(snippetValidationService.snippetIsValid(snippetFromDB.get()))
                return snippetRepository.save(snippetFromDB.get());
        }
        return null;
    }

    public void delete(int snippetId) {
        Optional.ofNullable(
                snippetRepository.findById(snippetId)
        ).ifPresent(snippet ->{
            snippet.setUser(null);
            snippet.setLanguage(null);
            snippetRepository.save(snippet);
            snippetRepository.delete(snippet);
        });
    }

    public void deleteLanguage(Language language){
        var snippets = snippetRepository.getAllByLanguage(language);
        snippets.forEach(snippet -> {
            snippet.setLanguage(null);
            snippetRepository.save(snippet);
        });
    }


    public void deleteAllByUser(User user){
        Optional<List<Snippet>> dbSnippets = Optional.ofNullable(snippetRepository.getAllByUser(user));
        dbSnippets.ifPresent(snippets ->
                snippets.forEach(snippet -> {
                    snippet.setUser(null);
                    snippet.setLanguage(null);
                    snippetRepository.save(snippet);
                    snippetRepository.delete(snippet);
                })
        );
    }
}

