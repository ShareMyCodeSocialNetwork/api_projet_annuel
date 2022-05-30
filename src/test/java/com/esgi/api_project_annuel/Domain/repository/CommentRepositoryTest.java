package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.*;
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
class CommentRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    CommentRepository repository;

    private final Comment object1 = new Comment();
    private final Comment object2 = new Comment();


    @BeforeEach
    void setUp() {
        object1.setUser(new User());
        object1.setContent("content");
        object1.setPost(new Post());

        object2.setUser(new User());
        object2.setContent("content");
        object2.setPost(new Post());

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
        assertThat(dbContent.getContent()).isEqualTo(createdObject2.getContent());
        assertThat(dbContent.getId()).isEqualTo(createdObject2.getId());
        assertThat(dbContent.getPost()).isEqualTo(createdObject2.getPost());
        assertThat(dbContent.getUser()).isEqualTo(createdObject2.getUser());
    }
}