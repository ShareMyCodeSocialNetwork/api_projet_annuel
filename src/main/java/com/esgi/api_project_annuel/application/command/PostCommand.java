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


    LikeCommand likeCommand;
    PostValidationService postValidationService = new PostValidationService();
    UserValidationService userValidationService = new UserValidationService();

    public Post create(PostRequest postRequest, User user){
        Post post = new Post();
        post.setContent(postRequest.content);
        post.setUser(user);

        if(!userValidationService.isUserValid(user))
            return null;

        if(!postValidationService.isValid(post))
            return null;
            //throw new RuntimeException("Invalid post properties");

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
        Optional<Post> dbPost = Optional.ofNullable(postRepository.findById(postId));
        if(dbPost.isPresent()){
            Post post = new Post();
            post.setId(dbPost.get().getId());
            //throw new RuntimeException("invalid post properties");
            return postRepository.save(post);
        }
        return null;
    }

    public void delete(int postId){
        Optional<Post> dbPost = Optional.ofNullable(postRepository.findById(postId));
        dbPost.ifPresent(post ->{
            //on supprime le lien du post avec l' utilisateur avant de supprimer le post sinon on ne peut pas supprimer le post
            post.setUser(null);
            postRepository.save(post);
            postRepository.delete(post);
        }
        );
    }

    public void deleteAllUserPosts(User user){
        Optional<List<Post>> dbPosts = Optional.ofNullable(postRepository.findByUser(user));
        dbPosts.ifPresent(posts ->
            postRepository.deleteAll(posts)
        );
    }

    public Post changeContent(PostRequest postRequest, int postId){
        Optional<Post> dbPost = Optional.ofNullable(postRepository.findById(postId));
        if(dbPost.isPresent()){
            dbPost.get().setContent(postRequest.content);
            return postRepository.save(dbPost.get());
        }
        return null;
    }

}
