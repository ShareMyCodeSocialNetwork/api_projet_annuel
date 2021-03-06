package com.esgi.api_project_annuel.Domain.entities;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "user_share")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private int id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "password")
    private String password;

    @Column(name = "pseudo")
    private String pseudo;

    @Column(name = "profile_picture")
    private String profilePicture;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="user_roles")
    private Role roles;

}
