package com.esgi.api_project_annuel.Domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "post_share")
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "like")
    private long like;

    @Column(name = "dislike")
    private long dislike;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
