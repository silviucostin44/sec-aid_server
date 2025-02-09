package com.example.secaidserver.model.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

/**
 * User role class.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Id
    @Column(name = "ROLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(cascade = ALL, mappedBy = "role")
    private Set<UserEntity> user;

    public Role(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return getName().equals(role.getName());
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
