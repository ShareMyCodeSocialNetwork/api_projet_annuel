package com.esgi.api_project_annuel.application.command;
import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.PostRepository;
import com.esgi.api_project_annuel.application.validation.PostValidationService;
import com.esgi.api_project_annuel.application.validation.UserValidationService;
import com.esgi.api_project_annuel.web.request.PostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostCommand {
    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentCommand commentCommand;

    @Autowired
    LikeCommand likeCommand;

    PostValidationService postValidationService = new PostValidationService();
    UserValidationService userValidationService = new UserValidationService();

    public Post create(PostRequest postRequest, User user){
        Post post = new Post();
        post.setContent(postRequest.content);

        if(!userValidationService.isUserValid(user))
            return null;
        post.setUser(user);

        if(!postValidationService.isValid(post))
            return null;

        return postRepository.save(post);
    }

    public Post update(int postId, PostRequest postRequest){
        Optional<Post> dbPost = Optional.ofNullable(postRepository.findById(postId));
        if(dbPost.isPresent()){
            dbPost.get().setContent(postRequest.content);
            if(!postValidationService.isValid(dbPost.get()))
                return null;
            return postRepository.save(dbPost.get());
        }
        return null;

    }

    public void delete(int postId){
        Optional<Post> dbPost = Optional.ofNullable(postRepository.findById(postId));
        dbPost.ifPresent(post ->{
            post.setUser(null);
            postRepository.save(post);
            likeCommand.deleteAllLikesPost(post);
            postRepository.delete(post);
        }
        );
    }

    public void deleteAllUserPosts(User user){
        Optional<List<Post>> dbPosts = Optional.ofNullable(postRepository.findByUser(user));
        dbPosts.ifPresent(posts ->
            posts.forEach(post -> {
                commentCommand.deleteCommentsInPost(post);
                likeCommand.deleteAllLikesPost(post);
                post.setUser(null);
                postRepository.save(post);
                postRepository.delete(post);
            })
        );
    }

    public Post changeContent(PostRequest postRequest, int postId){
        Optional<Post> dbPost = Optional.ofNullable(postRepository.findById(postId));
        if(dbPost.isPresent()){
            dbPost.get().setContent(postRequest.content);
            if (postValidationService.isValid(dbPost.get()))
                return postRepository.save(dbPost.get());
        }
        return null;
    }

}
