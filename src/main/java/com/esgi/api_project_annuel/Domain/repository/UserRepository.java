package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findById(int userId);
}
