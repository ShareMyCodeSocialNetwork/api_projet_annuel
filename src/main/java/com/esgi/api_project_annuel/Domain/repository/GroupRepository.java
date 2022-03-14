package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  GroupRepository extends JpaRepository<Group,Long> {
    Group findById(int id);
}
