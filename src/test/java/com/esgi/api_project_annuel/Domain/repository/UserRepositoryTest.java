package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.Role;
import com.esgi.api_project_annuel.Domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    UserRepository userRepository;

    private final User user1 = new User();
    private final User user2 = new User();
    private final User user3 = new User();

    @BeforeEach
    void setUp() {
        user1.setEmail("test1@myges.fr");
        user1.setFirstname("test1");
        user1.setLastname("test1");
        user1.setPassword("testPassword1");
        user1.setPseudo("testPseudo1");
        user1.setProfilePicture("test1");
        user1.setRoles(new Role());

        user2.setEmail("test2@myges.fr");
        user2.setFirstname("test2");
        user2.setLastname("test2");
        user2.setPassword("test2Password");
        user2.setPseudo("testPseudo2");
        user2.setProfilePicture("test2");
        user2.setRoles(new Role());

        user3.setEmail("test3@myges.fr");
        user3.setFirstname("test3");
        user3.setLastname("test3");
        user3.setPassword("testPassword3");
        user3.setPseudo("testPseudo3");
        user3.setProfilePicture("test3");
        user3.setRoles(new Role());
    }


    @Test
    public void should_find_empty_ROLE() {
        var users = userRepository.findAll();
        assertThat(users).isNotEmpty();
    }

    @Test
    public void should_find_all_user() {
        var init = userRepository.findAll();
        entityManager.persist(user1);

        entityManager.persist(user2);

        entityManager.persist(user3);

        var users = userRepository.findAll();
        assertThat(users).hasSize(init.size() + 3).contains(user1, user2, user3);
    }

    @Test
    void findById() {
        entityManager.persist(user1);
        var user2Created = entityManager.persist(user2);
        var dbUser = userRepository.findById(user2Created.getId());
        assertThat(dbUser.getPseudo()).isEqualTo(user2Created.getPseudo());
        assertThat(dbUser.getEmail()).isEqualTo(user2Created.getEmail());
        assertThat(dbUser.getFirstname()).isEqualTo(user2Created.getFirstname());
        assertThat(dbUser.getLastname()).isEqualTo(user2Created.getLastname());
        assertThat(dbUser.getPassword()).isEqualTo(user2Created.getPassword());
        assertThat(dbUser.getProfilePicture()).isEqualTo(user2Created.getProfilePicture());
        assertThat(dbUser.getRoles()).isEqualTo(user2Created.getRoles());


    }

    @Test
    public void should_delete_User_by_id() {
        var init = userRepository.findAll();
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(user3);
        userRepository.deleteById(user2.getId());
        var users = userRepository.findAll();
        assertThat(users).hasSize(init.size() + 2).contains(user1, user3);
    }

    @Test
    void findByEmail() {
        entityManager.persist(user1);
        assertThat(userRepository.findByEmail(user1.getEmail())).isEqualTo(user1);
        assertThat(userRepository.findByEmail(user2.getEmail())).isEqualTo(null);
    }

    @Test
    void findByPseudo() {
        entityManager.persist(user1);
        assertThat(userRepository.findByPseudo(user1.getPseudo())).isEqualTo(user1);
        assertThat(userRepository.findByPseudo(user2.getPseudo())).isEqualTo(null);

    }

    @Test
    void findUserByEmailAndPassword() {
        entityManager.persist(user1);
        assertThat(userRepository.findByPseudo(user1.getPseudo())).isEqualTo(user1);
        assertThat(userRepository.findByPseudo(user2.getPseudo())).isEqualTo(null);
    }
}