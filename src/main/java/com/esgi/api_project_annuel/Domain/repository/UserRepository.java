package com.esgi.api_project_annuel.Domain.repository;

<<<<<<< HEAD:src/main/java/com/esgi/api_project_annuel/Domain/repository/UserRepository.java
import com.esgi.api_project_annuel.Domain.entities.User;
=======
import com.esgi.api_project_annuel.model.User;
>>>>>>> master:src/main/java/com/esgi/api_project_annuel/repositories/UserRepository.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findById(int userId);
}
