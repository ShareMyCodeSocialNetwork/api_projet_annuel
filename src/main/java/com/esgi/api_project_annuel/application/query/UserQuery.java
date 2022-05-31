package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.PostRepository;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Boolean userEmailExist(String mail){
        return userRepository.findByEmail(mail) != null;
    }

    public Boolean userPseudoExist(String pseudo){
        return userRepository.findByPseudo(pseudo) != null;
    }

    public User getByPseudo(String pseudo){
        return userRepository.findByPseudo(pseudo);
    }
    public User getByEmail(String email){
        return userRepository.findByEmail(email);
    }

    /*public User getByEmailAndPassword(String email, String password){
        return userRepository.findUserByEmailAndPassword(email, password);
    }*/

    public boolean existsById(int id){
        return userRepository.existsById(id);
    }
}
