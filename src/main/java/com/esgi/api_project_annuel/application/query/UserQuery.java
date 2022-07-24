package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.PostRepository;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import com.esgi.api_project_annuel.application.util.Levenshtein;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserQuery {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    Levenshtein levenshtein = new Levenshtein();

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

    public List<User> getAllByFirstname(String firstname){
        return userRepository.findAllByFirstname(firstname);
    }
    public List<User> getAllByLastname(String lastname){
        return userRepository.findAllByLastname(lastname);
    }

    public List<User> getByPseudoLevenshtein(String pseudo){
        var users = userRepository.findAll();
        var usersFound = new ArrayList<User>();
        users.forEach(user -> {
            if(levenshtein.calculate(pseudo.toUpperCase(), user.getPseudo().toUpperCase()) < 3){
                usersFound.add(user);
            }
        });
        return usersFound;
    }
    public List<User> getByEmailLevenshtein(String email){
        var users = userRepository.findAll();
        var usersFound = new ArrayList<User>();
        users.forEach(user -> {
            if(levenshtein.calculate(email.toUpperCase(), user.getEmail().toUpperCase()) < 3){
                usersFound.add(user);
            }
        });
        return usersFound;
    }

    public List<User> getAllByFirstnameLevenshtein(String firstname){
        var users = userRepository.findAll();
        var usersFound = new ArrayList<User>();
        users.forEach(user -> {
            if(levenshtein.calculate(firstname.toUpperCase(), user.getFirstname().toUpperCase()) < 3){
                usersFound.add(user);
            }
        });
        return usersFound;
    }
    public List<User> getAllByLastnameLevenshtein(String lastname){
        var users = userRepository.findAll();
        var usersFound = new ArrayList<User>();
        users.forEach(user -> {
            if(levenshtein.calculate(lastname.toUpperCase(), user.getLastname().toUpperCase()) < 3){
                usersFound.add(user);
            }
        });
        return usersFound;
    }

    public HashSet<User> SearchLevenshtein(String value){
        var users = new ArrayList<User>();

        var byPseudo = this.getByPseudoLevenshtein(value);
        var byEmail = this.getByEmailLevenshtein(value);
        var byFirstname = this.getAllByFirstnameLevenshtein(value);
        var byLastname = this.getAllByLastnameLevenshtein(value);

        if (byEmail.size() > 0)
            users.addAll(byEmail);
        if (byPseudo.size() > 0)
            users.addAll(byPseudo);
        if (byFirstname.size() > 0)
            users.addAll(byFirstname);
        if (byLastname.size() > 0)
            users.addAll(byLastname);

        return new HashSet<>(users);
    }

    /*public User getByEmailAndPassword(String email, String password){
        return userRepository.findUserByEmailAndPassword(email, password);
    }

    public boolean existsById(int id){
        return userRepository.existsById(id);
    }*/
}
