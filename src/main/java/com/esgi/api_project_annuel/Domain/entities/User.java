package com.esgi.api_project_annuel.Domain.entities;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "user_share")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private int id;

    @Column(name = "email")
    private String Email;

    @Column(name = "firstname")
    private String FirstName;

    @Column(name = "lastname")
    private String LastName;

    @Column(name = "password")
    private String Password;

    @Column(name = "profile_picture")
    private String profilePicture;

}
