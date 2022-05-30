package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.Like;
import com.esgi.api_project_annuel.Domain.entities.Post;
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
class LikeRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    LikeRepository repository;

    private final Like object1 = new Like();
    private final Like object2 = new Like();


    @BeforeEach
    void setUp() {
        object1.setUser(new User());
        object1.setPost(new Post());
    }

    @Test
    public void should_find_empty() {
        var result = repository.findAll();
        assertThat(result).isEmpty();
    }

    @Test
    public void should_find_nothing_if_repository_is_empty() {
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
        assertThat(dbContent.getUser()).isEqualTo(createdObject2.getUser());
        assertThat(dbContent.getPost()).isEqualTo(createdObject2.getPost());
    }
}