package com.example.tech_card_storage.controller;

import com.example.tech_card_storage.model.TechnologyCard;
import com.example.tech_card_storage.service.TechnologyCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class TechnologyCardController {
    private final TechnologyCardService techCardService;

    public TechnologyCardController(TechnologyCardService techCardService){
        this.techCardService = techCardService;
    }

    @GetMapping("/search-by-full-name")
    public ResponseEntity<List<TechnologyCard>> searchByFullName(@RequestParam("name") String name) {
        return ResponseEntity.ok(techCardService.searchByFullName(name));
    }

    @GetMapping("/search-by-inventory-number")
    public ResponseEntity<List<TechnologyCard>> searchByInventoryNumber(@RequestParam("number") String number){
        return ResponseEntity.ok(techCardService.searchByInventoryNumber(number));
    }

    //Post-метод для загрузки новой карты
    @PostMapping("/upload")
    public ResponseEntity<String> uploadCard(
            @RequestParam("file") MultipartFile file,
            @RequestParam("inventoryNumber") String inventoryNumber,
            @RequestParam("fullName") String fullName
    ) throws IOException {
        if (!file.isEmpty()) {
            var filePath = "uploads/" + file.getOriginalFilename();
            Files.write(Paths.get(filePath), file.getBytes());

            var card = new TechnologyCard();
            card.setInventoryNumber(inventoryNumber);
            card.setFullName(fullName);
            card.setFilePath(filePath);
            techCardService.saveCard(card);

            return ResponseEntity.ok("Картинка успешно загружена");
        }
        return ResponseEntity.badRequest().body("Ошибка загрузки файла");
    }
}
