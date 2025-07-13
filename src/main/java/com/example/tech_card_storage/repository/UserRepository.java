package com.example.tech_card_storage.repository;

import com.example.tech_card_storage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
