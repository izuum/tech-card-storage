package com.example.tech_card_storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "technology_cards")
public class TechnologyCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DATE")
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private LocalDate createdAt;

    @Column(name = "pc_inventory_number")
    private String pcInventoryNumber;

    @Column(name = "user_full_name")
    private String userFullName;

    @Column(name = "file_path")
    private String filePath;
}
