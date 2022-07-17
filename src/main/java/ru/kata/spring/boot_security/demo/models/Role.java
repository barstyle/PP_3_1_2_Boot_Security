package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    public static String PREFIX_ROLE = "ROLE_";
    public static String ROLE_ADMIN = "ADMIN";
    public static String ROLE_USER = "USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(String name, Collection<User> users) {
        this.name = name;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

//    @Override
//    public String toString() {
//        return "Role {" +
//               "id = " + id +
//               ", name = '" + name + '\'' +
//               ", users = " + users +
//               '}';
//    }


    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getAuthority() {
        return PREFIX_ROLE + getName();
    }
}
