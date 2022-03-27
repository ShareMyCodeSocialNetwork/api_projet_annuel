package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.PostRepository;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import com.esgi.api_project_annuel.application.validation.PostValidationService;
import com.esgi.api_project_annuel.application.validation.UserValidationService;
import com.esgi.api_project_annuel.web.request.PostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostCommand {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    PostValidationService postValidationService;
    UserValidationService userValidationService;

    public Post create(PostRequest postRequest){
        Post post = new Post();

        post.setContent(postRequest.content);
        post.setDislike(postRequest.dislike);
        post.setLike(postRequest.like);
        User user = userRepository.getById(postRequest.user_id);

        if(!userValidationService.isUserValid(user))
            throw new RuntimeException("can't create post without user");
        post.setUser(user);
        if(!postValidationService.isValid(post))
            throw new RuntimeException("Invalid post properties");
        return postRepository.save(post);
    }

    public Post update(int postId, PostRequest postRequest){
        Optional<Post> dbPost = Optional.ofNullable(postRepository.findById(postId));
        Post post = new Post();
        post.setLike(postRequest.like);
        post.setDislike(postRequest.dislike);
        post.setContent(postRequest.content);
        //todo : le optional check si ca existe ou pas ?
        if(!postValidationService.isValid(post))
            throw new RuntimeException("invalid post properties");
        post.setId(dbPost.get().getId());
        return postRepository.save(post);
    }

    public void delete(int postId){
        Optional<Post> dbPost = Optional.ofNullable(postRepository.findById(postId));
        if(dbPost.isEmpty())
            throw new RuntimeException("Post not found on id : " + postId);
        postRepository.delete(dbPost.get());
    }
}
