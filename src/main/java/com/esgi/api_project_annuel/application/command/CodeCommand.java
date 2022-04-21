package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.Domain.repository.CodeRepository;
import com.esgi.api_project_annuel.application.validation.CodeValidationService;
import com.esgi.api_project_annuel.web.request.CodeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.Optional;

@Service
public class CodeCommand {

    @Autowired
    CodeRepository codeRepository;

    CodeValidationService codeValidationService = new CodeValidationService();

    public Code create(CodeRequest codeRequest){

        Code code = new Code();

        code.setContent(codeRequest.content);
        code.setId(codeRequest.programming_langage_id);

        if(!codeValidationService.codeIsValid(code)) throw new RuntimeException("Invalid code snippet properties");

        return codeRepository.save(code);
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

        Optional<Code> codeFromDB = Optional.ofNullable(codeRepository.findById(codeId));

        if (codeFromDB.isEmpty()) {
            throw new RuntimeException("Code snippet not found with id " + codeFromDB);
        }
        Code code = codeFromDB.get();
        codeRepository.delete(code);
    }

}
