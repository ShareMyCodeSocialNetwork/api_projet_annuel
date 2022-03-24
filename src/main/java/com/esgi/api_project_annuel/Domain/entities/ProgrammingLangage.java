package com.esgi.api_project_annuel.Domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "programming_langage")
public class ProgrammingLangage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @Column(name = "id", nullable = false)
    private int id;


    @Column(name = "langage")
    @Getter @Setter
    private String name;
}