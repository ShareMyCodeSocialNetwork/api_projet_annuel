package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.UserRoleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleGroupRepository extends JpaRepository<UserRoleGroup, Integer> {
    UserRoleGroup findById(long id);
}
