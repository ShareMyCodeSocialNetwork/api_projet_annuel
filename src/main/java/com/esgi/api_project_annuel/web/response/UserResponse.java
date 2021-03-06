package com.esgi.api_project_annuel.web.response;

public class UserResponse {
    int id;
    String lastname;
    String firstname;
    String email;
    String pseudo;
    String profilePicture;

    String password;

    public UserResponse() {
    }

    public int getId() {
        return id;
    }

    public UserResponse setId(int id) {
        this.id = id;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public UserResponse setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public UserResponse setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPseudo() {
        return pseudo;
    }

    public UserResponse setPseudo(String pseudo) {
        this.pseudo = pseudo;
        return this;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public UserResponse setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserResponse setPassword(String password) {
        this.password = password;
        return this;
    }
}
