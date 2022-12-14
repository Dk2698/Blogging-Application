package com.kumar.blog.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// database corresponding fields
@Entity
@Table(name="users")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "user_name", nullable= false, length = 100)
    private String name;
    private String email;
    private String password;
    private String about;
    // one user - multiple post created
    // mappedBy is written by post entity inside user reference
    @OneToMany(mappedBy ="user", cascade= CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> posts =  new ArrayList<>();


}
