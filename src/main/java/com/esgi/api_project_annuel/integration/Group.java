package com.esgi.api_project_annuel.integration;

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

//TODO ADD LIST OF MEMBERS IN THE GROUP
//TODO ADD ROLES FOR USERS : OWNER AND MEMBERS
