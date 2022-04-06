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

    public User create(UserRequest userRequest){
        User user = new User();
/*
        user.setEmail(userRequest.email);
        user.setFirstName(userRequest.firstName);
        user.setLastName(userRequest.lastName);
        user.setPassword(userRequest.password);

        System.out.println(userRequest.group_id);
        if(userRequest.group_id != 0){
            Group group = groupRepository.findById(userRequest.group_id);
            user.setGroupOfUser(group);
        }

        if (!userValidationService.isUserValid(user)){
            throw new RuntimeException("Invalid user properties");
        }*/
        //todo : user create
        return userRepository.save(user);
    }


    public User update(int userId, User updateUser) throws InvalidObjectException {
        Optional<User> userFromDB = Optional.ofNullable(userRepository.findById(userId));
        if(!userValidationService.isUserValid(updateUser)){
            throw new InvalidObjectException("Invalid user properties");
        }
        if (userFromDB.isEmpty()) {
            throw new InvalidObjectException("Invalid userId properties");
        }
        updateUser.setId(userFromDB.get().getId());
        return userRepository.save(updateUser);
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
