package com.esgi.api_project_annuel.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.regex.Pattern;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
     @Id @GeneratedValue
     private Long id;

    @NonNull
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(nullable = false, unique = true)
    private String firstName;

    @NonNull
    @Column(nullable = false, unique = true)
    private String lastName;

    @NonNull
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    public boolean isValid() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return  !firstName.isBlank() && !lastName.isBlank() && pat.matcher(email).matches() && password.length() >= 8 && password.length() <= 30;

    }

}

//TODO ADD LIST OF FRIENDS AND FOLLOWORS AND GROUPS
//TODO ADD ROUTES TO LOCALHOST POSTGRES DATABASE IN SYSTEM PROPERTIES