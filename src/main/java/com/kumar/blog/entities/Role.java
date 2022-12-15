package com.kumar.blog.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

// User -> Role ->> relationship many to many
@Entity
@Data
public class Role {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
    @Id
    private int id;

    private  String name;
}
