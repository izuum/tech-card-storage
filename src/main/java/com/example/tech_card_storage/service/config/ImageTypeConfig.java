package com.example.tech_card_storage.service.config;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ImageTypeConfig {
    private final List<String> imageTypeList = Arrays.asList("image/png", "image/jpeg", "image/jpg", "image/gif");

    public List<String> getImageTypeList() {
        return imageTypeList;
    }

}
