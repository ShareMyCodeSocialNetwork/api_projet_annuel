package com.esgi.api_project_annuel.repositories;

import com.esgi.api_project_annuel.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  GroupRepository extends JpaRepository<Group,Long> {
}
