package com.example.secaidserver.model.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    private String username;

    private String password;

    @ManyToOne
    @JoinColumn(name="ROLE_ID", nullable = false)
    private Role role;

    public UserEntity(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
