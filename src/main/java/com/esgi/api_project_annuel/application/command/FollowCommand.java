package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.Follow;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.FollowRepository;
import com.esgi.api_project_annuel.application.validation.FollowValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowCommand {
    @Autowired
    FollowRepository followRepository;

    FollowValidationService followValidationService = new FollowValidationService();

    public Follow create(User followed, User follower){
        Follow follow = new Follow();
        follow.setFollowedUser(followed);
        follow.setFollowerUser(follower);
        if (followValidationService.isValid(follow))
            return followRepository.save(follow);
        return null;
    }

}
