package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.Group;
import com.esgi.api_project_annuel.Domain.entities.Role;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.entities.UserRoleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleGroupRepository extends JpaRepository<UserRoleGroup, Integer> {
    UserRoleGroup findById(int id);

    UserRoleGroup findByGroupAndUser(Group group, User user);

    List<UserRoleGroup> findAllByUser(User user);

    List<UserRoleGroup> findAllByGroup(Group group);

    List<UserRoleGroup> findAllByRole(Role role);
}
