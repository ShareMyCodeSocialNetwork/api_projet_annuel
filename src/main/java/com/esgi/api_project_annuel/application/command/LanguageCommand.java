package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.Language;
import com.esgi.api_project_annuel.Domain.repository.LanguageRepository;
import com.esgi.api_project_annuel.application.validation.LanguageValidationService;
import com.esgi.api_project_annuel.web.request.LanguageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.Optional;

@Service
public class LanguageCommand {

    @Autowired
    LanguageRepository languageRepository;

    LanguageValidationService languageValidationService = new LanguageValidationService();

    public Language create(LanguageRequest languageRequest){

        Language language = new Language();

        language.setName(languageRequest.name);

        if(!languageValidationService.languageIsValid(language)) throw new RuntimeException("Invalid user properties");

        return languageRepository.save(language);
    }

    public Language update(int languageId, LanguageRequest updatedLanguage) {

        Optional<Language> languageFromDB = Optional.ofNullable(languageRepository.findById(languageId));

        if(languageFromDB.isPresent()){
            languageFromDB.get().setName(updatedLanguage.name);
            if(languageValidationService.languageIsValid(languageFromDB.get()))
                return languageRepository.save(languageFromDB.get());
        }
        return null;

    }

    public void delete(int languageId) {

        Optional<Language> languageFromDB = Optional.ofNullable(languageRepository.findById(languageId));

        if (languageFromDB.isEmpty()) {
            throw new RuntimeException("Programming language not found on id " + languageFromDB);
        }
        Language language = languageFromDB.get();
        languageRepository.delete(language);
    }

}

