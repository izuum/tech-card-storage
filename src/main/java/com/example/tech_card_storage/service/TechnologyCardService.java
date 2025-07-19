package com.example.tech_card_storage.service;

import com.example.tech_card_storage.model.TechnologyCard;
import com.example.tech_card_storage.repository.TechnologyCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TechnologyCardService {
    private final TechnologyCardRepository repository;

    @Autowired
    public TechnologyCardService(TechnologyCardRepository repository){
        this.repository = repository;
    }

    public void saveCard(TechnologyCard card){
        repository.save(card);
    }

    public List<TechnologyCard> searchByCriteria(String criteria){
        List<TechnologyCard> results = new ArrayList<>();
        results.addAll(repository.findByUserFullNameContainingIgnoreCase(criteria));
        results.addAll(repository.findByPcInventoryNumber(criteria));

        return results;
    }
}
