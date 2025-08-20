package com.example.tech_card_storage.controller;

import com.example.tech_card_storage.service.TechnologyCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;

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
            @RequestParam("fullName") String fullName,
            RedirectAttributes redirectAttributes) throws IOException {
        if(service.upload(file, inventoryNumber, fullName)){
            redirectAttributes.addFlashAttribute("successMessage", "Технологическая карта успешно загружена!");
            return "redirect:/upload";
        }
        return "redirect:/error-page.html";
    }

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws MalformedURLException {
        return service.downloadFile(filename);
    }
}
