package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    Like findById(int id);


}
