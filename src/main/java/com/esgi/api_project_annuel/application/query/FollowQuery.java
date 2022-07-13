package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.entities.Follow;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowQuery {
    @Autowired
    FollowRepository followRepository;

    public FollowQuery(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public Follow getById(int id){
        return followRepository.findById(id);
    }

    public List<Follow> getAll(){
        return followRepository.findAll();
    }

    public List<Follow> getAllByFollowedUser(User followed){
        return followRepository.getAllByFollowedUser(followed);
    }

    public List<Follow> getAllByFollowerUser(User follower){
        return followRepository.getAllByFollowerUser(follower);
    }

    public Follow getFollowByFollowedAndFollower(User followed, User follower){
        return followRepository.getFollowByFollowedUserAndFollowerUser(followed, follower);
    }
}
