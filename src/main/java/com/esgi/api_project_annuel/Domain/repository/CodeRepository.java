package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.Domain.entities.Language;
import com.esgi.api_project_annuel.Domain.entities.Project;
import com.esgi.api_project_annuel.Domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<Code,Integer> {

    Code findById(int codeId);

    List<Code> findAllByProject(Project project);

    List<Code> getAllByLanguage(Language language);

    List<Code> getAllByUser(User user);

    List<Code> findAllByUser(User user);
}
