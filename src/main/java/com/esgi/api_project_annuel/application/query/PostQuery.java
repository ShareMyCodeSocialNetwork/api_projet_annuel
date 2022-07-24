package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.entities.Like;
import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.LikeRepository;
import com.esgi.api_project_annuel.Domain.repository.PostRepository;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import com.esgi.api_project_annuel.application.util.Levenshtein;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class PostQuery {
    @Autowired
    PostRepository postRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserQuery userQuery;

    @Autowired
    private final Levenshtein levenshtein = new Levenshtein();


    public PostQuery(){}

    public List<Post> getAll(){
        return postRepository.findAll();
    }

    public Post getById(int postId){
        return postRepository.findById(postId);
    }

    public List<Post> getByUser(int userId){
        return postRepository.findByUser(userRepository.getById(userId));
    }

    public List<Post> getPostByContentLevenshtein(String content){
        var posts = postRepository.findAll();
        var postsFound = new ArrayList<Post>();
        posts.forEach(post -> {
            if(levenshtein.calculate(content.toUpperCase(), post.getContent().toUpperCase()) < 3){
                postsFound.add(post);
            }
        });
        return postsFound;
    }
    public List<Post> getPostByCodeNameLevenshtein(String codeName){
        var posts = postRepository.findAllByCodeExists();
        var postsFound = new ArrayList<Post>();
        posts.forEach(post -> {
            if(levenshtein.calculate(codeName.toUpperCase(), post.getCode().getNameCode().toUpperCase()) < 3){
                postsFound.add(post);
            }
        });
        return postsFound;
    }
    public List<Post> getPostByCodeLanguageLevenshtein(String codeLanguage){
        var posts = postRepository.findAllByCodeExists();
        var postsFound = new ArrayList<Post>();
        posts.forEach(post -> {
            if(levenshtein.calculate(codeLanguage.toUpperCase(), post.getCode().getLanguage().getName().toUpperCase()) < 3){
                postsFound.add(post);
            }
        });
        return postsFound;
    }

    public List<Post> getByUserLevenshtein(String value){
        var users = userQuery.SearchLevenshtein(value);
        var posts = new ArrayList<Post>();

        users.forEach(user -> {
            var userPosts = postRepository.findByUser(user);
            if (userPosts.size() > 0)
                posts.addAll(userPosts);
        });
        return posts;
    }

    public HashSet<Post> findPostLevenshtein(String value){
        var byContent = this.getPostByContentLevenshtein(value);
        var byCodeName = this.getPostByCodeNameLevenshtein(value);
        var byUser = this.getByUserLevenshtein(value);
        var byCodeLanguage = this.getPostByCodeLanguageLevenshtein(value);

        var postsFound = new ArrayList<Post>();
        if (byCodeLanguage.size() > 0)
            postsFound.addAll(byCodeLanguage);
        if (byUser.size() > 0)
            postsFound.addAll(byUser);
        if (byCodeName.size() > 0)
            postsFound.addAll(byCodeName);
        if (byContent.size() > 0)
            postsFound.addAll(byContent);
        return new HashSet<>(postsFound);
    }

    /*
    public List<Like> getLikes(int postId){
        var post = postRepository.findById(postId);
        return likeRepository.findAllByPost(post);
    }

    public List<Post> getPostsByUserFollowed(List<User> followed){
        List<Post> posts = new ArrayList<>();
        followed.forEach(user ->
            posts.addAll(postRepository.findByUser(user))
        );
        return posts;
    }*/

}
