package com.esgi.api_project_annuel.Domain.repository;

import com.esgi.api_project_annuel.Domain.entities.Follow;
import com.esgi.api_project_annuel.Domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    Follow findById(int id);

    List<Follow> getAllByFollowedUser(User followed);

    List<Follow> getAllByFollowerUser(User follower);

    Follow getFollowByFollowedUserAndFollowerUser(User followed, User follower);

}
