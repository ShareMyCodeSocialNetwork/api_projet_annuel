package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.entities.Language;
import com.esgi.api_project_annuel.Domain.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageQuery {

    @Autowired
    LanguageRepository languageRepository;

    public List<Language> getAll() {
        return languageRepository.findAll();
    }

    public Language getById(int languageId) {
        return languageRepository.findById(languageId);
    }

    /*public boolean existsById(int id){
        return languageRepository.existsById(id);
    }*/

}
