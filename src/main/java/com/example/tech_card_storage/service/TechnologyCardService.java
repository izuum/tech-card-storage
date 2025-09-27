package com.example.tech_card_storage.service;

import com.example.tech_card_storage.model.TechnologyCard;
import com.example.tech_card_storage.repository.TechnologyCardRepository;
import com.example.tech_card_storage.service.config.CardsFileTypeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        results.addAll(repository.findByPcInventoryNumberContaining(criteria));

        return results;
    }

    public String search(String searchTerm, Model model) {
        List<TechnologyCard> resultCards = searchByCriteria(searchTerm);
        resultCards.forEach(card -> {
            if (card.getFilePath() != null) {
                card.setFilePath(extractFilenamePath(card.getFilePath()));
            }
        });
        model.addAttribute("resultCards", resultCards);
        return "search";
    }

    public boolean upload(
            MultipartFile file,
            String inventoryNumber,
            String fullName
    ) throws IOException {
        if (!file.isEmpty() && checkContentTypeOfUploadFile(file)) {
            String filePath = "uploads/" + file.getOriginalFilename();
            Files.write(Paths.get(filePath), file.getBytes());

            TechnologyCard card = new TechnologyCard(null, null, inventoryNumber, fullName, filePath);
            saveCard(card);

            return true;
        }
        return false;
    }

    public ResponseEntity<Resource> downloadFile(String fileName) throws MalformedURLException {
        try {
            Resource resource = new UrlResource("file:" + System.getProperty("user.dir") + "/uploads/" + fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Invalid URL");
        }
    }

    public String extractFilenamePath(String filePath){
        if (filePath == null){
            return "";
        }
        int lastSlashIndex = filePath.lastIndexOf('/');
        return filePath.substring(lastSlashIndex + 1);
    }

    public boolean checkContentTypeOfUploadFile(MultipartFile file) {
        if(!CardsFileTypeConfig.CARDS_FILE_TYPE_LIST.contains(file.getContentType())){
            throw new IllegalArgumentException("Разрешена загрузка ТОЛЬКО PDF-файлов");
        }
        return true;
    }
}
