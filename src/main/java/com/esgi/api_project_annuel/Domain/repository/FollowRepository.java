package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    Follow findById(long id);

}
