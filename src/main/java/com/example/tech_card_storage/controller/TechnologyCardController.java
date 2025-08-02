package com.example.tech_card_storage.controller;

import com.example.tech_card_storage.service.TechnologyCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/cards")
public class TechnologyCardController {
    private final TechnologyCardService service;

    @Autowired
    public TechnologyCardController(TechnologyCardService service){
        this.service = service;
    }

    @GetMapping("/search")
    public String performSearch(@RequestParam("searchTerm") String searchTerm, Model model) {
        return service.search(searchTerm, model);
    }

    @PostMapping("/upload")
    public String uploadCard(
            @RequestPart("file") MultipartFile file,
            @RequestParam("inventoryNumber") String inventoryNumber,
            @RequestParam("fullName") String fullName
    ) throws IOException {
        return service.upload(file, inventoryNumber, fullName);
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        return downloadFile(filename);
    }
}
