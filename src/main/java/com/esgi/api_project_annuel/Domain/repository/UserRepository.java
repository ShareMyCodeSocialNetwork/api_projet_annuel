package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findById(int userId);

    User findByEmail(String email);

    User findByPseudo(String pseudo);

    List<User> findAllByFirstname(String firstname);

    List<User> findAllByLastname(String lastname);

    //User findUserByEmailAndPassword(String email, String password);
}
