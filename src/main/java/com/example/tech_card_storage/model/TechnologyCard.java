package com.example.tech_card_storage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "technology_cards")
public class TechnologyCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pc_inventory_number")
    private String pcInventoryNumber;

    @Column(name = "user_full_name")
    private String userFullName;

    @Column(name = "file_path")
    private String filePath;

    public TechnologyCard() {
    }

    public TechnologyCard(Long id, String pcInventoryNumber, String userFullName, String filePath){
        this.id = id;
        this.pcInventoryNumber = pcInventoryNumber;
        this.userFullName = userFullName;
        this.filePath = filePath;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getInventoryNumber() {
        return pcInventoryNumber;
    }
    public void setInventoryNumber(String pcInventoryNumber) {
        this.pcInventoryNumber = pcInventoryNumber;
    }

    public String getFullName() {
        return userFullName;
    }
    public void setFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
