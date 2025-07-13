package com.example.tech_card_storage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "technology_cards")
public class TechnologyCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Column(name = "pc_inventory_number")
    private String inventoryNumber;

    @Column(name = "user_full_name")
    private String fullName;

    @Column(name = "file_path")
    private String filePath;
}
