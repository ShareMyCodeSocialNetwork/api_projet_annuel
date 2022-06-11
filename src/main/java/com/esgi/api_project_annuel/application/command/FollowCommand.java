package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.Follow;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.FollowRepository;
import com.esgi.api_project_annuel.application.query.FollowQuery;
import com.esgi.api_project_annuel.application.validation.FollowValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowCommand {
    private final FollowRepository followRepository;

    private final FollowQuery followQuery;

    FollowValidationService followValidationService = new FollowValidationService();

    @Autowired
    public FollowCommand(FollowRepository followRepository, FollowQuery followQuery) {
        this.followRepository = followRepository;
        this.followQuery = followQuery;
    }


    public Follow create(User followed, User follower){
        Follow follow = new Follow();
        follow.setFollowedUser(followed);
        follow.setFollowerUser(follower);
        var checkFollow = followRepository.getFollowByFollowedUserAndFollowerUser(followed,follower);
        if(checkFollow == null)
            if (followValidationService.isValid(follow))
                return followRepository.save(follow);
        return null;
    }



    public void deleteById(int id){
        Optional<Follow> follow = Optional.ofNullable(followRepository.findById(id));
        follow.ifPresent(follow1 -> {
            follow1.setFollowedUser(null);
            follow1.setFollowerUser(null);
            followRepository.save(follow1);
            followRepository.delete(follow1);
        });
    }


    public void deleteAllByFollower(User follower){
        var followed = followQuery.getAllByFollowerUser(follower);
        followed.forEach(follow ->
                deleteById(follow.getId())
        );
    }

    public void deleteAllByFollowed(User followed){
        var follower = followQuery.getAllByFollowedUser(followed);
        follower.forEach(follow ->
                deleteById(follow.getId())
        );
    }
}
