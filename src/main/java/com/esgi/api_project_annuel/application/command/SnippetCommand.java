package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.repository.SnippetRepository;
import com.esgi.api_project_annuel.application.validation.SnippetValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnippetCommand {

    @Autowired
    SnippetRepository snippetRepository;

    SnippetValidationService snippetValidationService = new SnippetValidationService();




}
