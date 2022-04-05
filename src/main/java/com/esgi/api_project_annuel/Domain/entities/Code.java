package com.esgi.api_project_annuel.Domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "code")
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter@Setter
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "content")
    @Getter @Setter
    private String content;

    @JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "programming_langage_id")
    private ProgrammingLangage programmingLangage;

}