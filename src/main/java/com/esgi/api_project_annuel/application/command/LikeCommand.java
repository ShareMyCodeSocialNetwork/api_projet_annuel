package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.Like;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.LikeRepository;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import com.esgi.api_project_annuel.application.validation.UserValidationService;
import com.esgi.api_project_annuel.web.request.LikeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeCommand {

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    UserRepository userRepository;

    UserValidationService userValidationService;

   /* public Like create(LikeRequest likeRequest){
        Like like = new Like();
        like.setLikeValue(0);
        User user = userRepository.getById(likeRequest.user_id);
        if(!userValidationService.isUserValid(user))
            throw new RuntimeException("user does not exist");
        return likeRepository.save(like);
    }

    public Like userLike(int likeId){
        Like like = likeRepository.getById(likeId);
        like.setLikeValue(like.getLikeValue() + 1);
        return likeRepository.save(like);
    }
    public Like userUnlike(int likeId){
        Like like = likeRepository.getById(likeId);
        like.setLikeValue(like.getLikeValue() - 1);
        return likeRepository.save(like);
    }
*/
}
