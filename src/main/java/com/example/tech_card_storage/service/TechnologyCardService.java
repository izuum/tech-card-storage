package com.example.tech_card_storage.service;

import com.example.tech_card_storage.model.TechnologyCard;
import com.example.tech_card_storage.repository.TechnologyCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyCardService {
    private final TechnologyCardRepository repository;

    @Autowired
    public TechnologyCardService(TechnologyCardRepository repository){
        this.repository = repository;
    }

    public List<TechnologyCard> searchByFullName(String name){
        return repository.findByUserFullNameContainingIgnoreCase(name);
    }

    public List<TechnologyCard> searchByInventoryNumber(String number){
        return repository.findByPcInventoryNumber(number);
    }

    public void saveCard(TechnologyCard card){
        repository.save(card);
    }
}
