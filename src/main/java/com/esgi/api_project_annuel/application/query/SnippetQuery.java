package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.entities.Snippet;
import com.esgi.api_project_annuel.Domain.repository.SnippetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnippetQuery {

    @Autowired
    SnippetRepository snippetRepository;

    public List<Snippet> getAll() {
        return snippetRepository.findAll();
    }

    public Snippet getById(int snippetId) {
        return snippetRepository.findById(snippetId);
    }

}
