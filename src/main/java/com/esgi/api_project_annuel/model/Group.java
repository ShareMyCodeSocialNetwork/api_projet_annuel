package com.esgi.api_project_annuel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "group")
public class Group {
     @Id
     @GeneratedValue
     private Long id;

    @NonNull
    @Column(nullable = false, unique = true)
    private String name;


    public Group() {
    this.name="";
    }

    @JsonIgnore
    public boolean isValid() {
        return (!name.isBlank());
    }
}

//TODO ADD UserId for CREATOR OF GROUP
//TODO ADD LIST OF MEMBERS IN THE GROUP
//TODO GROUP CONTAINS AT LEAST ONE MEMBER : CREATOR