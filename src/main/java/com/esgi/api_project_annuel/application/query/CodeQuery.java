package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.Domain.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeQuery {

    @Autowired
    CodeRepository codeRepository;


    public List<Code> getAll() {
        return codeRepository.findAll();
    }


    public Code getById(int codeId) {
        return codeRepository.findById(codeId);
    }

}
