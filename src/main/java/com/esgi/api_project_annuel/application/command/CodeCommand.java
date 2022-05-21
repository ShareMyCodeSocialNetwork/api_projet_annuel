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

    public Code update(int codeId, Code updatedCode) throws InvalidObjectException{

        Optional<Code> codeFromDB = Optional.ofNullable(codeRepository.findById(codeId));

        if(!codeValidationService.codeIsValid(updatedCode)){
            throw new InvalidObjectException("Invalid codeId properties");
        }
        if (codeFromDB.isEmpty()) {
            throw new InvalidObjectException("Invalid codeId properties");
        }
        updatedCode.setId(codeFromDB.get().getId());
        return codeRepository.save(updatedCode);

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
                codes.forEach(code -> {
                    code.setUser(null);
                    code.setLanguage(null);
                    code.setProject(null);
                    codeRepository.save(code);
                    codeRepository.delete(code);
                })
        );
    }
}
