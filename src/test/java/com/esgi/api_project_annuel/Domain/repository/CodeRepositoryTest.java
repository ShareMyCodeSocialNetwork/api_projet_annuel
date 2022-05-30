package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.Code;
import com.esgi.api_project_annuel.Domain.entities.Language;
import com.esgi.api_project_annuel.Domain.entities.Project;
import com.esgi.api_project_annuel.Domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class CodeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    CodeRepository codeRepository;
    @Autowired
    LanguageRepository languageRepository;
    private final Code object1 = new Code();
    private final Code object2 = new Code();
    private final Language js = new Language();
    private final Language java = new Language();

    @BeforeEach
    void setUp() {
        js.setName("js");

        object1.setProject(new Project());
        object1.setNameCode("name");
        object1.setLanguage(js);
        object1.setContent("content");
        object1.setUser(new User());

        java.setName("java");
        object1.setProject(new Project());
        object1.setNameCode("name2");
        object1.setLanguage(java);
        object1.setContent("content2");
        object1.setUser(new User());
    }

    @Test
    public void should_find_empty() {
        var result = codeRepository.findAll();
        assertThat(result).isEmpty();
    }

    @Test
    public void should_find_no_user_if_repository_is_empty() {
        var result = codeRepository.findAll();
        assertThat(result).size().isEqualTo(0);
    }

    @Test
    public void should_find_all() {

        entityManager.persist(object1);

        entityManager.persist(object2);

        var results = codeRepository.findAll();
        assertThat(results).hasSize(2).contains(object1, object2);
    }

    @Test
    void findById() {
        entityManager.persist(object1);
        var createdObject2 = entityManager.persist(object2);

        var dbContent = codeRepository.findById(createdObject2.getId());
        assertThat(dbContent.getContent()).isEqualTo(createdObject2.getContent());
        assertThat(dbContent.getId()).isEqualTo(createdObject2.getId());
        assertThat(dbContent.getNameCode()).isEqualTo(createdObject2.getNameCode());
        assertThat(dbContent.getLanguage()).isEqualTo(createdObject2.getLanguage());
        assertThat(dbContent.getProject()).isEqualTo(createdObject2.getProject());
        assertThat(dbContent.getUser()).isEqualTo(createdObject2.getUser());
    }
}