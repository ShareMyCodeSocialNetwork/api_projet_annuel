package com.esgi.api_project_annuel.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("user not found "+id);
    }
}
