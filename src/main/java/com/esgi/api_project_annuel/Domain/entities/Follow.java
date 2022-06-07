package com.esgi.api_project_annuel.Domain.entities;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "follow_share")
public class Follow {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "follower_user_id")
    private User followerUser;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "followed_user_id")
    private User followedUser;

}
