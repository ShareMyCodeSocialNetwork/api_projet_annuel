package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.Follow;
import com.esgi.api_project_annuel.Domain.repository.FollowRepository;
import com.esgi.api_project_annuel.web.request.FollowRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowCommand {
    @Autowired
    FollowRepository followRepository;

    public Follow create(FollowRequest followRequest){
        Follow follow = new Follow();

        return followRepository.save(follow);
    }

}
