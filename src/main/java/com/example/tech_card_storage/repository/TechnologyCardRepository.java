package com.example.tech_card_storage.repository;

import com.example.tech_card_storage.model.TechnologyCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnologyCardRepository extends JpaRepository<TechnologyCard, Long> {
    List<TechnologyCard> findByUserFullNameContainingIgnoreCase(String name);
    List<TechnologyCard> findByPcInventoryNumber(String number);
}
