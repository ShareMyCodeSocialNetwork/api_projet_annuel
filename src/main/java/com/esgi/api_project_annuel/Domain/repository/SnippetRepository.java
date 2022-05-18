package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.Language;
import com.esgi.api_project_annuel.Domain.entities.Snippet;
import com.esgi.api_project_annuel.Domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SnippetRepository extends JpaRepository<Snippet, Integer> {

    Snippet findById(int snippetId);
    List<Snippet> getAllByLanguage(Language language);

    List<Snippet> getAllByUser(User user);
}
