package com.kumar.blog.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

// database corresponding fields
@Entity
@Table(name="users")
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="user_role",
            joinColumns =@JoinColumn(name = "user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<Role>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // take one by one role
       List<SimpleGrantedAuthority> authorities =  this.roles.stream().map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getUsername() {
        // making username field into email field
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
