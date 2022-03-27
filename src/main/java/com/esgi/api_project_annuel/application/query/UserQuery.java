package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.entities.Post;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.PostRepository;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserQuery {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    public UserQuery(){}


    public List<User> getAll() {
        return userRepository.findAll();
    }


    public User getById(int userId) {
        return userRepository.findById(userId);
    }

    public List<Post> getPosts(User user) {
        return postRepository.findByUser(user);
    }
}
