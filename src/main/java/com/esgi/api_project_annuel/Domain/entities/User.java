package com.esgi.api_project_annuel.Domain.entities;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;



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


    @Column(name = "lastName")
    private String LastName;


    @Column(name = "password")
    private String Password;


    @JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group GroupOfUser;


}
