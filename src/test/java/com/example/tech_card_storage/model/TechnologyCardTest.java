package com.example.tech_card_storage.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TechnologyCardTest {

    @Test
    public void testSettingAndGettingFields(){
        TechnologyCard card = new TechnologyCard();

        card.setId(1L);
        card.setInventoryNumber("PC-123");
        card.setFullName("Иван Петров");
        card.setFilePath("uploads/sample.jpg");

        assertEquals(1L, card.getId());
        assertEquals("PC-123", card.getInventoryNumber());
        assertEquals("Иван Петров", card.getFullName());
        assertEquals("uploads/sample.jpg", card.getFilePath());
    }

    @Test
    public void testConsistency(){
        TechnologyCard card = new TechnologyCard();

        card.setInventoryNumber("");
        card.setFullName(null);

        assertTrue(card.getInventoryNumber().isEmpty());
        assertNull(card.getFullName());
    }

    @Test
    public void testConstructorWithArguments(){
        TechnologyCard card = new TechnologyCard(1L, "PC-123", "Иван Петров",  "uploads/sample.jpg");

        assertEquals(1L, card.getId());
        assertEquals("PC-123", card.getInventoryNumber());
        assertEquals("Иван Петров", card.getFullName());
        assertEquals("uploads/sample.jpg", card.getFilePath());
    }

    @Test
    public void testDefaultConstructor(){
        TechnologyCard card = new TechnologyCard();

        assertNull(card.getId());
        assertNull(card.getInventoryNumber());
        assertNull(card.getFullName());
        assertNull(card.getFilePath());

    }
}
