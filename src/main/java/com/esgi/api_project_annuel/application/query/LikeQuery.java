package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.entities.Like;
import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeQuery {
    @Autowired
    LikeRepository likeRepository;

    public List<Like> getAll(){
        return likeRepository.findAll();
    }

    public Like getById(int id){
        return likeRepository.findById(id);
    }

    public List<Like> getByUser(User user){
        return likeRepository.findAllByUser(user);
    }

    public List<Like> getByPost(Post post){
        return likeRepository.findAllByPost(post);
    }
}
