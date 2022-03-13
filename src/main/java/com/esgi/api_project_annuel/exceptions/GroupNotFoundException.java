package com.esgi.api_project_annuel.exceptions;

public class GroupNotFoundException  extends RuntimeException {
    public GroupNotFoundException(Long id) {
        super("user not found " + id);
    }
}
