package com.esgi.api_project_annuel.service;

import com.esgi.api_project_annuel.integration.User;

import java.io.InvalidObjectException;
import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUserById(long id);

    User createUser(User user) throws InvalidObjectException;

    void updateUser(long id, User user) throws InvalidObjectException;

    void deleteUser(long id);
}

