package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.Role;
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
class RoleRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    RoleRepository repository;

    private final Role object1 = new Role();
    private final Role object2 = new Role();


    @BeforeEach
    void setUp() {
        object1.setTitlePermission("name");
        object2.setTitlePermission("name2");
    }

    @Test
    public void should_find_2() {
        var result = repository.findAll();
        assertThat(result).size().isEqualTo(2);
    }

    @Test
    public void should_find_2_role() {
        var result = repository.findAll();
        assertThat(result).size().isEqualTo(2);
    }

    @Test
    public void should_find_all() {

        entityManager.persist(object1);

        entityManager.persist(object2);

        var results = repository.findAll();
        assertThat(results).hasSize(4).contains(object1, object2);
    }

    @Test
    void findById() {
        entityManager.persist(object1);
        var createdObject2 = entityManager.persist(object2);

        var dbContent = repository.findById(createdObject2.getId());
        assertThat(dbContent.getId()).isEqualTo(createdObject2.getId());
        assertThat(dbContent.getTitlePermission()).isEqualTo(createdObject2.getTitlePermission());
    }
}