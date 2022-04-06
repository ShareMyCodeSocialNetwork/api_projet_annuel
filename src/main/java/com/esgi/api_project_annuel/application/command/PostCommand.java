package com.esgi.api_project_annuel.application.command;

import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.LikeRepository;
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

    @Autowired
    LikeRepository likeRepository;


    LikeCommand likeCommand;
    PostValidationService postValidationService;
    UserValidationService userValidationService;

    public Post create(PostRequest postRequest){
        Post post = new Post();
        //post.setDislikeId(dislikeRepository.findById(postRequest.dislikeId));
        //post.setLikeId(likeRepository.findById(postRequest.likeId));
        post.setContent(postRequest.content);
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
        if(dbPost.isPresent()){
            Post post = new Post();
            post.setContent(postRequest.content);
            post.setId(dbPost.get().getId());
            if(!postValidationService.isValid(post))
                return null;
            //throw new RuntimeException("invalid post properties");
            return postRepository.save(post);
        }
        return null;

    }

    public Post like(int postId){

        //todo : like passer en obj donc a changer
        Optional<Post> dbPost = Optional.ofNullable(postRepository.findById(postId));
        if(dbPost.isPresent()){
            Post post = new Post();
            //todo a faire
            // post.setLikeId(likeCommand.userLike(post.getLikeId()));
            post.setId(dbPost.get().getId());
            //throw new RuntimeException("invalid post properties");
            return postRepository.save(post);
        }
        return null;
    }

    public Post dislike(int postId){
        //todo : dislike passer en obj donc a changer
        Optional<Post> dbPost = Optional.ofNullable(postRepository.findById(postId));
        if(dbPost.isPresent()){
            Post post = new Post();
            //post.setLike(post.getLike() + 1);
            post.setId(dbPost.get().getId());
            //throw new RuntimeException("invalid post properties");
            return postRepository.save(post);
        }
        return null;
    }

    public void delete(int postId){
        Optional<Post> dbPost = Optional.ofNullable(postRepository.findById(postId));
        if(dbPost.isEmpty())
            throw new RuntimeException("Post not found on id : " + postId);
        postRepository.delete(dbPost.get());
    }
}
