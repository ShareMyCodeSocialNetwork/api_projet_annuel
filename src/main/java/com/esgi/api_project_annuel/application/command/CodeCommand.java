package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.Domain.entities.Language;
import com.esgi.api_project_annuel.Domain.entities.Project;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.CodeRepository;
import com.esgi.api_project_annuel.Domain.repository.LanguageRepository;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import com.esgi.api_project_annuel.application.query.LanguageQuery;
import com.esgi.api_project_annuel.application.query.ProjectQuery;
import com.esgi.api_project_annuel.application.query.UserQuery;
import com.esgi.api_project_annuel.application.validation.CodeValidationService;
import com.esgi.api_project_annuel.web.request.CodeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.List;
import java.util.Optional;

@Service
public class CodeCommand {

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    UserQuery userQuery;

    @Autowired
    LanguageQuery languageQuery;

    @Autowired
    ProjectQuery projectQuery;

    CodeValidationService codeValidationService = new CodeValidationService();

    public Code create(CodeRequest codeRequest, Language language, User user, Project project){

        Code code = new Code();

        code.setNameCode(codeRequest.name);
        code.setContent(codeRequest.content);
        code.setUser(user);
        code.setLanguage(language);
        code.setProject(project);

        if(codeValidationService.codeIsValid(code))
            return codeRepository.save(code);
        return null;
    }

    public Code update(int codeId, CodeRequest codeRequest, Language language, Project project) {

        Optional<Code> codeFromDB = Optional.ofNullable(codeRepository.findById(codeId));

        if(codeFromDB.isPresent()){
            codeFromDB.get().setNameCode(codeRequest.name.equals("") ? codeFromDB.get().getNameCode() : codeRequest.name);
            codeFromDB.get().setContent(codeRequest.content.equals("") ? codeFromDB.get().getContent() : codeRequest.content);
            codeFromDB.get().setLanguage(language == null ? codeFromDB.get().getLanguage() : language);
            codeFromDB.get().setProject(project == null ? codeFromDB.get().getProject() : project);

            if(codeValidationService.codeIsValid(codeFromDB.get()))
                return codeRepository.save(codeFromDB.get());
        }
        return null;
    }

    public void delete(int codeId) {
        Optional.ofNullable(codeRepository.findById(codeId)
        ).ifPresent(code -> {
            code.setProject(null);
            code.setUser(null);
            code.setLanguage(null);
            codeRepository.save(code);
            codeRepository.delete(code);
        });
    }

    public void deleteLanguage(Language language){
        var codes = codeRepository.getAllByLanguage(language);
        codes.forEach(code -> {
            code.setLanguage(null);
            codeRepository.save(code);
        });
    }

    public void deleteAllByUser(User user){
        Optional<List<Code>> dbCodes = Optional.ofNullable(codeRepository.getAllByUser(user));
        dbCodes.ifPresent(codes ->
                codes.forEach(code ->
                        delete(code.getId())
                )
        );
    }
}
