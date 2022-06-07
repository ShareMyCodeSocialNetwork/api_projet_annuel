package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.Language;
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
class LanguageRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    LanguageRepository repository;

    private final Language object1 = new Language();
    private final Language object2 = new Language();


    @BeforeEach
    void setUp() {
        object1.setName("name");
        object2.setName("name2");
    }

    @Test
    public void should_be_NotEmpty() {
        var result = repository.findAll();
        assertThat(result).isNotEmpty();
    }

    @Test
    public void should_find_3() {
        var result = repository.findAll();
        assertThat(result).size().isEqualTo(3);
    }

    @Test
    public void should_find_all() {

        entityManager.persist(object1);

        entityManager.persist(object2);

        var results = repository.findAll();
        assertThat(results).hasSize(5).contains(object1, object2);
    }

    @Test
    void findById() {
        entityManager.persist(object1);
        var createdObject2 = entityManager.persist(object2);

        var dbContent = repository.findById(createdObject2.getId());
        assertThat(dbContent.getId()).isEqualTo(createdObject2.getId());
        assertThat(dbContent.getName()).isEqualTo(createdObject2.getName());
    }
}