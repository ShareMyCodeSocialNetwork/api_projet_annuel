package com.esgi.api_project_annuel.Domain.entities;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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

//
//    @ManyToMany
//    @JoinTable(name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "roles_id"))
//    private List<Role> roles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    @Getter @Setter @JsonIgnore
    private Set<User> friends = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    @Getter @Setter @JsonIgnore
    private Set<User> followers = new HashSet<>();

}
