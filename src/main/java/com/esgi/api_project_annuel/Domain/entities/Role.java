package com.esgi.api_project_annuel.Domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "role")
public class Role {


    @ManyToOne
    @JoinColumn(name = "admin_id")
    public static final Role ADMIN = new Role(1, "ROLE_ADMIN", "admin");

    @ManyToOne
    @JoinColumn(name = "user_id")
    public static final Role USER = new Role(2, "ROLE_USER", "user");


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Getter @Setter
    private int id;

    @Column(unique = true, name = "name")
    @Getter @Setter
    private String name;

    @Column(name = "description")
    @Getter @Setter
    private String description;


    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users;


    public Role(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


}