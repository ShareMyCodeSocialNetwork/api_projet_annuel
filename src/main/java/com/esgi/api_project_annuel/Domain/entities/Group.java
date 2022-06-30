package com.esgi.api_project_annuel.Domain.entities;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "group_share")
public class Group {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User owner;
}


