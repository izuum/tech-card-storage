package com.example.tech_card_storage.controller;

import com.example.tech_card_storage.model.TechnologyCard;
import com.example.tech_card_storage.service.TechnologyCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/cards")
public class TechnologyCardController {
    private final TechnologyCardService techCardService;

    @Autowired
    public TechnologyCardController(TechnologyCardService techCardService){
        this.techCardService = techCardService;
    }

    @GetMapping("/search")
    public String performSearch(@RequestParam("searchTerm") String searchTerm, Model model) {
        List<TechnologyCard> resultCards = techCardService.searchByCriteria(searchTerm);
        resultCards.forEach(card -> card.setFilePath(extractFilenamePath(card.getFilePath())));
        model.addAttribute("resultCards", resultCards);
        return "search";
    }

    //Post-метод для загрузки новой карты
    @PostMapping("/upload")
    public String uploadCard(
            @RequestPart("file") MultipartFile file,
            @RequestParam("inventoryNumber") String inventoryNumber,
            @RequestParam("fullName") String fullName
    ) throws IOException {
        if (!file.isEmpty()) {
            String filePath = "uploads/" + file.getOriginalFilename();
            Files.write(Paths.get(filePath), file.getBytes());

            TechnologyCard card = new TechnologyCard(null, inventoryNumber, fullName, filePath);
            techCardService.saveCard(card);

            return "redirect:/search?searchTerm=" + fullName;
        }
        return "redirect:/upload";
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws MalformedURLException {
        Resource resource = new UrlResource("file:" + System.getProperty("user.dir") + "/uploads" + filename);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private String extractFilenamePath(String filePath){
        int lastSlashIndex = filePath.lastIndexOf('/');
        return filePath.substring(lastSlashIndex + 1);
    }
}
