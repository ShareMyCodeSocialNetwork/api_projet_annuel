package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.Group;
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
class ProjectRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    ProjectRepository repository;

    private final Project object1 = new Project();
    private final Project object2 = new Project();


    @BeforeEach
    void setUp() {
        object1.setName("name");
        object1.setGroup(new Group());
        object1.setOwner(new User());

        object2.setName("name2");
    }

    @Test
    public void should_find_empty() {
        var result = repository.findAll();
        assertThat(result).isEmpty();
    }

    @Test
    public void should_find_no_user_if_repository_is_empty() {
        var result = repository.findAll();
        assertThat(result).size().isEqualTo(0);
    }

    @Test
    public void should_find_all() {

        entityManager.persist(object1);

        entityManager.persist(object2);

        var results = repository.findAll();
        assertThat(results).hasSize(2).contains(object1, object2);
    }

    @Test
    void findById() {
        entityManager.persist(object1);
        var createdObject2 = entityManager.persist(object2);

        var dbContent = repository.findById(createdObject2.getId());
        assertThat(dbContent.getId()).isEqualTo(createdObject2.getId());
        assertThat(dbContent.getName()).isEqualTo(createdObject2.getName());
        assertThat(dbContent.getOwner()).isEqualTo(createdObject2.getOwner());
        assertThat(dbContent.getGroup()).isEqualTo(createdObject2.getGroup());
    }
}