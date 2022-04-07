package com.esgi.api_project_annuel.application.command;



import com.esgi.api_project_annuel.Domain.entities.Group;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.GroupRepository;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import com.esgi.api_project_annuel.application.validation.EmailValidation;
import com.esgi.api_project_annuel.application.validation.UserValidationService;
import com.esgi.api_project_annuel.web.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.Objects;
import java.util.Optional;

/**
 * Command object
 */
@Service
public final class UserCommand {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    UserValidationService userValidationService = new UserValidationService();

    //todo FAIRE LES VERIFICATIONS DANS LE CONTROLLEUR

    public User create(UserRequest userRequest){
        var user = new User();
        user.setEmail(userRequest.email);
        user.setFirstName(userRequest.firstName);
        user.setLastName(userRequest.lastName);
        user.setPassword(userRequest.password);
        user.setProfilePicture(
                Objects.requireNonNullElse(userRequest.profilePicture, "default_profile_picture")
        );
        if (!userValidationService.isUserValid(user)){
            throw new RuntimeException("Invalid user properties");
        }
        return userRepository.save(user);
    }


    public User changePassword(int userId, String password){
        Optional<User> dbUser = Optional.ofNullable(userRepository.findById(userId));
        if(dbUser.isPresent()){
            var user = dbUser.get();
            user.setPassword(password);
            return userRepository.save(user);
        }else
            return null;
    }

    public User changeFirstname(int userId, UserRequest userRequest) {
        Optional<User> userFromDB = Optional.ofNullable(userRepository.findById(userId));
        if(userFromDB.isEmpty())
            return null;
        else{
            var user = userFromDB.get();
            user.setFirstName(userRequest.firstName);
            if(userValidationService.isUserValid(user))
                userRepository.save(user);
        }
        return null;
    }

    public User changeLastname(int userId, UserRequest userRequest) {
        Optional<User> userFromDB = Optional.ofNullable(userRepository.findById(userId));
        if(userFromDB.isEmpty())
            return null;
        else{
            var user = userFromDB.get();
            user.setFirstName(userRequest.firstName);
            if(userValidationService.isUserValid(user))
                userRepository.save(user);
        }
        return null;
    }

    public void delete(int userId) {
        Optional<User> userFromDb = Optional.ofNullable(userRepository.findById(userId));
        if (userFromDb.isEmpty()) {
            throw new RuntimeException("user not found on id " + userId);
        }
        User user = userFromDb.get();
        userRepository.delete(user);
    }



}
