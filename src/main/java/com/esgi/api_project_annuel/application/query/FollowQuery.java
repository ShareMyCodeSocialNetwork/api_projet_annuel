package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowQuery {
    @Autowired
    FollowRepository followRepository;

    public FollowQuery(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }
}
