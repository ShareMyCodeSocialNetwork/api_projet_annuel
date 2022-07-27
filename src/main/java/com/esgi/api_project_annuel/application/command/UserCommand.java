package com.esgi.api_project_annuel.application.command;
import com.esgi.api_project_annuel.Domain.entities.Role;
import com.esgi.api_project_annuel.Domain.entities.User;
import com.esgi.api_project_annuel.Domain.repository.RoleRepository;
import com.esgi.api_project_annuel.Domain.repository.UserRepository;
import com.esgi.api_project_annuel.application.query.UserQuery;
import com.esgi.api_project_annuel.application.validation.UserValidationService;
import com.esgi.api_project_annuel.web.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserCommand implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserQuery userQuery;
    @Autowired
    PostCommand postCommand;
    @Autowired
    CommentCommand commentCommand;
    @Autowired
    LikeCommand likeCommand;
    @Autowired
    FollowCommand followCommand;
    @Autowired
    SnippetCommand snippetCommand;
    @Autowired
    UserRoleGroupCommand userRoleGroupCommand;
    @Autowired
    ProjectCommand projectCommand;
    @Autowired
    CodeCommand codeCommand;

    UserValidationService userValidationService = new UserValidationService();


    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRoles().getTitlePermission()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }

    public User create(UserRequest userRequest){
        var user = new User();
        user.setEmail(userRequest.email);
        user.setFirstname(userRequest.firstname);
        user.setLastname(userRequest.lastname);
        user.setPassword(userRequest.password);
        user.setPseudo(userRequest.pseudo);
        user.setProfilePicture(
                Objects.requireNonNullElse(userRequest.profilePicture, "default_profile_picture")
        );
        user.setRoles(roleRepository.findRoleByTitlePermission("USER"));
        if (!userValidationService.isUserValid(user))
            return null;
        String encodedPassword = passwordEncoder.encode(userRequest.password);
        System.out.println("ENCODED PASSWORD : " + encodedPassword);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }


    public User changePassword(int userId, UserRequest userRequest){
        Optional<User> dbUser = Optional.ofNullable(userRepository.findById(userId));
        if(dbUser.isPresent()){
            var user = dbUser.get();
            user.setPassword(userRequest.password);
            if(userValidationService.isUserValid(user)){
                user.setPassword(passwordEncoder.encode(userRequest.password));
                return userRepository.save(user);
            }
        }
        return null;
    }

    public User changeFirstname(int userId, UserRequest userRequest) {
        Optional<User> userFromDB = Optional.ofNullable(userRepository.findById(userId));
        if(userFromDB.isPresent()){
            var user = userFromDB.get();
            user.setFirstname(userRequest.firstname);
            if(userValidationService.isUserValid(user))
                return userRepository.save(user);
        }
        return null;
    }

    public User changeLastname(int userId, UserRequest userRequest) {
        Optional<User> userFromDB = Optional.ofNullable(userRepository.findById(userId));
        if(userFromDB.isPresent()){
            var user = userFromDB.get();
            user.setLastname(userRequest.lastname);
            if(userValidationService.isUserValid(user))
                return userRepository.save(user);
        }
        return null;
    }

    public User changeEmail(int userId, UserRequest userRequest){
        Optional<User> userFromDB = Optional.ofNullable(userRepository.findById(userId));
        if(userFromDB.isPresent()){
            userFromDB.get().setEmail(userRequest.email);
            if(userValidationService.isUserValid(userFromDB.get()))
                if(!userQuery.userEmailExist(userFromDB.get().getEmail()))
                    return userRepository.save(userFromDB.get());
        }
        return null;
    }

    public User changePseudo(int userId, UserRequest userRequest){
        Optional<User> userFromDB = Optional.ofNullable(userRepository.findById(userId));
        if(userFromDB.isPresent()){
            var user = userFromDB.get();
            user.setPseudo(userRequest.pseudo);
            if(userValidationService.isUserValid(user))
                if(!userQuery.userPseudoExist(user.getPseudo()))
                    return userRepository.save(user);
        }
        return null;
    }

    public void delete(int userId) {
        Optional<User> userFromDb = Optional.ofNullable(userRepository.findById(userId));
        userFromDb.ifPresent(user ->{
                    commentCommand.deleteAllUserComments(user);
                    codeCommand.deleteAllByUser(user);
                    projectCommand.deleteAllProjectsUser(user);
                    postCommand.deleteAllUserPosts(user);
                    likeCommand.deleteAllLikesUser(user);
                    snippetCommand.deleteAllByUser(user);
                    followCommand.deleteAllByFollowed(user);
                    followCommand.deleteAllByFollower(user);
                    userRoleGroupCommand.deleteAllByUser(user);
                    user.setRoles(null);
                    userRepository.save(user);
                    userRepository.delete(user);
                }

        );
    }

    /*public void assignUserWithRole(int role_id,int user_id){
        User user = userRepository.findById(user_id);
        Role role = roleRepository.findById(role_id);

        user.setRoles(role);
        userRepository.save(user);
    }*/

}
