package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.Follow;
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
class FollowRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    FollowRepository repository;

    private final Follow object1 = new Follow();
    private final Follow object2 = new Follow();

    @BeforeEach
    void setUp() {
        object1.setFollowedUser(new User());
        object1.setFollowerUser(new User());
        object2.setFollowedUser(new User());
        object2.setFollowerUser(new User());
    }
    @Test
    public void should_find_empty() {
        var result = repository.findAll();
        assertThat(result).isNotEmpty();
    }



    @Test
    public void should_find_all() {
        var init = repository.findAll();

        entityManager.persist(object1);

        entityManager.persist(object2);

        var results = repository.findAll();
        assertThat(results).hasSize(init.size() + 2).contains(object1, object2);
    }

    @Test
    void findById() {
        entityManager.persist(object1);
        var createdObject2 = entityManager.persist(object2);

        var dbContent = repository.findById(createdObject2.getId());
        assertThat(dbContent.getFollowedUser()).isEqualTo(createdObject2.getFollowedUser());
        assertThat(dbContent.getId()).isEqualTo(createdObject2.getId());
        assertThat(dbContent.getFollowerUser()).isEqualTo(createdObject2.getFollowerUser());
    }

}