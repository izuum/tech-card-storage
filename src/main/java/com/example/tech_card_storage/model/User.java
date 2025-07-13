package com.example.tech_card_storage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String username;
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;
}

enum Role {
    ADMIN, USER
}