package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.Like;
import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.LikeRepository;
import com.esgi.api_project_annuel.application.validation.LikeValidationService;
import com.esgi.api_project_annuel.web.request.LikeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeCommand {
    @Autowired
    LikeRepository likeRepository;

    LikeValidationService likeValidationService = new LikeValidationService();

    //user like a post
    //todo verifier que lutilisateur n a pas deja liker le post
   public Like create(LikeRequest likeRequest, User user, Post post){
        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        if(!likeValidationService.isValid(like))
            return null;
        return likeRepository.save(like);
    }

    //user unlike post
    public void delete(int likeId){
       Optional<Like> like = Optional.ofNullable(likeRepository.findById(likeId));
       like.ifPresent(like1 -> {
           like.get().setPost(null);
           like.get().setUser(null);
           likeRepository.save(like.get());
           likeRepository.delete(like.get());
       });


    }

    public void deleteAllLikesPost(Post post){
        Optional<List<Like>> likes = Optional.ofNullable(likeRepository.findAllByPost(post));
        likes.ifPresent(likeList ->
                likeList.forEach(like ->
                        delete(like.getId())
                )
        );

    }

    public void deleteAllLikesUser(User user){
        Optional<List<Like>> likes = Optional.ofNullable(likeRepository.findAllByUser(user));
        likes.ifPresent(likeList ->
                likeList.forEach(like ->
                        delete(like.getId())
                )
        );

    }
}
