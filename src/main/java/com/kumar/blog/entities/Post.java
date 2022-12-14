package com.kumar.blog.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// creating Post entity and relation b/w User entity
@Entity
@Table(name="posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(name = "post_title", length= 100, nullable= false)
    private String title;

    @Column(length=1000)
    private String content;

    private String imageName;

    private Date addedDate;

    //which user will post created
    // single category reverse write
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    // name using joinColumn
    @ManyToOne
    private User user;

    // in this table adding two field
    // category_category_id
    // user_id

    // add post to field require category and user
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<Comment>();
}
